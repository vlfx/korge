package korlibs.korge.ui

import korlibs.korge.annotations.*
import korlibs.korge.render.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.memory.*

@KorgeExperimental
inline fun Container.uiVerticalList(
    provider: UIVerticalList.Provider,
    width: Float = 256f,
    block: @ViewDslMarker Container.(UIVerticalList) -> Unit = {}
): UIVerticalList = UIVerticalList(provider, width)
    .addTo(this).also { block(it) }

@KorgeExperimental
open class UIVerticalList(provider: Provider, width: Float = 200f) : UIView(DEFAULT_SIZE.copy(width = width)) {
    interface Provider {
        val numItems: Int
        val fixedHeight: Float?
        fun getItemY(index: Int): Float = (fixedHeight ?: 20f) * index
        fun getItemHeight(index: Int): Float
        fun getItemView(index: Int, vlist: UIVerticalList): View

        object Dummy : Provider {
            override val numItems: Int get() = 0
            override val fixedHeight: Float? = null
            override fun getItemHeight(index: Int): Float = 0f
            override fun getItemView(index: Int, vlist: UIVerticalList): View = DummyView()
        }
    }

    private var dirty = false
    private val viewsByIndex = LinkedHashMap<Int, View>()
    private var lastArea = Rectangle.NaN
    private var lastPoint = Point.NaN
    var provider: Provider = provider
        set(value) {
            field = value
            dirty = true
            updateList()
        }

    init {
        updateList()
        addUpdater {
            updateList()
        }
    }

    override fun renderInternal(ctx: RenderContext) {
        updateList()
        super.renderInternal(ctx)
    }

    private fun getIndexAtY(y: Float): Int {
        val index = y / (provider.fixedHeight?.toFloat() ?: 20f)
        return index.toInt()
    }

    /**
     * Invalidates the list, regenerating all the children views.
     */
    fun invalidateList() {
        dirty = true
        removeChildren()
        viewsByIndex.clear()
        updateList()
    }

    /**
     * Updates the list after size changes, but keeps its contents.
     */
    fun updateList() {
        if (parent == null) return
        //if (stage == null) return
        val area = getVisibleGlobalArea()
        //val area = getVisibleWindowArea(tempRect)
        val point = globalPos
        val numItems = provider.numItems
        if (dirty || area != lastArea || point != lastPoint) {
            dirty = false
            lastArea = area
            lastPoint = point

            //val nheight = provider.fixedHeight?.toDouble() ?: 20.0
            //val nItems = (area.height / nheight).toIntCeil()
            //println(nItems)
            //removeChildren()

            //val fromIndex = (startIndex).clamp(0, numItems - 1)
            //val toIndex = (startIndex + nItems).clamp(0, numItems - 1)

            //println("----")

            //println("point=$point")

            val transform = parent!!.globalMatrix.toTransform()
            //println("transform=${transform.scaleAvg}")
            val fromIndex = getIndexAtY((-point.y + 0) / transform.scaleY).clamp(0, numItems - 1)
            var toIndex = fromIndex
            //println("numItems=$numItems")
            if (numItems > 0) {
                for (index in fromIndex until numItems) {
                    val view = viewsByIndex.getOrPut(index) {
                        val itemHeight = provider.getItemHeight(index)
                        provider.getItemView(index, this)
                            .also { addChild(it) }
                            .position(0f, provider.getItemY(index))
                            .size(width, itemHeight)
                    }
                    view.zIndex = index.toFloat()
                    toIndex = index

                    //val localViewY = view.localToGlobalY(0.0, view.height)

                    //println(":: ${view.localToGlobalY(0.0, view.height)}, ${area.bottom}")

                    //if (view.localToRenderY(0.0, view.height) >= area.bottom) {
                    if (view.localToGlobal(Point(0.0, view.heightD)).yD >= area.bottom) {
                        //if (view.localToWindowY(stage!!.views, 0.0, view.height) >= area.bottom) {
                        //if (false) {
                        //println("localViewY=localViewY, globalY=${view.localToGlobalY(0.0, view.height)}")
                        break
                    }
                }
            }
            //println("area=$area, point=$point, nItems=${toIndex - fromIndex}, fromIndex=$fromIndex, toIndex=$toIndex, globalBounds=${this.globalBounds}")

            val removeIndices = viewsByIndex.keys.filter { it !in fromIndex .. toIndex }.toSet()

            viewsByIndex.forEach { (index, view) ->
                if (index in removeIndices) {
                    view.removeFromParent()
                }
            }
            for (index in removeIndices) viewsByIndex.remove(index)
        }
        size(width, provider.getItemY(numItems - 1) + provider.getItemHeight(numItems - 1))
        //println("height=$height")
    }
}

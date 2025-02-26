package korlibs.math.geom

import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2Test {
    @Test
    fun name() {
        val v = MPoint(1.0, 1.0)
        //assertEquals(sqrt(2.0), v.length, 0.001)
        assertEquals(sqrt(2.0), v.length)
    }

    @Test
    fun testString() {
        assertEquals("(1, 2)", MPoint(1, 2).toString())
    }

    @Test
    fun testIsCollinear() {
        assertEquals(true, Point.isCollinear(Point(0, 0), Point(5, 0), Point(10, 0)))
        assertEquals(false, Point.isCollinear(Point(0, 0.1), Point(5, 0), Point(10, 0)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 0.1), Point(10, 0)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 0), Point(10, 0.1)))

        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 5), Point(0, 10)))
        assertEquals(false, Point.isCollinear(Point(0.1, 0), Point(0, 5), Point(0, 10)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(0.1, 5), Point(0, 10)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(0, 5), Point(0.1, 10)))

        assertEquals(true, Point.isCollinear(Point(0, 0), Point(5, 5), Point(10, 10)))
        assertEquals(false, Point.isCollinear(Point(0, 0.1), Point(5, 5), Point(10, 10)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 5.1), Point(10, 10)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 5), Point(10, 10.1)))

        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 5), Point(5, -5)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 0), Point(0, 5)))

        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 0), Point(5, -5)))
        assertEquals(false, Point.isCollinear(Point(0, 0), Point(5, 5), Point(5, -5)))

        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 0), Point(0, 0)))
        assertEquals(true, Point.isCollinear(Point(0, 0), Point(5, 0), Point(0, 0)))
        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 5), Point(0, 0)))
        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 0), Point(0, 5)))
        assertEquals(true, Point.isCollinear(Point(0, 0), Point(0, 0), Point(5, 0)))
    }
}

package korlibs.crypto

import kotlin.test.Test
import kotlin.test.assertEquals

class HMACTest {
    @Test
    fun hmacSHA1() {
        val data = ByteArray(16){(it + 1).toByte()}

        var key = ByteArray(10){it.toByte()} // key length lt chunk size
        var mac = HMAC.hmacSHA1(key, data)
        assertEquals("976a1c6879bc6e776752c254343431bcdc46f298", mac.hex)

        key = ByteArray(64){it.toByte()} // key length eq chunk size
        mac = HMAC.hmacSHA1(key, data)
        assertEquals("322af17f50bfa006ecae7cab356d2ba1f8517f8f", mac.hex)

        key = ByteArray(120){it.toByte()} // key length gt chunk size
        mac = HMAC.hmacSHA1(key, data)
        assertEquals("3123c313a25aab46b56b873730291a57783d8dce", mac.hex)
    }

    @Test
    fun hmacSHA256() {
        val data = ByteArray(16){(it + 1).toByte()}

        var key = ByteArray(10){it.toByte()} // key length lt chunk size
        var mac = HMAC.hmacSHA256(key, data)
        assertEquals("4d4b24f0ac17843f1797609b6acb67acbb72b04775c6186a62b69db8b6fc00af", mac.hex)

        key = ByteArray(64){it.toByte()} // key length eq chunk size
        mac = HMAC.hmacSHA256(key, data)
        assertEquals("1b0646619ebcdde1fbb754f23182f26da3067e0689c27b928040271bf1848a33", mac.hex)

        key = ByteArray(120){it.toByte()} // key length gt chunk size
        mac = HMAC.hmacSHA256(key, data)
        assertEquals("c8bdca76e9e2257a5e7b26f4d9f1eee7e21fcea098e2255288a32ca0c19ae605", mac.hex)
    }

    @Test
    fun hmacMD5() {
        val data = ByteArray(16){(it + 1).toByte()}

        var key = ByteArray(10){it.toByte()} // key length lt chunk size
        var mac = HMAC.hmacMD5(key, data)
        assertEquals("ed082a8955ccb35ced1dd4fa3372f08e", mac.hex)

        key = ByteArray(64){it.toByte()} // key length eq chunk size
        mac = HMAC.hmacMD5(key, data)
        assertEquals("ad6a2aefac457cb561d5e43066f3dc8b", mac.hex)

        key = ByteArray(120){it.toByte()} // key length gt chunk size
        mac = HMAC.hmacMD5(key, data)
        assertEquals("e34ada41811e232eeeaf9b3a1a1bf0d1", mac.hex)
    }
}

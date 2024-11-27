package mx.aztek.hackernewsreader.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {
    @Test
    fun timeAgo_failure() {
        assertEquals("Not a date".timeAgo(), "Invalid string")
    }
}

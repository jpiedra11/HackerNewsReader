package mx.aztek.hackernewsreader.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {
    @Test
    fun timeAgo_failure() {
        assertEquals("Not a date".timeAgo(), "Invalid date")
    }

    @Test
    fun timeAgo_success() {
        assertEquals("2020-11-25T17:25:15Z".timeAgo(), "long time ago")
    }
}

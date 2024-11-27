package mx.aztek.hackernewsreader.data

import android.app.Application
import android.preference.PreferenceManager

class SharedPreferences(
    context: Application,
) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getDeletedNews(): List<String> = prefs.getString("HACKER_NEWS_DELETED", "").orEmpty().split(",")

    fun setDeletedNews(deleted: List<String>) {
        val string = deleted.joinToString(",")
        prefs.edit().putString("HACKER_NEWS_DELETED", string).apply()
    }
}

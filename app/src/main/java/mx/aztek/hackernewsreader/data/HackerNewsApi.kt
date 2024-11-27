package mx.aztek.hackernewsreader.data

import mx.aztek.hackernewsreader.model.HackerNews
import mx.aztek.hackernewsreader.utils.Constants.ANDROID
import mx.aztek.hackernewsreader.utils.Constants.NEWS_END_POINT
import mx.aztek.hackernewsreader.utils.Constants.QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsApi {
    @GET(NEWS_END_POINT)
    suspend fun getHackerNews(
        @Query(QUERY) query: String = ANDROID,
    ): HackerNews
}

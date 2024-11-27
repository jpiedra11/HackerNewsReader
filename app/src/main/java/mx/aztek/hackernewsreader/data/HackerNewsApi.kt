package mx.aztek.hackernewsreader.data

import mx.aztek.hackernewsreader.model.HackerNews
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsApi {
    @GET("api/v1/search_by_date")
    suspend fun getHackerNews(
        @Query("query") query: String = "android",
    ): HackerNews
}

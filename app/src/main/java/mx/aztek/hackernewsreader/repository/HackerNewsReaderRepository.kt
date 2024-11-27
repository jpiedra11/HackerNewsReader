package mx.aztek.hackernewsreader.repository

import mx.aztek.hackernewsreader.data.HackerNewsApi
import mx.aztek.hackernewsreader.data.SharedPreferences
import mx.aztek.hackernewsreader.model.Hits
import javax.inject.Inject

class HackerNewsReaderRepository
    @Inject
    constructor(
        private val hackerNewsApi: HackerNewsApi,
        private val sharedPreferences: SharedPreferences,
    ) {
        suspend fun getHits(): ArrayList<Hits> = hackerNewsApi.getHackerNews().hits

        fun getDeletedNews(): List<String> = sharedPreferences.getDeletedNews()

        fun setDeletedNews(deleteNews: List<String>) = sharedPreferences.setDeletedNews(deleteNews)
    }

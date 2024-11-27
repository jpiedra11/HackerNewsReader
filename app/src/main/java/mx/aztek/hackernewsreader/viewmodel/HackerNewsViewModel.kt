package mx.aztek.hackernewsreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.aztek.hackernewsreader.model.Hits
import mx.aztek.hackernewsreader.repository.HackerNewsReaderRepository
import javax.inject.Inject

@HiltViewModel
class HackerNewsViewModel
    @Inject
    constructor(
        private val repository: HackerNewsReaderRepository,
    ) : ViewModel() {
        private val _hits = MutableStateFlow(emptyList<Hits>())
        val hits: StateFlow<List<Hits>>
            get() = _hits

        init {
            viewModelScope.launch {
                _hits.value = repository.getHits()
            }
        }

        fun refreshNews() {
            viewModelScope.launch {
                _hits.value = repository.getHits().filter { hit -> !getDeletedNews().contains(hit.objectID) }
            }
        }

        fun setDeletedNews(deletedNews: List<String>) {
            repository.setDeletedNews(deletedNews)
        }

        fun getDeletedNews(): List<String> = repository.getDeletedNews()

        fun deleteNews(newsToDelete: String) {
            val deletedNews = getDeletedNews().toMutableList()
            deletedNews.add(newsToDelete)
            setDeletedNews(deletedNews)
        }
    }

package com.nytimes.task.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nytimes.task.base.BaseViewModel
import com.nytimes.task.helpers.ErrorHandler
import com.nytimes.task.network.api.ApiServiceRepository
import com.nytimes.task.network.data.NewsItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val apiServiceRepository: ApiServiceRepository,
    private val errorHandler: ErrorHandler,
    private val key: String
) : BaseViewModel() {

    private val _news = apiServiceRepository.getNews(key)
        .catch { e ->
            _isLoading.value = false
            errorHandler.parseError(e)
        }
        .map {
            _isLoading.value = false
            it.result
        }
        .asLiveData()


    val news: LiveData<List<NewsItem>> = _news


}
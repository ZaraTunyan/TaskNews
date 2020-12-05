package com.nytimes.task.network.api

import com.nytimes.task.network.data.BaseResponse
import com.nytimes.task.network.data.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val period : Int = 30

class ApiServiceRepository(private val apiService : ApiService) {

    fun getNews(key: String): Flow<BaseResponse<List<NewsItem>>> =
        flow { emit(apiService.getNews(period, key)) }
}
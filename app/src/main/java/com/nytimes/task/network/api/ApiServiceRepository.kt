package com.nytimes.task.network.api

import com.nytimes.task.network.data.BaseResponse
import com.nytimes.task.network.data.NewsItem
import io.reactivex.Observable

const val period : Int = 30

class ApiServiceRepository(private val apiService : ApiService){

    fun getNews(key : String) : Observable<BaseResponse<List<NewsItem>>> = apiService.getNews(period, key)

}
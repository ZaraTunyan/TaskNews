package com.nytimes.task.network.api

import com.nytimes.task.network.data.BaseResponse
import com.nytimes.task.network.data.NewsItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("viewed/{period}.json")
    fun getNews(@Path("period") period: Int, @Query("api-key") key : String) : Observable<BaseResponse<List<NewsItem>>>
}
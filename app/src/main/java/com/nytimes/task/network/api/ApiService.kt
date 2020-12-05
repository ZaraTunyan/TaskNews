package com.nytimes.task.network.api

import com.nytimes.task.network.data.BaseResponse
import com.nytimes.task.network.data.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("viewed/{period}.json")
    suspend fun getNews(
        @Path("period") period: Int,
        @Query("api-key") key: String
    ): BaseResponse<List<NewsItem>>
}
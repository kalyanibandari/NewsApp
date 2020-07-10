package com.example.newsapp.service

import com.example.newsapp.model.Articles
import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetNewsDataService {
    @GET("v2/everything")
    fun getNewsList(
        @Query("q") newsName: String,
        @Query("from") fromDate: String,
        @Query("sortBy") newsType: String,
        @Query("apiKey") apiKey: String
    ): Call<News>
}
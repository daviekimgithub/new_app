package com.example.newsapp

import com.example.newsapp.App
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/search/Counter/page/1/")
    fun getNews(): Call<List<App>>

}
package com.example.newsapp

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = addHeader(originalRequest)
            chain.proceed(modifiedRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://steam2.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private fun addHeader(request: Request): Request {
        val modifiedRequest = request.newBuilder()
            .header("X-RapidAPI-Key", "415f6d39f6mshd74e45af5034a7fp18252cjsn7701b2010f83")
            .header("X-RapidAPI-Host", "steam2.p.rapidapi.com")
            .build()
        return modifiedRequest
    }

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

}
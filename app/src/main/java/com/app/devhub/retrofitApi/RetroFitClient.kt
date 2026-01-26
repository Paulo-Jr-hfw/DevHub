package com.app.devhub.retrofitApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInitializer {
    private const val BASE_URL = "https://api.github.com/"

    val api: GitHubService  by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService ::class.java)
    }
}
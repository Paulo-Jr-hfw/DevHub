package com.app.devhub.retrofitApi

import com.app.devhub.model.GitProfileWeb
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService  {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): GitProfileWeb
}
package com.app.devhub.model

import com.google.gson.annotations.SerializedName

data class GitRepoWeb(
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int,
    val language: String?
)

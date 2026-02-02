package com.app.devhub.model

import com.google.gson.annotations.SerializedName

data class GitProfileWeb(
    val name: String?,
    @SerializedName("login")
    val user: String,
    val bio: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("public_repos")
    val repositories: Int?
)

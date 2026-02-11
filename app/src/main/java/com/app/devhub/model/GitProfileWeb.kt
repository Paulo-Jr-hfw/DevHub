package com.app.devhub.model

import com.app.devhub.data.local.room.GitProfileEntity
import com.google.gson.annotations.SerializedName

data class GitProfileWeb(
    val name: String?,
    @SerializedName("login")
    val user: String,
    val bio: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("public_repos")
    val repositories: Int?,
    val followers: Int?,
    val following: Int?
)

fun GitProfileWeb.toEntity(): GitProfileEntity {
    return GitProfileEntity(
        user = this.user,
        name = this.name,
        avatarUrl = this.avatarUrl,
        bio = this.bio,
        repositories = this.repositories,
        followers = this.followers,
        following = this.following
    )
}

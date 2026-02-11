package com.app.devhub.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil_favorito")
data class GitProfileEntity(
    @PrimaryKey val user: String,
    val name: String?,
    val avatarUrl: String?,
    val bio: String?,
    val repositories: Int?,
    val followers: Int?,
    val following: Int?
)
package com.app.devhub.data.repository

import com.app.devhub.data.local.room.GitProfileDao
import com.app.devhub.data.local.room.GitProfileEntity
import com.app.devhub.data.remote.retrofitApi.GitHubService
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.model.GitRepoWeb
import com.app.devhub.model.toEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitProfileRepository @Inject constructor(
    private val api: GitHubService,
    private val dao: GitProfileDao
) {
    suspend fun getProfile(username: String): GitProfileEntity {
        val localProfile = dao.getProfileByUsername(username)
        if (localProfile != null) {
            return localProfile
        }

        val remoteProfile = api.getUser(username)
        return remoteProfile.toEntity()
    }
    suspend fun saveFavorite(profile: GitProfileEntity) {
        dao.insert(profile)
    }
    suspend fun deleteFavorite(profile: GitProfileEntity) {
        dao.delete(profile)
    }

    fun getAllFavorites() = dao.getAllFavorites()

    suspend fun isFavorite(username: String): Boolean {
        return dao.isFavorite(username)
    }

    suspend fun getFullProfile(username: String): Pair<GitProfileEntity, List<GitRepoWeb>> {
        return coroutineScope {
            val userDef = async { getProfile(username) }
            val reposDef = async { api.getUserRepos(username) }

            Pair(userDef.await(), reposDef.await())
        }
    }
}

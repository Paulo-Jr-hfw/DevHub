package com.app.devhub.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.devhub.data.local.room.GitProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GitProfileDao {
    //salvar
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(profile: GitProfileEntity)

    //lista favoritos
    @Query("SELECT * FROM perfil_favorito")
    fun getAllFavorites(): Flow<List<GitProfileEntity>>

    //deletar favorito
    @Delete
    suspend fun delete(profile: GitProfileEntity)

    //verificar se está favoritado
    @Query("SELECT EXISTS(SELECT 1 FROM perfil_favorito WHERE user = :username)")
    suspend fun isFavorite(username: String): Boolean

}
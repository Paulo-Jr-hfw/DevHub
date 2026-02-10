package com.app.devhub.screens.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.devhub.data.local.room.GitProfileEntity
import com.app.devhub.data.repository.GitProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val repository: GitProfileRepository
) : ViewModel(){
    val favoritos = repository.getAllFavorites()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun removerFavorito(profile: GitProfileEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(profile)
        }
    }
}
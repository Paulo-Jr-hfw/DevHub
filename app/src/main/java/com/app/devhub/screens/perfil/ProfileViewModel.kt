package com.app.devhub.screens.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.devhub.data.local.room.GitProfileEntity
import com.app.devhub.model.GitRepoWeb
import com.app.devhub.data.repository.GitProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(
        val user: GitProfileEntity,
        val repositories: List<GitRepoWeb>
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
    object Empty : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: GitProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Empty)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUser(username: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val (user, repos) = repository.getFullProfile(username)

                _uiState.value = ProfileUiState.Success(
                    user = user,
                    repositories = repos
                )

            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Usuário não encontrado")
                e.printStackTrace()
            }
        }
    }
}
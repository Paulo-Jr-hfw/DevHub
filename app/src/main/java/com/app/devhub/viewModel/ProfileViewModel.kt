package com.app.devhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.model.GitRepoWeb
import com.app.devhub.retrofitApi.RetrofitInitializer
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: GitProfileWeb,
        val repositories: List<GitRepoWeb>
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
    object Empty : ProfileUiState()
}

class ProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Empty)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUser(username: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                //async para iniciar as duas buscas em paralelo
                val userDeferred = async { RetrofitInitializer.api.getUser(username) }
                val reposDeferred = async { RetrofitInitializer.api.getUserRepos(username) }
                val userResponse = userDeferred.await()
                val reposResponse = reposDeferred.await()

                _uiState.value = ProfileUiState.Success(
                    user = userResponse,
                    repositories = reposResponse
                )
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Usuário não encontrado ou erro de rede")
                e.printStackTrace()
            }
        }
    }
}
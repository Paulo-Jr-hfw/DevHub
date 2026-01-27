package com.app.devhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.retrofitApi.RetrofitInitializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: GitProfileWeb) : ProfileUiState()
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
                val response = RetrofitInitializer.api.getUser(username)
                _uiState.value = ProfileUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error("Usuário não encontrado ou erro de rede")
            }
        }
    }
}
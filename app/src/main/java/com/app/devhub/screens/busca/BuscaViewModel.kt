package com.app.devhub.screens.busca

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BuscaUiState(
    val searchText: String = "",
    val isLoading: Boolean = false
)

class BuscaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BuscaUiState(searchText =""))
    val uiState = _uiState.asStateFlow()

    fun onSearchChange(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchText = newText
            )
        }
    }
    fun limparTexto() {
        _uiState.update { currentState ->
            currentState.copy(
                searchText = ""
            )
        }
    }

    }


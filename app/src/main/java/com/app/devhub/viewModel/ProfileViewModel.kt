package com.app.devhub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.retrofitApi.RetrofitInitializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val _user = MutableStateFlow<GitProfileWeb?>(null)
    val user: StateFlow<GitProfileWeb?> = _user

    fun loadUser(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInitializer.api.getUser(username)
                _user.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
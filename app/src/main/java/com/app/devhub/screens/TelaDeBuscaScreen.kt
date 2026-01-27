package com.app.devhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.devhub.components.gitHubLogoAnimated
import com.app.devhub.components.searchBar
import com.app.devhub.viewModel.BuscaViewModel

@Composable
fun telaDeBusca(
    buscaViewModel : BuscaViewModel = viewModel(),
    onNavigateToProfile: (String) -> Unit
) {
    val uiState by buscaViewModel.uiState.collectAsState()

    TelaDeBuscaContent(
        searchText = uiState.searchText,
        onSearchChange = buscaViewModel::onSearchChange,
        onSearchClick = {
            if (uiState.searchText.isNotBlank()) {
                onNavigateToProfile(uiState.searchText)
            }
        }
    )
}

@Composable
fun TelaDeBuscaContent(
    searchText: String,
    onSearchChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        gitHubLogoAnimated(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        searchBar(
            value = searchText,
            onValueChange = onSearchChange,
            onSearch = onSearchClick,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
    }
}

//@Preview
//@Composable
//private fun telabuscaPreview() {
//    DevHubTheme {
//        telaDeBusca()
//    }
//
//}
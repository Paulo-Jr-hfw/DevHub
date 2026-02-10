package com.app.devhub.screens.busca

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.devhub.components.gitHubLogoAnimated
import com.app.devhub.components.searchBar
import com.app.devhub.ui.theme.DevHubTheme


@Composable
fun TelaDeBusca(
    buscaViewModel : BuscaViewModel = viewModel(),
    onNavigateToProfile: (String) -> Unit,
    onNavigateToFavoritos: () -> Unit
) {
    val uiState by buscaViewModel.uiState.collectAsState()


            TelaDeBuscaContent(
                searchText = uiState.searchText,
                onSearchChange = buscaViewModel::onSearchChange,
                onSearchClick = {
                    if (uiState.searchText.isNotBlank()) {
                        onNavigateToProfile(uiState.searchText)
                    }
                },
                onNavigateToFavoritos = onNavigateToFavoritos
            )
        }

@Composable
fun TelaDeBuscaContent(
    searchText: String,
    onSearchChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onNavigateToFavoritos: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "DevHub",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        gitHubLogoAnimated(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        searchBar(
            value = searchText,
            onValueChange = onSearchChange,
            onSearch = onSearchClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNavigateToFavoritos,
            border = BorderStroke(1.dp, Color(0xFFFFD700))
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("VER FAVORITOS", color = Color(0xFFFFD700))
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun telabuscaPreview() {
    DevHubTheme {
        TelaDeBuscaContent(
            searchText = "Paulo Junior",
            onSearchChange = {},
            onSearchClick = {},
            onNavigateToFavoritos = {}
        )
    }
}
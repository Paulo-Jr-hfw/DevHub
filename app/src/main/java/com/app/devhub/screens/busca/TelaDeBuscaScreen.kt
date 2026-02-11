package com.app.devhub.screens.busca

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.devhub.components.gitHubLogoAnimated
import com.app.devhub.components.searchBar
import com.app.devhub.ui.theme.DevHubTheme
import com.app.devhub.ui.theme.GithubBackground
import com.app.devhub.ui.theme.GithubBlue
import com.app.devhub.ui.theme.GithubGold
import com.app.devhub.ui.theme.TextForeground


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

    val gradientHero = Brush.linearGradient(
        0.0f to GithubBlue.copy(alpha = 0.15f),
        1.0f to GithubGold.copy(alpha = 0.05f),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GithubBackground)
            .background(gradientHero),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                Text(
                    text = "Dev",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextForeground,
                        letterSpacing = (-1).sp
                    )
                )
                Text(
                    text = "Hub",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = GithubBlue,
                        letterSpacing = (-1).sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, GithubGold.copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = GithubGold
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GithubGold
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "VER FAVORITOS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TelaBuscaPreview() {
    DevHubTheme {
        TelaDeBuscaContent(
            searchText = "Paulo Junior",
            onSearchChange = {},
            onSearchClick = {},
            onNavigateToFavoritos = {}
        )
    }
}
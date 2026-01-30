package com.app.devhub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.devhub.R
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.model.GitRepoWeb
import com.app.devhub.viewModel.ProfileUiState
import com.app.devhub.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfil(
    username: String,
    viewModel: ProfileViewModel = viewModel(),
    onVoltarClick: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(username) {
        viewModel.loadUser(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de $username") },
                navigationIcon = {
                    IconButton(onClick = onVoltarClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        // O recheio muda conforme o estado
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> LoadingView()
                is ProfileUiState.Success -> ProfileContent(
                    user = state.user,
                    repositories = state.repositories
                )
                is ProfileUiState.Error -> ErrorView(state.message)
                else -> {}
            }
        }
    }
}

@Composable
fun ProfileContent(
    user: GitProfileWeb,
    repositories: List<GitRepoWeb>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            PerfilCard(user = user)
        }
        item{
            Text(
                text = "Popular repositories",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        items(repositories) { repo ->
            RepoCard(repo = repo)
        }
    }
}

@Composable
fun PerfilCard(user: GitProfileWeb) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val headerHeight = 150.dp
        val imageSize = 150.dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue,
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .height(headerHeight)
        )


        AsyncImage(
            model = user.avatarUrl,
            placeholder = painterResource(R.drawable.foto_perfil),
            contentDescription = "foto perfil",
            modifier = Modifier
                .size(imageSize)
                .align(Alignment.TopCenter)
                .offset(y = headerHeight - imageSize / 2)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = headerHeight + imageSize / 2 + 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(user.name ?: "Sem nome", fontSize = 32.sp)
            Text(user.user, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(user.bio ?: "Sem bio")
            Text("Repositórios: ${user.repositories}")
        }
    }

}

@Composable
fun RepoCard(repo: GitRepoWeb) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFF0D1117),
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = repo.name,
                color = Color(0xFF58A6FF),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (!repo.description.isNullOrBlank()) {
                Text(
                    text = repo.description,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Linha de Status (Linguagem, Estrelas e Forks)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Linguagem
                repo.language?.let { lang ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color.Cyan)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = lang, fontSize = 12.sp)
                    }
                }

                // Estrelas
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Text(text = "${repo.stars}", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    // Usamos o fillMaxSize para que o círculo fique centralizado na tela toda
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = Color.Red, fontWeight = FontWeight.Bold)
    }
}

package com.app.devhub.screens.perfil

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.devhub.R
import com.app.devhub.data.local.room.GitProfileEntity
import com.app.devhub.model.GitRepoWeb
import com.app.devhub.ui.theme.GithubBackground
import com.app.devhub.ui.theme.GithubBlue
import com.app.devhub.ui.theme.GithubBorder
import com.app.devhub.ui.theme.GithubCard
import com.app.devhub.ui.theme.GithubError
import com.app.devhub.ui.theme.GithubGold
import com.app.devhub.ui.theme.LanguageColors.getLanguageColor
import com.app.devhub.ui.theme.TextForeground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfil(
    username: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onVoltarClick: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(username) {
        viewModel.loadUser(username)
        viewModel.checkFavoriteStatus(username)
    }

    Scaffold(
        containerColor = GithubBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Perfil de $username",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVoltarClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            "Voltar",
                            tint = Color.White)
                    }
                },
                actions = {
                    if (uiState is ProfileUiState.Success) {
                        FavoriteButton(
                            viewModel,
                            userEntity = (uiState as ProfileUiState.Success).user,
                            isFavorite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GithubBackground,
                    titleContentColor = TextForeground
                )
            )
        }
    ) { paddingValues ->
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
    user: GitProfileEntity,
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
                text = "Repositórios populares",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = TextForeground,
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
fun PerfilCard(user: GitProfileEntity) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(GithubBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            val headerGradient = Brush.verticalGradient(
                colors = listOf(
                    GithubBlue.copy(alpha = 0.4f),
                    GithubBlue.copy(alpha = 0.1f),
                    GithubBackground)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(headerGradient)
            )


            AsyncImage(
                model = user.avatarUrl,
                placeholder = painterResource(R.drawable.image_icon),
                contentDescription = "foto perfil",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter)
                    .shadow(16.dp, CircleShape)
                    .clip(CircleShape)
                    .border(3.dp, GithubBorder, CircleShape)
                    .background(GithubCard)
            )
        }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.name ?: "Sem nome",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextForeground
                )
                Text(
                    text = "@${user.user}",
                    fontSize = 18.sp,
                    color = GithubBlue,
                    fontWeight = FontWeight.Medium
                )

                user.bio?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        color = TextForeground.copy(alpha = 0.8f)
                    )
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    ProfileStatItem(
                        icon = Icons.Default.Book,
                        value = user.repositories ?: 0,
                        label = "Repos"
                    )
                    ProfileStatItem(
                        icon = Icons.Default.Groups,
                        value = user.followers ?: 0,
                        label = "Seguidores"
                    )
                    ProfileStatItem(
                        icon = Icons.Default.PersonAdd,
                        value = user.following ?: 0,
                        label = "Seguindo"
                    )
                }
            }
    }
}


@Composable
fun RepoCard(repo: GitRepoWeb) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = GithubCard,
            contentColor = TextForeground
        ),
        border = BorderStroke(1.dp, GithubBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = repo.name,
                color = GithubBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (!repo.description.isNullOrBlank()) {
                Text(
                    text = repo.description,
                    color = TextForeground.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repo.language?.let { lang ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(getLanguageColor(lang))
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(text = lang, fontSize = 12.sp, color = TextForeground.copy(alpha = 0.6f))
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = GithubGold
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = "${repo.stars}", fontSize = 12.sp, color = TextForeground.copy(alpha = 0.6f))
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GithubBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = GithubBlue,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Buscando dados...",
                color = GithubBlue.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GithubBackground)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = GithubError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ops! algo deu errado",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FavoriteButton( viewModel: ProfileViewModel, userEntity: GitProfileEntity, isFavorite: Boolean) {
    IconButton(onClick = {
        if (isFavorite) {
            viewModel.deletaFavorito(userEntity)
        } else {
            viewModel.salvaFavorito(userEntity)
        }
    }) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Default.StarBorder,
            contentDescription = "Favoritar",
            tint = if (isFavorite) Color(0xFFFFD700) else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
    }

}

@Composable
private fun ProfileStatItem(
    icon: ImageVector,
    value: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = GithubBlue
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextForeground
            )
        }
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextForeground.copy(alpha = 0.6f)
        )
    }
}

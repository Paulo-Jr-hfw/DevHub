package com.app.devhub.screens.favoritos

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.devhub.R
import com.app.devhub.data.local.room.GitProfileEntity
import com.app.devhub.ui.theme.GithubBackground
import com.app.devhub.ui.theme.GithubBlue
import com.app.devhub.ui.theme.GithubBorder
import com.app.devhub.ui.theme.GithubCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeFavoritos(
    onNavigateBack: () -> Unit,
    onNavigateToProfile: (String) -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val listaFavoritos by viewModel.favoritos.collectAsState()

    Scaffold(
        containerColor = GithubBackground,
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GithubBackground,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (listaFavoritos.isEmpty()) {
                EmptyStateFavoritos()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = listaFavoritos,
                        key = { perfil -> perfil.user }
                    ) { perfil ->
                        Box(
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(300),
                                fadeOutSpec = tween(300),
                                placementSpec = spring(stiffness = Spring.StiffnessLow)
                            )
                        ) {
                            FavoritoCard(
                                profile = perfil,
                                onClick = { onNavigateToProfile(perfil.user) },
                                onDelete = { viewModel.removerFavorito(perfil) }
                            )
                        }
                    }
                }
        }
    }
}}

@Composable
fun FavoritoCard(
    profile: GitProfileEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = { }
) {
    // Detecta a interação (clique/toque)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animação da cor da borda e da espessura para o efeito "Glow"
    val borderColor by animateColorAsState(
        targetValue = if (isPressed) GithubBlue else GithubBorder,
        label = "borderColor"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 1.dp,
        label = "borderWidth"
    )

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = GithubCard,
            contentColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = profile.avatarUrl,
                placeholder = painterResource(R.drawable.image_icon),
                contentDescription = "Foto de ${profile.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, GithubBorder, CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = profile.name ?: "Sem nome",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "@${profile.user}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GithubBlue,
                )
                profile.bio?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Remover dos favoritos",
                    tint = Color(0xFFFFB800)
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun EmptyStateFavoritos() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(GithubBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(80.dp).alpha(0.1f),
                tint = Color.White)

            Text(
                text = "Você ainda não tem favoritos",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, name = "Lista de Favoritos")
@Composable
fun ListaFavoritosPreview() {
    val profiles = listOf(
        GitProfileEntity("google", "Google", null, "Bio do Google", 10, 10,15),
        GitProfileEntity("square", "Square", null, "Bio da Square", 5,30,40),
        GitProfileEntity("facebook", "Meta", null, "Bio da Meta", 20,50,31)
    )

    MaterialTheme {
        Column {
            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            profiles.forEach { profile ->
                FavoritoCard(profile = profile)
            }
        }
    }
}
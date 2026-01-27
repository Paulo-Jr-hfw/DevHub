package com.app.devhub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.devhub.R
import com.app.devhub.ui.theme.DevHubTheme
import com.app.devhub.viewModel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.devhub.model.GitProfileWeb
import com.app.devhub.viewModel.ProfileUiState

@Composable
fun telaPerfil(
    username: String,
    viewModel: ProfileViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(username) {
        viewModel.loadUser(username)
    }

    when (val state = uiState) {
        is ProfileUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
        is ProfileUiState.Success -> {
            ProfileContent(user = state.user)
        }
        is ProfileUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message, color = Color.Red)
            }
        }
        ProfileUiState.Empty -> { /* Nada para mostrar */ }
    }
}

@Composable
fun ProfileContent(user: GitProfileWeb) {
    Box(modifier = Modifier.fillMaxSize()) {
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



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DevHubTheme {
        telaPerfil(username = "teste")
    }
}
package com.app.devhub.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.devhub.screens.busca.TelaDeBusca
import com.app.devhub.screens.perfil.TelaPerfil
import com.app.devhub.ui.theme.DevHubTheme
import com.app.devhub.screens.busca.BuscaViewModel
import com.app.devhub.screens.favoritos.TelaDeFavoritos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                DevHubTheme {
                    val navController = rememberNavController()
                    val buscaViewModel: BuscaViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "busca"
                    ) {
                        composable(route = "busca") {
                            TelaDeBusca(
                                buscaViewModel = buscaViewModel,
                                onNavigateToProfile = { usuario ->
                                    navController.navigate("perfil/$usuario")
                                    buscaViewModel.limparTexto()
                                },
                                onNavigateToFavoritos = {
                                    navController.navigate("favoritos")
                                }
                            )
                        }

                        composable(route = "perfil/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            TelaPerfil(username = username,
                                onVoltarClick = {navController.popBackStack()
                                }
                            )
                        }

                        composable(route = "favoritos") {
                            TelaDeFavoritos(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToProfile = { usuario ->
                                    navController.navigate("perfil/$usuario")
                                    buscaViewModel.limparTexto()
                                }
                            )
                        }
                    }
                }
            }
        }
}
package com.app.devhub.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.devhub.screens.telaDeBusca
import com.app.devhub.screens.TelaPerfil
import com.app.devhub.ui.theme.DevHubTheme
import com.app.devhub.viewModel.BuscaViewModel

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
                            telaDeBusca(
                                buscaViewModel = buscaViewModel,
                                onNavigateToProfile = { usuario ->
                                    navController.navigate("perfil/$usuario")
                                    buscaViewModel.limparTexto()
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
                    }
                }
            }
        }
}
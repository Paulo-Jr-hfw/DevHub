package com.app.devhub.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.devhub.screens.telaDeBusca
import com.app.devhub.screens.telaPerfil
import com.app.devhub.ui.theme.DevHubTheme

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                DevHubTheme {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "busca"
                    ) {
                        composable(route = "busca") {
                            telaDeBusca(
                                onNavigateToProfile = { usuario ->
                                    navController.navigate("perfil/$usuario")
                                }
                            )
                        }

                        composable(route = "perfil/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            telaPerfil(username = username)
                        }
                    }
                }
            }
        }
}
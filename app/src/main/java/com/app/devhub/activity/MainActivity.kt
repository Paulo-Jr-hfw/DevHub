package com.app.devhub.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.app.devhub.screens.telaDeBusca
import com.app.devhub.screens.telaResultado
import com.app.devhub.ui.theme.DevHubTheme

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                DevHubTheme {
                    var telaAtual by remember { mutableStateOf("busca") }
                    var usuarioBuscado by remember { mutableStateOf("") }

                    when (telaAtual) {
                        "busca" -> {
                            telaDeBusca(
                                onSearchExecute = { nome ->
                                    usuarioBuscado = nome
                                    telaAtual = "resultado"
                                }
                            )
                        }
                        "resultado" -> {
                            telaResultado(username = usuarioBuscado)
                        }
                    }
                }
            }
        }
    }
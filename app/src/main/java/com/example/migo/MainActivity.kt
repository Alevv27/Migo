package com.example.migo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.migo.ui.ChatScreen
import com.example.migo.ui.SplashScreen
import com.example.migo.ui.theme.MigoTheme

class MainActivity : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels() // Obtiene el ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MigoTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "splash") {
                    // Pantalla Splash
                    composable(route = "splash") {
                        SplashScreen(navController = navController)
                    }

                    // Pantalla de Chat (destino final)
                    composable(route = "chat") {
                        ChatScreen(viewModel = chatViewModel)
                    }
                }
            }
        }
    }
}





/*Pantalla principal  con login */
/*
class MainActivity : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MigoTheme {

                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()
                val isUserLoggedIn = auth.currentUser != null

                NavHost(
                    navController = navController,
                    startDestination = "login" // Se asegura que siempre inicie en Login
                ) {
                    composable("login") {
                        LoginScreen(navController = navController, onLoginSuccess = {
                            navController.navigate("chat") {
                                popUpTo("login") { inclusive = true } // Eliminamos la pantalla de login del stack
                            }
                        })
                    }
                    /*Pantalla para registrar */
                    composable("register") {
                        RegisterScreen(navController = navController) // Agrega la pantalla de registro
                    }
                    /*Pantalla de chat*/
                    composable("chat") { ChatScreen(chatViewModel) }
                }

            }
        }
    }
}
*/






/*Pantalla principal  con login  Solo es copia en caso de que falle*/
/*
class MainActivity : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MigoTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()
                val isUserLoggedIn = auth.currentUser != null

                NavHost(
                    navController = navController,
                    startDestination = "login" // Se asegura que siempre inicie en Login
                ) {
                    composable("login") {
                        LoginScreen(navController = navController, onLoginSuccess = {
                            navController.navigate("chat") {
                                popUpTo("login") { inclusive = true } // Eliminamos la pantalla de login del stack
                            }
                        })
                    }

                    composable("chat") { ChatScreen(chatViewModel) }
                }
            }
        }
    }
}
*/

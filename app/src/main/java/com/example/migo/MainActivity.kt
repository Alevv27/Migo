package com.example.migo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.migo.ChatViewModel
import com.example.migo.ui.theme.MigoTheme

class MainActivity : ComponentActivity() {
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MigoTheme {
                ChatScreen(chatViewModel)
            }
        }
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var userInput by remember { mutableStateOf("") }
    val chatResponse by viewModel.chatResponse.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Escribe tu mensaje") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (userInput.isNotBlank()) {
                    viewModel.sendMessageToApi(userInput)  // Se usa el nombre corregido
                    userInput = ""
                }
            },
            enabled = !isLoading,
            modifier = Modifier.padding(8.dp)  // Se añade padding para mejor apariencia
        ) {
            Text(if (isLoading) "Enviando..." else "Enviar")
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text("Esperando respuesta...", color = androidx.compose.ui.graphics.Color.Gray)
        } else if (errorMessage.isNotEmpty()) {
            Text("Error: $errorMessage", color = androidx.compose.ui.graphics.Color.Red)
        } else if (chatResponse.isNotEmpty()) {
            Text("Respuesta: $chatResponse")
        }
    }
}

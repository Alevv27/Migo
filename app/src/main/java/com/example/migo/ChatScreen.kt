

package com.example.migo.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.migo.ChatViewModel

import kotlinx.coroutines.launch

/**
 * Pantalla principal del chat, donde se muestra el historial y el campo de entrada de texto.
 *
 * @param viewModel El ViewModel que administra la lógica del chat.
 */
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var userInput by remember { mutableStateOf("") }
    val chatHistory by viewModel.chatHistory.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll al final del chat cuando se agregan nuevos mensajes.
    LaunchedEffect(chatHistory.size) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Migo", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            chatHistory.forEach { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        VoiceInputField(
            userInput = userInput,
            onUserInputChange = { userInput = it },
            onSendMessage = {
                if (userInput.isNotBlank()) {
                    viewModel.sendMessageToApi(userInput)
                    userInput = ""
                }
            }
        )
    }
}

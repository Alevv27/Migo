

package com.example.migo.ui

import ChatBubble
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.migo.ChatViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.times
import com.example.migo.R

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var userInput by remember { mutableStateOf("") }
    val chatHistory by viewModel.chatHistory.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    // Auto-scroll al último mensaje
    LaunchedEffect(chatHistory.size) {
        coroutineScope.launch {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Fondo gris claro
            .padding(16.dp)
            .systemBarsPadding()
            .imePadding()
    ) {
        // 🔵 Fila con la imagen y la barra 🔵
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically // Alinear en el centro verticalmente
        ) {
            // 🔵 Migo SIEMPRE VISIBLE 🔵
            //logo migo
            Image(
                painter = painterResource(id = R.drawable.migoi),
                contentDescription = "Logo de Migo",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la imagen y la barra

            // Barra al lado de la imagen
            Box(
                modifier = Modifier
                    .width(250.dp) // Ancho más pequeño
                    .height(4.dp) // Altura de la barra
                    .background(Color(0xFFCDD4F0)) // Color de la barra

            )
        }
        // 🔻 Lista de mensajes, desplazable 🔻
        Box(
            modifier = Modifier
                .weight(1f) // Permite que solo esto sea desplazable
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                reverseLayout = false
            ) {
                items(chatHistory) { message ->
                    ChatBubble(message)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔵 Campo de entrada SIEMPRE ABAJO 🔵
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

//codigo de respaldo
/*
package com.example.migo.ui

import ChatBubble
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.migo.ChatViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.times
import com.example.migo.R

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    var userInput by remember { mutableStateOf("") }
    val chatHistory by viewModel.chatHistory.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    // Auto-scroll al último mensaje
    LaunchedEffect(chatHistory.size) {
        coroutineScope.launch {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00BCD4)) // Fondo gris claro
            .padding(16.dp)
            .systemBarsPadding()
            .imePadding(),


    ) {


        // 🔵 Migo SIEMPRE VISIBLE 🔵
        //logo migo
        Image(
            painter = painterResource(id = R.drawable.migoi),
            contentDescription = "Logo de Migo",
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)

        )


        // 🔻 Lista de mensajes, desplazable 🔻
        Box(
            modifier = Modifier
                .weight(1f) // Permite que solo esto sea desplazable
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                reverseLayout = false
            ) {
                items(chatHistory) { message ->
                    ChatBubble(message)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔵 Campo de entrada SIEMPRE ABAJO 🔵
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
* */
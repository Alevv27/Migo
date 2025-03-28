/*
package com.example.migo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migo.ChatMessage

/**
 * Componente que representa un mensaje en el chat.
 *
 * @param message Un objeto ChatMessage con el contenido y el tipo de mensaje.
 */
@Composable
fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.text,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .background(
                    if (message.isUser) MaterialTheme.colorScheme.primary else Color(0xFF888888),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        )
    }
}
*/
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migo.ChatMessage
/*
@Composable
fun ChatBubble(message: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        // Mostrar la fecha y hora del mensaje
        Text(
            text = message.timestamp,
            fontSize = 12.sp,
            color = Color(0xFF000000),// hora
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Box(
            modifier = Modifier
                .background(
                    if (message.isUser) MaterialTheme.colorScheme.primary else Color(0xFFCDD4F0),//bot
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = Color(0xFF000000)
            )
        }
    }
}
*/
@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mostrar imagen solo si el mensaje es de la IA
        if (!message.isUser) {
            Image(
                painter = painterResource(id = message.profileImage),
                contentDescription = "Avatar de Migo AI",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
        ) {
            // Mostrar la fecha y hora del mensaje
            Text(
                text = message.timestamp,
                fontSize = 12.sp,
                color = Color(0xFF000000),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Box(
                modifier = Modifier
                    .background(
                        if (message.isUser) MaterialTheme.colorScheme.primary else Color(0xFFCDD4F0),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = Color(0xFF000000)
                )
            }
        }

        // Mostrar imagen del usuario si es un mensaje suyo
        if (message.isUser) {
            Image(
                painter = painterResource(id = message.profileImage),
                contentDescription = "Avatar del usuario",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

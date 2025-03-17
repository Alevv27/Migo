
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

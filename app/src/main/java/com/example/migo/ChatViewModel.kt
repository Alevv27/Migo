/*
package com.example.migo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _chatResponse = MutableStateFlow("")
    val chatResponse = _chatResponse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun sendMessageToApi(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                val response = repository.getChatResponse(prompt)
                _chatResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
*/

package com.example.migo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Representa un mensaje en el chat.
 * @param text Contenido del mensaje.
 * @param isUser Indica si el mensaje fue enviado por el usuario (true) o por la IA (false).
 */

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String = getCurrentTime(),
    val profileImage: Int // Imagen del usuario o IA
)

/*Para mostrar las fechas*/
fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("EEE, d MMM  hh:mm a", Locale.getDefault())
    return sdf.format(Date())
}
/**
 * ViewModel encargado de gestionar el historial de chat y la comunicación con la API.
 */
class ChatViewModel : ViewModel() {
    private val repository = ChatRepository() // Repositorio que maneja las solicitudes a la API.

    // Estado que almacena el historial del chat.
    private val _chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatHistory = _chatHistory.asStateFlow()

    // Estado que indica si la aplicación está cargando una respuesta.
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Estado que almacena los mensajes de error.
    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()
/*
    init {
        // Agregar un mensaje de bienvenida cuando se inicia la aplicación
        _chatHistory.value = listOf(
            ChatMessage("¡Hola! Soy Migo AI 🤖, tu asistente en salud. ¿En qué puedo ayudarte hoy?", isUser = false)
        )
    }*/
    init {
    // Agregar un mensaje de bienvenida cuando se inicia la aplicación
        _chatHistory.value = listOf(
            ChatMessage(
                text = "¡Hola! Soy Migo AI 🤖, tu asistente en salud. ¿En qué puedo ayudarte hoy?",
                isUser = false,
                profileImage = R.drawable.migoi // Imagen de perfil de la IA
            )
        )
    }
/*
    fun sendMessageToApi(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            // Agregar mensaje del usuario con timestamp
            _chatHistory.value = _chatHistory.value + ChatMessage(prompt, isUser = true)

            try {
                val response = repository.getChatResponse(prompt)
                val aiResponse = response.ifBlank { "Lo siento, no tengo una respuesta en este momento." }

                // Agregar respuesta de la IA con timestamp
                _chatHistory.value = _chatHistory.value + ChatMessage("Migo AI: $aiResponse", isUser = false)
            } catch (e: Exception) {
                _errorMessage.value = "Hubo un problema al obtener la respuesta."

                _chatHistory.value = _chatHistory.value + ChatMessage(
                    "Migo AI: Hubo un problema al obtener la respuesta.",
                    isUser = false
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
*/
fun sendMessageToApi(prompt: String) {
    viewModelScope.launch {
        _isLoading.value = true
        _errorMessage.value = ""

        // Agregar mensaje del usuario con su imagen de perfil
        _chatHistory.value = _chatHistory.value + ChatMessage(
            text = prompt,
            isUser = true,
            profileImage = R.drawable.foto // Imagen del usuario
        )

        try {
            val response = repository.getChatResponse(prompt)
            val aiResponse = response.ifBlank { "Lo siento, no tengo una respuesta en este momento." }

            // Agregar respuesta de la IA con su imagen de perfil
            _chatHistory.value = _chatHistory.value + ChatMessage(
                text = "Migo AI: $aiResponse",
                isUser = false,
                profileImage = R.drawable.migoi // Imagen de la IA
            )
        } catch (e: Exception) {
            _errorMessage.value = "Hubo un problema al obtener la respuesta."

            _chatHistory.value = _chatHistory.value + ChatMessage(
                text = "Migo AI: Hubo un problema al obtener la respuesta.",
                isUser = false,
                profileImage = R.drawable.migoi // Imagen de la IA
            )
        } finally {
            _isLoading.value = false
        }
    }
}

}

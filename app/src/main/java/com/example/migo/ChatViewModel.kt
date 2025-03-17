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

/**
 * Representa un mensaje en el chat.
 * @param text Contenido del mensaje.
 * @param isUser Indica si el mensaje fue enviado por el usuario (true) o por la IA (false).
 */
data class ChatMessage(val text: String, val isUser: Boolean)

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

    /**
     * Envía un mensaje a la API y actualiza el historial del chat.
     * @param prompt El mensaje del usuario que se enviará a la API.
     */
    fun sendMessageToApi(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true // Indica que la solicitud está en proceso.
            _errorMessage.value = "" // Resetea el mensaje de error.

            // Agrega el mensaje del usuario al historial.
            _chatHistory.value = _chatHistory.value + ChatMessage(prompt, isUser = true)

            try {
                // Intenta obtener una respuesta de la IA.
                val response = repository.getChatResponse(prompt)

                // Si la respuesta es vacía o nula, muestra un mensaje predeterminado.
                val aiResponse = response.ifBlank { "Lo siento, no tengo una respuesta en este momento." }

                // Agrega la respuesta de la IA al historial.
                _chatHistory.value = _chatHistory.value + ChatMessage(aiResponse, isUser = false)
            } catch (e: Exception) {
                // Captura cualquier error y muestra un mensaje amigable.
                _errorMessage.value = "Hubo un problema al obtener la respuesta. Inténtalo más tarde."

                // Opcional: Agregar mensaje de error en el chat.
                _chatHistory.value = _chatHistory.value + ChatMessage(
                    "Hubo un problema al obtener la respuesta. Inténtalo más tarde.",
                    isUser = false
                )
            } finally {
                _isLoading.value = false // Indica que la carga ha finalizado.
            }
        }
    }
}

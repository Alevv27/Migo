package com.example.migo

import android.util.Log

class ChatRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getChatResponse(prompt: String): String {
        return try {
            val response = apiService.sendMessage(ChatRequest(prompt))

            if (response.isNotEmpty() && response[0].generated_text.isNotBlank()) {
                response[0].generated_text.trim()
            } else {
                "La IA no pudo generar una respuesta, intenta de nuevo."
            }
        } catch (e: Exception) {
            Log.e("ChatRepository", "Error en la solicitud: ${e.message}")
            "Error al obtener respuesta: ${e.message}"
        }
    }

}

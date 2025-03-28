/*
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
*/
/*
package com.example.migo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getChatResponse(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = ChatRequest(
                    model = "deepseek/deepseek-r1-zero:free",
                    messages = listOf(
                        mapOf("role" to "user", "content" to prompt)
                    )
                )

                val response = apiService.sendMessage(request)

                response.choices.firstOrNull()?.message?.content?.trim()
                    ?: "La IA no pudo generar una respuesta, intenta de nuevo."
            } catch (e: Exception) {
                Log.e("ChatRepository", "Error en la solicitud: ${e.message}")
                "Error al obtener respuesta: ${e.message}"
            }
        }
    }
}
*/
/*
package com.example.migo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getChatResponse(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = ChatRequest(
                    model = "anthropic/claude-3.5-haiku", // Puedes cambiar a otro modelo si es necesario
                    messages = listOf(
                        mapOf("role" to "user", "content" to prompt)
                    )
                )

                val response = apiService.sendMessage(request)

                // Mostrar respuesta completa en Logcat
                Log.d("ChatRepository", "Respuesta completa: $response")

                // Extraer contenido del primer mensaje recibido
                val content = response.choices.firstOrNull()?.message?.content

                if (content.isNullOrBlank()) {
                    "La IA no pudo generar una respuesta válida, intenta de nuevo."
                } else {
                    content.trim()
                }

            } catch (e: Exception) {
                Log.e("ChatRepository", "Error en la solicitud: ${e.message}")
                "Error al obtener respuesta: ${e.message}"
            }
        }
    }
}
*/
package com.example.migo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository {
    private val apiService = RetrofitClient.instance

    // Lista de palabras clave relacionadas con psicología
    private val keywordsPsychology = listOf(
        "ansiedad", "estrés", "depresión", "autoestima", "emociones", "terapia",
        "sentimientos", "tristeza", "felicidad", "relaciones", "psicología", "miedo",
        "motivación", "trauma", "duelo", "angustia", "pensamientos", "hábitos", "autoayuda"
    )

    // Lista de temas fuera del enfoque de la IA
    private val forbiddenTopics = listOf(
        "fútbol", "programación", "política", "dinero", "negocios", "criptomonedas", "android",
        "java", "kotlin", "deportes", "cocina", "videojuegos", "cine", "música", "tecnología"
    )

    suspend fun getChatResponse(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val containsPsychology = keywordsPsychology.any { prompt.contains(it, ignoreCase = true) }
                val containsForbidden = forbiddenTopics.any { prompt.contains(it, ignoreCase = true) }

                if (containsForbidden && !containsPsychology) {
                    return@withContext "Lo siento, pero mi enfoque es la psicología. ¿Tienes alguna inquietud emocional o psicológica con la que pueda ayudarte?"
                }

                val request = ChatRequest(
                    model = "anthropic/claude-3.5-haiku",
                    messages = listOf(
                        mapOf("role" to "user", "content" to prompt)
                    )
                )

                val response = apiService.sendMessage(request)

                response.choices.firstOrNull()?.message?.content?.trim()
                    ?: "La IA no pudo generar una respuesta, intenta de nuevo."
            } catch (e: Exception) {
                Log.e("ChatRepository", "Error en la solicitud: ${e.message}")
                "Error al obtener respuesta: ${e.message}"
            }
        }
    }
}

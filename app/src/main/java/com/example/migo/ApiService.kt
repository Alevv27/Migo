/*
package com.example.migo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// URL corregida (sin la barra al final)
const val BASE_URL = "https://api-inference.huggingface.co/"

// Modelos de datos
data class ChatRequest(
    val inputs: String,  // Ahora es un simple String en lugar de un Map
    val parameters: Map<String, Any> = mapOf("temperature" to 0.1)
)

data class ChatResponse(val generated_text: String)

// Interfaz para la API
interface ApiService {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json" // Agregado para evitar errores 400
    )
    @POST("models/google/flan-t5-small")
    suspend fun sendMessage(@Body request: ChatRequest): List<ChatResponse> // ✅ Devuelve una lista
}

// Cliente Retrofit
object RetrofitClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
*/
package com.example.migo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// URL correcta de OpenRouter
const val BASE_URL = "https://openrouter.ai/api/"

// Modelos de datos
data class ChatRequest(
    val model: String = "anthropic/claude-3.5-haiku",
    val messages: List<Map<String, String>>,  // Lista de mensajes
    val temperature: Double = 0.7
)

data class ChatResponse(val choices: List<Choice>)
data class Choice(val message: Message)
data class Message(val content: String)

// Interfaz de la API
interface ApiService {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    @POST("v1/chat/completions")  // URL correcta
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}

// Cliente Retrofit corregido
object RetrofitClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
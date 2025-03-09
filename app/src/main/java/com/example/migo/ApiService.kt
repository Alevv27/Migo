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



package com.example.migo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.util.Locale

/**
 * Componente de entrada de texto con soporte para reconocimiento de voz.
 *
 * @param userInput El texto actual ingresado por el usuario.
 * @param onUserInputChange Función que se llama cuando cambia el texto ingresado.
 * @param onSendMessage Función que se llama para enviar el mensaje.
 */
@Composable
fun VoiceInputField(
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var isListening by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startListening(speechRecognizer, onUserInputChange)
            }
        }
    )

    // Función que verifica permiso y, si es necesario, lo solicita.
    fun checkPermissionAndStartListening() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) -> {
                startListening(speechRecognizer, onUserInputChange)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón para activar/desactivar el reconocimiento de voz.
        IconButton(
            onClick = {
                if (isListening) {
                    speechRecognizer.stopListening()
                    isListening = false
                } else {
                    checkPermissionAndStartListening()
                    isListening = true
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Grabar Audio",
                tint = if (isListening) Color.Red else MaterialTheme.colorScheme.primary
            )
        }

        // Campo de entrada de texto.
        OutlinedTextField(
            value = userInput,
            onValueChange = onUserInputChange,
            placeholder = { Text("Escribe o usa el micrófono...") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSendMessage() }),
            shape = RoundedCornerShape(20.dp)
        )

        // Botón para enviar el mensaje.
        IconButton(
            onClick = onSendMessage,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Enviar Mensaje",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Inicia el reconocimiento de voz usando SpeechRecognizer.
 *
 * @param speechRecognizer La instancia de SpeechRecognizer.
 * @param onUserInputChange Función para actualizar el texto reconocido.
 */
fun startListening(speechRecognizer: SpeechRecognizer, onUserInputChange: (String) -> Unit) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Hablando...")
    }

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {}
        override fun onResults(results: Bundle?) {
            results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.let { matches ->
                if (matches.isNotEmpty()) {
                    onUserInputChange(matches[0])
                }
            }
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    })

    speechRecognizer.startListening(intent)
}

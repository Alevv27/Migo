import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Pantalla1(navigateToNextScreen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pantalla1")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = navigateToNextScreen) {
            Text("Ir a la siguiente pantalla")
        }
    }
}




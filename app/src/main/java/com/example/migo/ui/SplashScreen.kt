/*
package com.example.migo.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.migo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SplashScreen(navController: NavController) {
    // Definir las tres imágenes de introducción
    val splashImages = listOf(R.drawable.a, R.drawable.a, R.drawable.c)

    // Controlador del paginador horizontal
    val pagerState = rememberPagerState(pageCount = { splashImages.size })

    // Scope para manejar animaciones de desplazamiento
    val coroutineScope = rememberCoroutineScope()

    // Contenedor principal
    Box(modifier = Modifier.fillMaxSize()) {
        // Paginador horizontal para deslizar entre pantallas
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo a pantalla completa
                Image(
                    painter = painterResource(id = splashImages[page]),
                    contentDescription = "Pantalla ${page + 1}",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                // Botón "COMENZAR" solo en la última pantalla
                if (page == splashImages.size - 1) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 50.dp)
                    ) {
                        Button(
                            onClick = {
                                // Navegar a la pantalla de chat
                                navController.navigate("chat") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4169E1)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "COMENZAR",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                // Indicadores de página (puntos en la parte inferior)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    PageIndicator(
                        pageCount = splashImages.size,
                        currentPage = pagerState.currentPage,
                        onPageSelected = { selectedPage ->
                            // Animar el desplazamiento a la página seleccionada
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedPage)
                            }
                        }
                    )
                }
            }
        }
    }
}

// Componente para mostrar los indicadores de página (puntos)
@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Crear un indicador (punto) para cada página
        for (i in 0 until pageCount) {
            val color = if (i == currentPage) Color.White else Color.White.copy(alpha = 0.5f)
            val size = if (i == currentPage) 10.dp else 8.dp

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(size)
                    .background(color = color, shape = CircleShape)
                    .clickable { onPageSelected(i) }
            )
        }
    }
}

*/
package com.example.migo.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.migo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SplashScreen(navController: NavController) {
    val splashImages = listOf(R.drawable.p1, R.drawable.p2, R.drawable.p3)
    val pagerState = rememberPagerState(pageCount = { splashImages.size })
    val coroutineScope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = splashImages[page]),
                    contentDescription = "Pantalla ${page + 1}",
                    contentScale = ContentScale.Crop, // Ajuste responsivo sin distorsión
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight) // Se ajusta a la altura de la pantalla
                )

                if (page == splashImages.size - 1) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 50.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("chat") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4169E1)
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "COMENZAR",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    PageIndicator(
                        pageCount = splashImages.size,
                        currentPage = pagerState.currentPage,
                        onPageSelected = { selectedPage ->
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedPage)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until pageCount) {
            val color = if (i == currentPage) Color.White else Color.White.copy(alpha = 0.5f)
            val size = if (i == currentPage) 10.dp else 8.dp

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(size)
                    .background(color = color, shape = CircleShape)
                    .clickable { onPageSelected(i) }
            )
        }
    }
}

package com.example.rickymorty.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.ui.components.InfoCard
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.rickymorty.data.NameContainer
import com.example.rickymorty.ui.theme.RickYMortyTheme
import com.example.rickymorty.R

fun dialPhoneNumber(context: android.content.Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phoneNumber".toUri()
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

@Composable
fun PersonajeScreen(
    personaje: PersonajeInfo
) {

    val context = LocalContext.current

    // Animación de desplazamiento horizontal
    val infiniteTransition = rememberInfiniteTransition()

    val desplazamientoBrush by infiniteTransition.animateFloat(
        initialValue = -400f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val efectoBrush = Brush.linearGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFF0D3B12),
                0.15f to Color(0xFF1B5E20),
                0.50f to Color(0xFF00E676),
                0.85f to Color(0xFF1B5E20),
                1.0f to Color(0xFF0D3B12)
            ),
    start = Offset(desplazamientoBrush + 2400f, 0f),
    end = Offset(desplazamientoBrush, 1400f)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // FONDO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(efectoBrush)
        )
        // Contenedor redondeado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(MaterialTheme.colorScheme.surface)
        )
        // Imagen con borde animado
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
                .size(175.dp)
                .shadow(25.dp, CircleShape)
                .background(efectoBrush, CircleShape)
                .padding(6.dp)
                .clickable { dialPhoneNumber(context, personaje.id.toString()) },
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = personaje.image,
                contentDescription = personaje.name,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        // Información
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 340.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Nombre con brush
            Text(
                text = personaje.name.uppercase(),
                style = TextStyle(
                    brush = efectoBrush,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Estado
            Surface(
                color = if (personaje.status == "Alive") Color(0xFFE8F5E9) else Color(0xFFFBE9E7),
                shape = CircleShape
            ) {
                Text(
                    text = personaje.status,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (personaje.status == "Alive") Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 24.dp),
                thickness = 0.5.dp
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCard(
                        label = "Especie",
                        value = personaje.species,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        label = "Género",
                        value = personaje.gender,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCard(
                        label = "Origen",
                        value = personaje.origin.name,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        label = "Ubicación",
                        value = personaje.location.name,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PersonajeScreenPreview(){
    RickYMortyTheme{
        PersonajeScreen(
            PersonajeInfo(
                1,
                "Rick Sanchez",
                "Alive",
                "Human",
                "Male",
                NameContainer("Earth (C-137)"),
                NameContainer("Citadel of Ricks"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                null
)) }
}
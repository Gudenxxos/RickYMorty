package com.example.rickymorty.ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickymorty.data.NameContainer
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.ui.components.InfoCard
import com.example.rickymorty.ui.theme.primaryLight
import com.example.rickymorty.ui.theme.onPrimaryLight
import kotlin.math.cos
import kotlin.math.sin
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext




fun dialPhoneNumber(context: android.content.Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
@Composable
fun PersonajeScreen(
    personaje: PersonajeInfo
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PortalAnimation")

    // Colores agresivos neón (Radioactividad pura)
    val PortalBlack = Color(0xFF000F00)
    val PortalGreen = Color(0xFF00FF00) // Verde neón puro
    val PortalNeon = Color(0xFFCCFF00)  // Amarillo lima radioactivo

    // Animación de rotación continua para dar dinamismo
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (Math.PI * 2).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    // Ajuste del centro y radio para el círculo
    val centerX = 540f // Ajustado para centrar mejor visualmente
    val centerY = 400f
    val radius = 750f // Aumentado para que el degradado sea más suave y cubra más espacio

    // Gradiente RADIAL con colorStops para un degradado más sutil
    val vortexBrush = Brush.radialGradient(
        0.0f to PortalNeon,
        0.3f to PortalGreen,
        1.0f to PortalBlack,
        center = Offset(
            x = centerX + cos(angle) * 120f, // Movimiento más amplio para que se note el barrido de color
            y = centerY + sin(angle) * 120f
        ),
        radius = radius
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // FONDO CIRCULAR (Portal)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(vortexBrush)
        )

        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(MaterialTheme.colorScheme.surface)
        )

        val context = LocalContext.current
        // Imagen de perfil con MARCO CIRCULAR
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
                .size(175.dp)
                .shadow(30.dp, CircleShape)
                .background(vortexBrush, CircleShape)
                .padding(7.dp)
                .clickable { dialPhoneNumber(context, personaje.id.toString()) },
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = personaje.image,
                contentDescription = personaje.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        // 📄 Información del personaje
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 340.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // NOMBRE CON GRADIENTE
            Text(
                text = personaje.name.uppercase(),
                style = TextStyle(
                    brush = vortexBrush,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Chip de estado
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

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN QUE USA TUS COLORES VERDES DIRECTAMENTE

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider(modifier = Modifier.padding(bottom = 24.dp), thickness = 0.5.dp)

            // 🟢 Info Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCard("Especie", personaje.species)
                    InfoCard("Género", personaje.gender)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCard("Origen", personaje.origin.name)
                    InfoCard("Ubicación", personaje.location.name)
                }
            }
        }
    }
}
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonajeScreenPreview() {
    PersonajeScreen(
        personaje = PersonajeInfo(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = NameContainer("Earth (C-137)"),
            location = NameContainer("Citadel of Ricks"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            type = null
        )
    )
}*/

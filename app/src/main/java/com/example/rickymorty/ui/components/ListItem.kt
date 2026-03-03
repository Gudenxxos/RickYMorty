package com.example.rickymorty.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickymorty.data.NameContainer
import com.example.rickymorty.data.PersonajeInfo
import com.example.rickymorty.ui.theme.RickYMortyTheme
import com.example.rickymorty.R


@Composable
fun ListItem(
    personaje: PersonajeInfo,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen circular
            AsyncImage(
                model = personaje.image,
                contentDescription = personaje.name,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Nombre, Género, Especie y Ubicación
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${personaje.name} - (${personaje.gender})",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${personaje.species} - ${personaje.location.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
            // Icono de flecha
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Ver Detalle del Personaje",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.size(25.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    RickYMortyTheme(dynamicColor = false) {
        ListItem(personaje = PersonajeInfo(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = NameContainer("Earth (C-137)"),
            location = NameContainer("Citadel of Ricks"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            type = null
        )){
        }
    }
}

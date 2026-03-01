package com.example.rickymorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.rickymorty.ui.theme.primaryLight
import com.example.rickymorty.ui.theme.primaryContainerLight

@Composable
fun RowScope.InfoCard(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(primaryContainerLight.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {

        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = primaryLight,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
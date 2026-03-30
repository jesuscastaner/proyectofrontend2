package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TabItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val weight = if (selected) {
        FontWeight.Bold
    } else {
        FontWeight.Normal
    }

    val color = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = weight,
            color = color,
        )
        if (selected) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(3.dp)
                    .fillMaxWidth(0.5f)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(1.5.dp)
                    )
            )
        }
    }
}

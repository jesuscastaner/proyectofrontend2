package net.unir.proyectofrontend2.ui.components.icons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostRepliesIcon(
    modifier: Modifier = Modifier,
    repliesCount: Int = 0,
) {
    val color = if (repliesCount == 0) {
        MaterialTheme.colorScheme.outlineVariant
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.Chat,
            contentDescription = "Replies",
            modifier = modifier,
            tint = color,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = repliesCount.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = color,
        )
    }
}

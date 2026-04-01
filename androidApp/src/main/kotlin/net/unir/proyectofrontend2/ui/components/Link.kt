package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Link(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    onClick: () -> Unit,
) {
    Text(
        text,
        style = style,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = fontWeight,
        modifier = modifier.clickable { onClick() }
    )
}

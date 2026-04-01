package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun CircleImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        placeholder = ColorPainter(Color.DarkGray),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
    )
}

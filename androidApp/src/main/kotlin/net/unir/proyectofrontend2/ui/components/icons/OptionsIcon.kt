package net.unir.proyectofrontend2.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OptionsIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.MoreVert,
        contentDescription = "Options",
        modifier = modifier,
    )
}

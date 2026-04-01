package net.unir.proyectofrontend2.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "User",
        modifier = modifier,
    )
}

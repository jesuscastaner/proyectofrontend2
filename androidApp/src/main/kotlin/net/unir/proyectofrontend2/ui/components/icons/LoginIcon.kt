package net.unir.proyectofrontend2.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.AutoMirrored.Default.Login,
        contentDescription = "Login",
        modifier = modifier,
    )
}

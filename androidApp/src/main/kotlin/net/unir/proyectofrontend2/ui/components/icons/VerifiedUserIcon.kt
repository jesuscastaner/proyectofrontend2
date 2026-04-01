package net.unir.proyectofrontend2.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VerifiedUserIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Verified,
        contentDescription = "Verified user",
        modifier = modifier,
        tint = MaterialTheme.colorScheme.primary
    )
}

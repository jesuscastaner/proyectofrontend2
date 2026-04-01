package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LibraryResourceLink(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    publicationYear: Short? = null,
    language: String? = null,
    role: String? = null,
    onClick: () -> Unit,
) {
    Row(modifier = modifier) {
        Link(
            title,
            style = style,
            fontWeight = fontWeight,
            onClick = onClick
        )
        publicationYear?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "(${publicationYear})",
                style = style,
                fontWeight = fontWeight,
            )
        }
        language?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "[${language}]",
                style = style,
                fontWeight = fontWeight,
            )
        }
        role?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "(${role})",
                style = style,
                fontWeight = fontWeight,
            )
        }
    }
}

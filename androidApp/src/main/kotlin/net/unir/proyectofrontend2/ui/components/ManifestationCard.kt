package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.unir.proyectofrontend2.data.model.Manifestation

@Composable
fun ManifestationCard(
    manifestation: Manifestation,
    onClick: (id: Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(manifestation.id) }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                manifestation.title,
                fontWeight = FontWeight.Bold
            )
            manifestation.publicationYear?.let {
                Text(
                    "Publicado: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            manifestation.isbn?.let {
                Text(
                    "ISBN: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (manifestation.agents.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                manifestation.agents.forEach { agent ->
                    Text("- ${agent.name} (${agent.role ?: "unknown role"})")
                }
            }
        }
    }
}


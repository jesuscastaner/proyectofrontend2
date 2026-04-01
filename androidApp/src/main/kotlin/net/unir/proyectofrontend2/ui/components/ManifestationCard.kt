package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import kotlin.collections.forEach

@Composable
fun ManifestationCard(
    manifestation: Manifestation,
    authors: List<PaticipantAgent> = emptyList(),
    onClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    val editors = manifestation.agents.filter {
        it.role.equals("editor", ignoreCase = true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(manifestation.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = manifestation.coverImage,
                contentDescription = "${manifestation.title} cover image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                manifestation.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (authors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                authors.forEach {
                    LibraryResourceLink(
                        title = it.name,
                        onClick = { onAgentClick(it.id) }
                    )
                }
            }
            if (editors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                editors.forEach {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            manifestation.publicationYear?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "(${it})",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

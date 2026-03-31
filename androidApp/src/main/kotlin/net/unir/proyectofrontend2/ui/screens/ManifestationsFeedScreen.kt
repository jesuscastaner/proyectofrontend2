package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationsFeedScreen(
    navigateToManifestationDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: ManifestationsFeedViewModel = koinViewModel()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()
    val agentsMap by viewModel.agentsMap.collectAsStateWithLifecycle()

    AnimatedContent(manifestations.isNotEmpty()) { manifestationsAvailable ->
        if (manifestationsAvailable) {
            ManifestationsFeed(
                manifestations = manifestations,
                agentsMap = agentsMap,
                onManifestationClick = navigateToManifestationDetails,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ManifestationsFeed(
    manifestations: List<Manifestation>,
    modifier: Modifier = Modifier,
    agentsMap: Map<Long, List<PaticipantAgent>>,
    onManifestationClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(manifestations, key = { it.id }) { manifestation ->
            ManifestationCard(
                manifestation = manifestation,
                agents = agentsMap[manifestation.id] ?: emptyList(),
                onClick = onManifestationClick,
                onAgentClick = onAgentClick,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun ManifestationCard(
    manifestation: Manifestation,
    agents: List<PaticipantAgent>,
    onClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    val authors = agents.filter {
        it.role.equals("author", ignoreCase = true)
    }
    val editors = agents.filter {
        it.role.equals("editor", ignoreCase = true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(manifestation.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = manifestation.coverImage,
                contentDescription = "${manifestation.title} cover image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Text(
                manifestation.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            if (authors.isNotEmpty()) {
                authors.forEach { author ->
                    Text(
                        text = author.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable { onAgentClick(author.id) }
                    )
                }
            }
            if (editors.isNotEmpty()) {
                editors.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable { onAgentClick(it.id) }
                    )
                }
            }
            manifestation.publicationYear?.let {
                Text(
                    "Publicado: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

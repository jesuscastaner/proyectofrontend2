package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.ManifestationCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationsFeedScreen(
    navigateToManifestationDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: ManifestationsFeedViewModel = koinViewModel()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()
    val authorsMap by viewModel.authorsMap.collectAsStateWithLifecycle()

    AnimatedContent(manifestations.isNotEmpty()) { manifestationsAvailable ->
        if (manifestationsAvailable) {
            ManifestationsFeed(
                manifestations = manifestations,
                authorsMap = authorsMap,
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
    authorsMap: Map<Long, List<PaticipantAgent>> = emptyMap(),
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
                authors = authorsMap[manifestation.id] ?: emptyList(),
                onClick = onManifestationClick,
                onAgentClick = onAgentClick,
                onAddToShelfClick = { /* TODO */ }
            )
        }
    }
}

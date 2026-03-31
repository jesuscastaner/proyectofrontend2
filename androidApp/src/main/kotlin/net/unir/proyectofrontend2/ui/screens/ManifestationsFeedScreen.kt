package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.ManifestationCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationsFeedScreen(
    navigateToManifestationDetails: (id: Long) -> Unit,
) {
    val viewModel: ManifestationsFeedViewModel = koinViewModel()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()

    AnimatedContent(manifestations.isNotEmpty()) { manifestationsAvailable ->
        if (manifestationsAvailable) {
            ManifestationsFeed(
                manifestations = manifestations,
                onManifestationClick = navigateToManifestationDetails,
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
    onManifestationClick: (id: Long) -> Unit,
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
                onClick = onManifestationClick,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

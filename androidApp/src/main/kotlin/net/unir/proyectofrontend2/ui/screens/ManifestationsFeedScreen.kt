package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.ManifestationCard
import net.unir.proyectofrontend2.ui.components.TabItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationsFeedScreen(
    navigateToPostsFeed: () -> Unit,
    navigateToManifestationDetails: (id: Long) -> Unit,
) {
    val viewModel: ManifestationsFeedViewModel = koinViewModel()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()

    AnimatedContent(manifestations.isNotEmpty()) { manifestationsAvailable ->
        if (manifestationsAvailable) {
            ManifestationsFeed(
                manifestations = manifestations,
                onPostsTabClick = navigateToPostsFeed,
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
    onPostsTabClick: () -> Unit,
    onManifestationClick: (id: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TabItem(
                    text = "Posts",
                    onClick = onPostsTabClick
                )
                TabItem(
                    text = "Manifestations",
                    selected = true,
                    onClick = {},
                )
            }
        }
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

package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationDetailsScreen(
    id: Long,
) {
    val viewModel: ManifestationDetailsViewModel = koinViewModel()
    val manifestation by viewModel.manifestation.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(manifestation != null) { manifestationAvailable ->
        if (manifestationAvailable) {
            ManifestationDetails(
                manifestation = manifestation!!,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManifestationDetails(
    manifestation: Manifestation,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            Text(
                manifestation.title,
                style = MaterialTheme.typography.headlineMedium,
            )
            // ...
        }
        // ...
    }
}

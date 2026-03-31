package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.WorkDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkDetailsScreen(
    id: Long,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: WorkDetailsViewModel = koinViewModel()
    val work by viewModel.work.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(work != null) { manifestationAvailable ->
        if (manifestationAvailable) {
            WorkDetails(
                work = work!!,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkDetails(
    work: Work,
    modifier: Modifier = Modifier,
    onAgentClick: (id: Long) -> Unit = {},
) {
    val (authors, otherAgents) = work.agents.partition {
        it.role.equals("author", ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            AsyncImage(
                model = "https://picsum.photos/200/300?random=1",
                contentDescription = work.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                work.title,
                style = MaterialTheme.typography.headlineMedium,
            )
            if (authors.isNotEmpty()) {
                authors.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { onAgentClick(it.id) }
                    )
                }
            } else {
                Text(
                    text = "Unknown author",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )
            }
            work.publicationYear?.let {
                Text(
                    "Published: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            otherAgents.forEach {
                Text(
                    text = "${it.name} (${it.role})",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { onAgentClick(it.id) }
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

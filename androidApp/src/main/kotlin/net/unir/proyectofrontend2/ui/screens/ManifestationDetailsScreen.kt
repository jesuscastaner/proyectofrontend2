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
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManifestationDetailsScreen(
    id: Long,
    navigateToExpressionDetails: (id: Long) -> Unit,
    navigateToWorkDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: ManifestationDetailsViewModel = koinViewModel()
    val manifestation by viewModel.manifestation.collectAsStateWithLifecycle()
    val expression by viewModel.expression.collectAsStateWithLifecycle()
    val work by viewModel.work.collectAsStateWithLifecycle()
    val agents by viewModel.agents.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(manifestation != null) { manifestationAvailable ->
        if (manifestationAvailable) {
            ManifestationDetails(
                manifestation = manifestation!!,
                expression = expression!!,
                work = work!!,
                agents = agents,
                onExpressionClick = navigateToExpressionDetails,
                onWorkClick = navigateToWorkDetails,
                onAgentClick = navigateToAgentDetails,
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
    expression: Expression,
    work: Work,
    agents: List<PaticipantAgent>,
    modifier: Modifier = Modifier,
    onExpressionClick: (id: Long) -> Unit = {},
    onWorkClick: (id: Long) -> Unit = {},
    onAgentClick: (id: Long) -> Unit = {},
) {
    val authors = agents.filter {
        it.role.equals("author", ignoreCase = true)
    }
    val editors = agents.filter {
        it.role.equals("editor", ignoreCase = true)
    }
    val otherAgents = agents.filterNot {
        it.role.equals("author", ignoreCase = true) ||
            it.role.equals("editor", ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            AsyncImage(
                model = manifestation.coverImage,
                contentDescription = "${manifestation.title} cover image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                manifestation.title,
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
            if (editors.isNotEmpty()) {
                Text(
                    text = "Edition:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
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
                    "Published: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            manifestation.isbn?.let {
                Text(
                    "ISBN: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            otherAgents.forEach { agent ->
                Text(
                    text = "${agent.name} (${agent.role})",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { onAgentClick(agent.id) }
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Text(
                "Expression : ${expression.title}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onExpressionClick(expression.id) },
            )
            Text(
                "Work : ${work.title}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onWorkClick(work.id) },
            )
        }
    }
}

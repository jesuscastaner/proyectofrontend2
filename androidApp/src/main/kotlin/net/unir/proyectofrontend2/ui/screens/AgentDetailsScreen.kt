package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.AgentDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.FormattedLifeSpan
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.LibraryResourceLink
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AgentDetailsScreen(
    id: Long,
    navigateToWorkDetails: (id: Long) -> Unit,
    navigateToExpressionDetails: (id: Long) -> Unit,
    navigateToManifestationDetails: (id: Long) -> Unit,
) {
    val viewModel: AgentDetailsViewModel = koinViewModel()
    val agent by viewModel.agent.collectAsStateWithLifecycle()
    val works by viewModel.works.collectAsStateWithLifecycle()
    val expressions by viewModel.expressions.collectAsStateWithLifecycle()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(agent != null) { agentAvailable ->
        if (agentAvailable) {
            AgentDetails(
                agent = agent!!,
                works = works,
                expressions = expressions,
                manifestations = manifestations,
                onWorkClick = navigateToWorkDetails,
                onExpressionClick = navigateToExpressionDetails,
                onManifestationClick = navigateToManifestationDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgentDetails(
    agent: Agent,
    works: List<Work>,
    expressions: List<Expression>,
    manifestations: List<Manifestation>,
    modifier: Modifier = Modifier,
    onWorkClick: (id: Long) -> Unit,
    onExpressionClick: (id: Long) -> Unit,
    onManifestationClick: (id: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            AsyncImage(
                model = agent.profilePic,
                contentDescription = "${agent.name} profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                agent.name,
                style = MaterialTheme.typography.headlineMedium,
            )
            if (agent.birthYear != null || agent.deathYear != null) {
                Spacer(modifier = Modifier.height(12.dp))
                FormattedLifeSpan(
                    birthYear = agent.birthYear,
                    deathYear = agent.deathYear,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
        if (works.isNotEmpty()) {
            item {
                Heading("Works")
            }
            items(works, key = { it.id }) { work ->
                val role = work.agents
                    .firstOrNull { it.id == agent.id }
                    ?.role

                Spacer(modifier = Modifier.height(16.dp))
                LibraryResourceLink(
                    title = work.title,
                    publicationYear = work.publicationYear,
                    role = role,
                    onClick = { onWorkClick(work.id) },
                )
            }
        }
        if (
            works.isNotEmpty() &&
            expressions.isNotEmpty()
        ) item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (expressions.isNotEmpty()) {
            item {
                Heading("Expressions")
            }
            items(expressions, key = { it.id }) { expression ->
                val role = expression.agents
                    .firstOrNull { it.id == agent.id }
                    ?.role

                Spacer(modifier = Modifier.height(16.dp))
                LibraryResourceLink(
                    title = expression.title,
                    publicationYear = expression.publicationYear,
                    language = expression.language,
                    role = role,
                    onClick = { onExpressionClick(expression.id) }
                )
            }
        }
        if (
            expressions.isNotEmpty() &&
            manifestations.isNotEmpty()
        ) item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (manifestations.isNotEmpty()) {
            item {
                Heading("Manifestations")
            }
            items(manifestations, key = { it.id }) { manifestation ->
                val role = manifestation.agents
                    .firstOrNull { it.id == agent.id }
                    ?.role

                Spacer(modifier = Modifier.height(16.dp))
                LibraryResourceLink(
                    title = manifestation.title,
                    publicationYear = manifestation.publicationYear,
                    role = role,
                    onClick = { onManifestationClick(manifestation.id) }
                )
            }
        }
        if (
            works.isNotEmpty() ||
            expressions.isNotEmpty() ||
            manifestations.isNotEmpty()
        ) item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

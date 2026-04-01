package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.ExpressionDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.LibraryResourceLink
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpressionDetailsScreen(
    id: Long,
    navigateToWorkDetails: (id: Long) -> Unit,
    navigateToManifestationDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: ExpressionDetailsViewModel = koinViewModel()
    val expression by viewModel.expression.collectAsStateWithLifecycle()
    val work by viewModel.work.collectAsStateWithLifecycle()
    val manifestations by viewModel.manifestations.collectAsStateWithLifecycle()
    val agents by viewModel.agents.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(
        expression != null &&
            work != null
    ) { expressionAvailable ->
        if (expressionAvailable) {
            ExpressionDetails(
                expression = expression!!,
                work = work!!,
                manifestations = manifestations,
                agents = agents,
                onWorkClick = navigateToWorkDetails,
                onManifestationClick = navigateToManifestationDetails,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpressionDetails(
    expression: Expression,
    work: Work,
    manifestations: List<Manifestation>,
    modifier: Modifier = Modifier,
    agents: List<PaticipantAgent> = emptyList(),
    onWorkClick: (id: Long) -> Unit,
    onManifestationClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    val authors = agents.filter {
        it.role.equals("author", ignoreCase = true)
    }
    val otherAgents = agents.filterNot {
        it.role.equals("author", ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            Text(
                expression.title,
                style = MaterialTheme.typography.headlineLarge,
            )
            if (authors.isNotEmpty()) {
                authors.forEach {
                    Spacer(modifier = Modifier.height(16.dp))
                    LibraryResourceLink(
                        title = it.name,
                        style = MaterialTheme.typography.headlineSmall,
                        onClick = { onAgentClick(it.id) },
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Unknown author",
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )
            }
            expression.language?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Language: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            expression.publicationYear?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Published: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            otherAgents.forEach {
                Spacer(modifier = Modifier.height(8.dp))
                LibraryResourceLink(
                    title = it.name,
                    role = it.role,
                    style = MaterialTheme.typography.bodyLarge,
                    onClick = { onAgentClick(it.id) },
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Heading("Work")
            Spacer(modifier = Modifier.height(16.dp))
            LibraryResourceLink(
                title = work.title,
                publicationYear = work.publicationYear,
                onClick = { onWorkClick(work.id) },
            )
        }
        if (manifestations.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Heading("Manifestations of this expression")
            }
            items(manifestations, key = { it.id }) { manifestation ->
                Spacer(modifier = Modifier.height(16.dp))
                LibraryResourceLink(
                    title = manifestation.title,
                    publicationYear = manifestation.publicationYear,
                    onClick = { onManifestationClick(manifestation.id) }
                )
            }
        }
        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

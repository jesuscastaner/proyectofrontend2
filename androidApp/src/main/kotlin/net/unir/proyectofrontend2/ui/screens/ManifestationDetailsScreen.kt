package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.LibraryResourceLink
import net.unir.proyectofrontend2.ui.components.icons.AddToShelfIcon
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

    AnimatedContent(
        manifestation != null &&
            expression != null &&
            work != null
    ) { manifestationAvailable ->
        if (manifestationAvailable) {
            ManifestationDetails(
                manifestation = manifestation!!,
                expression = expression!!,
                work = work!!,
                agents = agents,
                onExpressionClick = navigateToExpressionDetails,
                onWorkClick = navigateToWorkDetails,
                onAgentClick = navigateToAgentDetails,
                onAddToShelfClick = { /* TODO */ }
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
    modifier: Modifier = Modifier,
    agents: List<PaticipantAgent> = emptyList(),
    onExpressionClick: (id: Long) -> Unit,
    onWorkClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
    onAddToShelfClick: (id: Long) -> Unit,
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
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = manifestation.coverImage,
                    contentDescription = "${manifestation.title} cover image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                )
                IconButton(
                    onClick = { onAddToShelfClick(manifestation.id) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AddToShelfIcon()
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                manifestation.title,
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
            manifestation.publicationYear?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Published: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            if (editors.isNotEmpty()) {
                editors.forEach {
                    Spacer(modifier = Modifier.height(8.dp))
                    LibraryResourceLink(
                        title = it.name,
                        role = it.role,
                        style = MaterialTheme.typography.bodyLarge,
                        onClick = { onAgentClick(it.id) },
                    )
                }
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
            manifestation.isbn?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "ISBN: $it",
                    style = MaterialTheme.typography.bodyMedium
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
            Spacer(modifier = Modifier.height(16.dp))
            Heading("Expression")
            Spacer(modifier = Modifier.height(16.dp))
            LibraryResourceLink(
                title = expression.title,
                publicationYear = expression.publicationYear,
                language = expression.language,
                onClick = { onExpressionClick(work.id) },
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

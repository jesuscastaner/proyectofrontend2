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
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.WorkDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.LibraryResourceLink
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkDetailsScreen(
    id: Long,
    navigateToExpressionDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: WorkDetailsViewModel = koinViewModel()
    val work by viewModel.work.collectAsStateWithLifecycle()
    val expressions by viewModel.expressions.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(work != null) { workAvailable ->
        if (workAvailable) {
            WorkDetails(
                work = work!!,
                expressions = expressions,
                onExpressionClick = navigateToExpressionDetails,
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
    expressions: List<Expression>,
    modifier: Modifier = Modifier,
    onExpressionClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
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
            Text(
                work.title,
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
            work.publicationYear?.let {
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
        }
        if (expressions.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Heading("Expressions of this work")
            }
            items(expressions, key = { it.id }) { expression ->
                Spacer(modifier = Modifier.height(16.dp))
                LibraryResourceLink(
                    title = expression.title,
                    publicationYear = expression.publicationYear,
                    language = expression.language,
                    onClick = { onExpressionClick(expression.id) }
                )
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

}

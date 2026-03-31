package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.presentation.viewmodel.ExpressionsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpressionsFeedScreen(
    navigateToExpressionDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: ExpressionsFeedViewModel = koinViewModel()
    val expressions by viewModel.expressions.collectAsStateWithLifecycle()
    val authorsMap by viewModel.authorsMap.collectAsStateWithLifecycle()

    AnimatedContent(expressions.isNotEmpty()) { expressionsAvailable ->
        if (expressionsAvailable) {
            ExpressionsFeed(
                expressions = expressions,
                authorsMap = authorsMap,
                onExpressionClick = navigateToExpressionDetails,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ExpressionsFeed(
    expressions: List<Expression>,
    modifier: Modifier = Modifier,
    authorsMap: Map<Long, List<PaticipantAgent>> = emptyMap(),
    onExpressionClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(expressions, key = { it.id }) { expression ->
            ExpressionCard(
                expression = expression,
                authors = authorsMap[expression.id] ?: emptyList(),
                onClick = onExpressionClick,
                onAgentClick = onAgentClick,
            )
        }
    }
}

@Composable
private fun ExpressionCard(
    expression: Expression,
    authors: List<PaticipantAgent>,
    onClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    val translators = expression.agents.filter {
        it.role.equals("translator", ignoreCase = true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(expression.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                expression.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            if (authors.isNotEmpty()) {
                authors.forEach {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable { onAgentClick(it.id) }
                    )
                }
            }
            expression.language?.let {
                Text(
                    expression.language.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (translators.isNotEmpty()) {
                translators.forEach {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onAgentClick(it.id) }
                    )
                }
            }
            expression.publicationYear?.let {
                Text(
                    it.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

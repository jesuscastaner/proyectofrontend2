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
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.presentation.viewmodel.WorksFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorksFeedScreen(
    navigateToWorkDetails: (id: Long) -> Unit,
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: WorksFeedViewModel = koinViewModel()
    val works by viewModel.works.collectAsStateWithLifecycle()

    AnimatedContent(works.isNotEmpty()) { worksAvailable ->
        if (worksAvailable) {
            WorksFeed(
                works = works,
                onWorkClick = navigateToWorkDetails,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun WorksFeed(
    works: List<Work>,
    modifier: Modifier = Modifier,
    onWorkClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(works, key = { it.id }) { work ->
            WorkCard(
                work = work,
                onClick = onWorkClick,
                onAgentClick = onAgentClick,
            )
        }
    }
}

@Composable
private fun WorkCard(
    work: Work,
    onClick: (id: Long) -> Unit,
    onAgentClick: (id: Long) -> Unit,
) {
    val authors = work.agents.filter {
        it.role.equals("author", ignoreCase = true)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(work.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/200/300?random=1",
                contentDescription = work.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                work.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            if (authors.isNotEmpty()) {
                authors.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable { onAgentClick(it.id) }
                    )
                }
            }
            work.publicationYear?.let {
                Text(
                    it.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

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
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.presentation.viewmodel.AgentsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.FormattedLifeSpan
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AgentsFeedScreen(
    navigateToAgentDetails: (id: Long) -> Unit,
) {
    val viewModel: AgentsFeedViewModel = koinViewModel()
    val agents by viewModel.agents.collectAsStateWithLifecycle()

    AnimatedContent(agents.isNotEmpty()) { agentsAvailable ->
        if (agentsAvailable) {
            AgentsFeed(
                agents = agents,
                onAgentClick = navigateToAgentDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun AgentsFeed(
    agents: List<Agent>,
    modifier: Modifier = Modifier,
    onAgentClick: (id: Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(agents, key = { it.id }) { agent ->
            AgentCard(
                agent = agent,
                onClick = onAgentClick,
            )
        }
    }
}

@Composable
private fun AgentCard(
    agent: Agent,
    onClick: (id: Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(agent.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = agent.profilePic,
                contentDescription = "${agent.name} profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                agent.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            FormattedLifeSpan(
                birthYear = agent.birthYear,
                deathYear = agent.deathYear,
            )
        }
    }
}

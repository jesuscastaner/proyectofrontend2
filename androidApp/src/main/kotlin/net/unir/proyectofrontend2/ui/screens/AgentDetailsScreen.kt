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
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.presentation.viewmodel.AgentDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.FormattedLifeSpan
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AgentDetailsScreen(
    id: Long,
) {
    val viewModel: AgentDetailsViewModel = koinViewModel()
    val agent by viewModel.agent.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(agent != null) { agentAvailable ->
        if (agentAvailable) {
            AgentDetails(
                agent = agent!!,
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
    modifier: Modifier = Modifier,
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
            FormattedLifeSpan(
                birthYear = agent.birthYear,
                deathYear = agent.deathYear,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

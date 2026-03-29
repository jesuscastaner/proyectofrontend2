package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.R
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.PostHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostDetailsScreen(
    id: Long,
    navigateToUserProfile: (id: Long) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: PostDetailsViewModel = koinViewModel()
    val post by viewModel.post.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(post != null) { postAvailable ->
        if (postAvailable) {
            PostDetails(
                post = post!!,
                onUserClick = navigateToUserProfile,
                onBackClick = navigateBack
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetails(
    post: Post,
    onUserClick: (id: Long) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post #${post.id}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Column(Modifier.padding(16.dp)) {
                PostHeader(
                    userId = post.id.toString(),
                    onClick = { onUserClick(post.userId) },
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    post.content,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    post.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

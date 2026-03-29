package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.R
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.PostFrame
import net.unir.proyectofrontend2.ui.components.PostHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostDetailsScreen(
    id: Long,
    navigateToUserProfile: (id: Long) -> Unit,
    navigateToPostDetails: (id: Long) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: PostDetailsViewModel = koinViewModel()
    val post by viewModel.post.collectAsStateWithLifecycle()
    val replies by viewModel.replies.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(post != null) { postAvailable ->
        if (postAvailable) {
            PostDetails(
                post = post!!,
                replies = replies,
                onUserClick = navigateToUserProfile,
                onReplyClick = navigateToPostDetails,
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
    replies: List<Post>,
    onUserClick: (id: Long) -> Unit,
    onReplyClick: (id: Long) -> Unit,
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            item {
                PostHeader(
                    userId = post.userId.toString(),
                    onClick = { onUserClick(post.userId) },
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    post.content,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    post.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.Gray
                )
            }
            items(replies, key = { it.id }) { reply ->
                Spacer(modifier = Modifier.height(12.dp))
                PostFrame(
                    post = reply,
                    onClick = { onReplyClick(reply.id) },
                    onUserClick = { onUserClick(reply.userId) }
                )
            }
        }
    }
}


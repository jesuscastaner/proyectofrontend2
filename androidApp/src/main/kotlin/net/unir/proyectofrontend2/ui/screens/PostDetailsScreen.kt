package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val repost by viewModel.repost.collectAsStateWithLifecycle()
    val replies by viewModel.replies.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(post != null) { postAvailable ->
        if (postAvailable) {
            PostDetails(
                post = post!!,
                repost = repost,
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
    repost: Post?,
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
                    userDisplayName = post.userDisplayName,
                    userUsername = post.userUsername,
                    userProfilePic = post.userProfilePic,
                    onClick = { onUserClick(post.userId) },
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    post.content,
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (repost != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                    ) {
                        PostFrame(
                            post = repost,
                            onClick = { onReplyClick(repost.id) },
                            onUserClick = { onUserClick(repost.userId) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    post.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
                if (replies.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Chat,
                            contentDescription = "Replies",
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = replies.size.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 32.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            items(replies, key = { it.id }) { reply ->
                PostFrame(
                    post = reply,
                    onClick = { onReplyClick(reply.id) },
                    onUserClick = { onUserClick(reply.userId) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}


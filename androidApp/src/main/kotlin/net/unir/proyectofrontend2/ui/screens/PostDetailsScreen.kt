package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.PostFrame
import net.unir.proyectofrontend2.ui.components.PostHeader
import net.unir.proyectofrontend2.ui.components.icons.PostRepliesIcon
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostDetailsScreen(
    id: Long,
    navigateToUserProfile: (id: Long) -> Unit,
    navigateToPostDetails: (id: Long) -> Unit,
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
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                post.createdAt,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(24.dp))
            PostRepliesIcon(
                repliesCount = replies.size,
                modifier = Modifier.size(24.dp),
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
        if (replies.isNotEmpty()) {
            item {
                Heading("Replies")
                Spacer(modifier = Modifier.height(24.dp))
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
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

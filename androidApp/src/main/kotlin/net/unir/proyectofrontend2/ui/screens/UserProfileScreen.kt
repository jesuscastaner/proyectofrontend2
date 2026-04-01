package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.presentation.viewmodel.UserProfileViewModel
import net.unir.proyectofrontend2.ui.components.CircleImage
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.Heading
import net.unir.proyectofrontend2.ui.components.PostFrame
import net.unir.proyectofrontend2.ui.components.VerifiedUserIcon
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    id: Long,
    navigateToAgentDetails: (id: Long) -> Unit,
    navigateToPostDetails: (id: Long) -> Unit,
) {
    val viewModel: UserProfileViewModel = koinViewModel()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val repostsMap by viewModel.repostsMap.collectAsStateWithLifecycle()
    val repliesCountMap by viewModel.repliesCountMap.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(user != null) { userAvailable ->
        if (userAvailable) {
            UserProfile(
                user = user!!,
                posts = posts,
                repostsMap = repostsMap,
                repliesCountMap = repliesCountMap,
                onUserAgentClick = navigateToAgentDetails,
                onPostClick = navigateToPostDetails,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserProfile(
    user: User,
    posts: List<Post>,
    repostsMap: Map<Long, Post?>,
    repliesCountMap: Map<Long, Int>,
    modifier: Modifier = Modifier,
    onUserAgentClick: (id: Long) -> Unit,
    onPostClick: (id: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            UserProfileHeader(
                user = user,
                onUserAgentClick = onUserAgentClick,
            )
        }
        if (posts.isNotEmpty()) {
            item {
                Heading("Posts")
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(posts, key = { it.id }) { post ->
                PostFrame(
                    post = post,
                    repost = repostsMap[post.id],
                    repliesCount = repliesCountMap[post.id] ?: 0,
                    onClick = { onPostClick(post.id) },
                    onUserClick = {},
                    onReplyToClick = onPostClick,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        } else {
            item {
                EmptyScreenContent(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun UserProfileHeader(
    user: User,
    modifier: Modifier = Modifier,
    onUserAgentClick: (id: Long) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleImage(
            imageUrl = user.profilePic,
            modifier = Modifier.size(90.dp),
            contentDescription = "Profile picture",
            onClick = {}
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                user.displayName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            if (user.isVerified) {
                Spacer(modifier = Modifier.width(6.dp))
                VerifiedUserIcon(modifier = Modifier.size(24.dp))
            }
        }
        Text(
            "@${user.username}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            user.website,
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            user.bio,
            style = MaterialTheme.typography.bodyMedium,
        )
        user.agentId?.let {
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { onUserAgentClick(it) }
            ) {
                Text("Agent profile")
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 24.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

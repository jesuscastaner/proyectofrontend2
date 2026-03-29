package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostsFeedViewModel
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.PostFrame
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostsFeedScreen(
    navigateToPostDetails: (id: Long) -> Unit,
    navigateToUserProfile: (id: Long) -> Unit,
) {
    val viewModel: PostsFeedViewModel = koinViewModel()
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    AnimatedContent(posts.isNotEmpty()) { postsAvailable ->
        if (postsAvailable) {
            PostsFeed(
                posts = posts,
                onPostClick = navigateToPostDetails,
                onUserClick = navigateToUserProfile,
            )
        } else {
            EmptyScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun PostsFeed(
    posts: List<Post>,
    onPostClick: (id: Long) -> Unit,
    onUserClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(posts, key = { it.id }) { post ->
            PostFrame(
                post = post,
                onClick = onPostClick,
                onUserClick = onUserClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

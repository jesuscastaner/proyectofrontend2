package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostListScreen(navigateToDetails: (id: Long) -> Unit) {
    val viewModel: PostListViewModel = koinViewModel()
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    AnimatedContent(posts.isNotEmpty()) { postsAvailable ->
        if (postsAvailable) {
            PostsGrid(
                posts = posts,
                onPostClick = navigateToDetails,
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun PostsGrid(
    posts: List<Post>,
    onPostClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(posts, key = { it.id }) { post ->
            PostFrame(
                post = post,
                onClick = { onPostClick(post.id) },
            )
        }
    }
}

@Composable
private fun PostFrame(
    post: Post,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = post.image,
            contentDescription = post.content,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(
            post.userId.toString(),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            post.content,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            post.createdAt,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            post.updatedAt,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

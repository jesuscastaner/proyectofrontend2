package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.presentation.viewmodel.PostListViewModel
import net.unir.proyectofrontend2.ui.components.PostsFeed
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostListScreen(
    navigateToPostDetails: (id: Long) -> Unit,
) {
    val viewModel: PostListViewModel = koinViewModel()
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    AnimatedContent(posts) { posts ->
        PostsFeed(
            posts = posts,
            onPostClick = navigateToPostDetails,
        )
    }
}

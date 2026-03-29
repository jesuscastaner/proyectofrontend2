package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.unir.proyectofrontend2.R
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.presentation.viewmodel.UserProfileViewModel
import net.unir.proyectofrontend2.ui.components.CircleImage
import net.unir.proyectofrontend2.ui.components.EmptyScreenContent
import net.unir.proyectofrontend2.ui.components.PostFrame
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    id: Long,
    navigateToPostDetails: (id: Long) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: UserProfileViewModel = koinViewModel()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(user != null) { userAvailable ->
        if (userAvailable) {
            UserProfile(
                user = user!!,
                posts = posts,
                onPostClick = navigateToPostDetails,
                onBackClick = navigateBack,
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
    onPostClick: (id: Long) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("@${user.username}") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        ) {
            item {
                UserProfileHeader(user = user)
                Spacer(Modifier.height(16.dp))
            }
            if (posts.isNotEmpty()) {
                items(posts, key = { it.id }) { post ->
                    PostFrame(
                        post = post,
                        onClick = { onPostClick(post.id) },
                        onUserClick = {}
                    )
                }
            } else {
                item {
                    EmptyScreenContent(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun UserProfileHeader(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleImage(
            modifier = modifier.size(90.dp),
            imageUrl = user.profilePic,
            onClick = {}
        )
        Spacer(modifier = modifier.height(12.dp))
        Text(
            user.displayName,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "@${user.username}",
                style = MaterialTheme.typography.bodySmall,
            )
            if (user.verified) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
        Text(
            user.website,
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            user.bio,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


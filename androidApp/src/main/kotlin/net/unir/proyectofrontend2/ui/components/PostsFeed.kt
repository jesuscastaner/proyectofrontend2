package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.unir.proyectofrontend2.data.model.Post

@Composable
fun PostsFeed(
    posts: List<Post>,
    onPostClick: (id: Long) -> Unit,
    onUserClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (posts.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        ) {
            items(posts, key = { it.id }) { post ->
                PostFrame(
                    post = post,
                    onClick = { onPostClick(post.id) },
                    onUserClick = { onUserClick(post.userId) }
                )
            }
        }
    } else {
        EmptyScreenContent(modifier = modifier.fillMaxWidth())
    }
}

@Composable
private fun PostFrame(
    post: Post,
    onClick: () -> Unit,
    onUserClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        PostHeader(
            userId = post.id.toString(),
            onClick = { onUserClick() },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            post.content,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.Gray
        )
    }
}

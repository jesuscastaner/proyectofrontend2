package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.unir.proyectofrontend2.data.model.Post

@Composable
fun PostsFeed(
    posts: List<Post>,
    onPostClick: (id: Long) -> Unit,
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Person,
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                "User #${post.userId}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            post.content,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            post.createdAt,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

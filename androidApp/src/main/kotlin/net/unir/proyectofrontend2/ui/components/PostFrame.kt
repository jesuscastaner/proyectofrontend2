package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.unir.proyectofrontend2.data.model.Post

@Composable
fun PostFrame(
    post: Post,
    onClick: (id: Long) -> Unit,
    onUserClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick(post.id) }
    ) {
        PostHeader(
            userId = post.id.toString(),
            onClick = { onUserClick(post.userId) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            post.content,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.Gray
        )
    }
}

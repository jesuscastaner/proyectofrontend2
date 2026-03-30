package net.unir.proyectofrontend2.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.unir.proyectofrontend2.data.model.Post

@Composable
fun PostFrame(
    post: Post,
    modifier: Modifier = Modifier,
    repost: Post? = null,
    replyCount: Int = 0,
    onClick: (id: Long) -> Unit,
    onUserClick: (id: Long) -> Unit,
    onReplyToClick: ((id: Long) -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(post.id) }
    ) {
        val replyToId = post.replyToId
        if (replyToId != null && onReplyToClick != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onReplyToClick(replyToId) }
                    .padding(bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Reply,
                    modifier = Modifier.size(16.dp),
                    contentDescription = "Reply to another post",
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Reply to post #${replyToId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        PostHeader(
            userDisplayName = post.userDisplayName,
            userUsername = post.userUsername,
            userProfilePic = post.userProfilePic,
            onClick = { onUserClick(post.userId) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            post.content,
            style = MaterialTheme.typography.bodyMedium
        )
        if (repost != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                PostFrame(
                    post = repost,
                    onClick = { onClick(repost.id) },
                    onUserClick = { onUserClick(repost.userId) },
                )
            }
        }
        if (replyCount > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Chat,
                    modifier = Modifier.size(16.dp),
                    contentDescription = "Replies",
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = replyCount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

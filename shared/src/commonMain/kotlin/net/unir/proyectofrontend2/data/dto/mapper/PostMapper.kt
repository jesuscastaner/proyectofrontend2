package net.unir.proyectofrontend2.data.dto.mapper

import net.unir.proyectofrontend2.data.dto.response.PostResponse
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.model.User

fun PostResponse.toPost(user: User): Post =
    Post(
        id = id,
        userId = userId,
        userDisplayName = user.displayName,
        userUsername = user.username,
        userProfilePic = user.profilePic,
        content = content,
        replyToId = replyToId,
        repostOfId = repostOfId,
        isVisible = isVisible,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

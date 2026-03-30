package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val userId: Long,
    val userDisplayName: String,
    val userUsername: String,
    val userProfilePic: String? = null,
    val content: String,
    val replyToId: Long? = null,
    val repostOfId: Long? = null,
    val isVisible: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

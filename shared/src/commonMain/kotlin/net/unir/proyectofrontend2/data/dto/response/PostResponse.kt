package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: Long,
    val userId: Long,
    val content: String,
    val replyToId: Long? = null,
    val repostOfId: Long? = null,
    val isVisible: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

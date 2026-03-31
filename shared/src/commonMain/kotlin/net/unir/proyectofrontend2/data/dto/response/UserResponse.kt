package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val displayName: String,
    val username: String,
    val profilePic: String,
    val bio: String,
    val website: String,
    val isVerified: Boolean,
    val isSuspended: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

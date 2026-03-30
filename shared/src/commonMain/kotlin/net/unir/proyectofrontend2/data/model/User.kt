package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
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

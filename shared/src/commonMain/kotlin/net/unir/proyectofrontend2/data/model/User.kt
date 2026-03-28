package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val username: String,
    val displayName: String,
    val profilePic: String,
    val bio: String,
    val website: String,
    val verified: Boolean,
    val suspended: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShelfManifestationUser(
    val id: Long,
    val shelfId: Long,
    val manifestationUserId: Long,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

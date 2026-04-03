package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Shelf(
    val id: Long,
    val userId: Long,
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Manifestation(
    val id: Long,
    val expressionId: Long,
    val title: String,
    val publicationYear: Int?,
    val isbn: String?,
    val agents: List<Agent> = emptyList()
)

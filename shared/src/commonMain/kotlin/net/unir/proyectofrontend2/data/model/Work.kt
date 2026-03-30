package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Work(
    val id: Long,
    val title: String,
    val publicationYear: Int?,
    val agents: List<Agent> = emptyList()
)

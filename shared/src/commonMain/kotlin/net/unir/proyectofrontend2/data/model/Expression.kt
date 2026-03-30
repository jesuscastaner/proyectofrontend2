package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Expression(
    val id: Long,
    val workId: Long,
    val title: String,
    val publicationYear: Int?,
    val language: String?,
    val agents: List<Agent> = emptyList()
)

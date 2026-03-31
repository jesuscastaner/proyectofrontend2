package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WorkResponse(
    val id: Long,
    val title: String,
    val publicationYear: Short?,
    val agents: List<PaticipantAgentResponse> = emptyList()
)

package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ExpressionResponse(
    val id: Long,
    val workId: Long,
    val title: String,
    val publicationYear: Short?,
    val language: String?,
    val agents: List<PaticipantAgentResponse> = emptyList()
)

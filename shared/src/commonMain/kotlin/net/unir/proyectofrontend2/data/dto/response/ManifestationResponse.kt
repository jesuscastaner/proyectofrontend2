package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ManifestationResponse(
    val id: Long,
    val expressionId: Long,
    val title: String,
    val publicationYear: Short?,
    val isbn: String?,
    val coverImage: String?,
    val agents: List<PaticipantAgentResponse> = emptyList(),
)

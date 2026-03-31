package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PaticipantAgentResponse(
    val id: Long,
    val role: String?,
)

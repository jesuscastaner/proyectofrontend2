package net.unir.proyectofrontend2.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AgentResponse(
    val id: Long,
    val name: String,
    val birthYear: Short?,
    val deathYear: Short?,
    val profilePic: String?,
)

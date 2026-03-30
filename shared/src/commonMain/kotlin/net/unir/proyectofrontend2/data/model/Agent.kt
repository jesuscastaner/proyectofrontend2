package net.unir.proyectofrontend2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Agent(
    val name: String,
    val role: String? = null
)

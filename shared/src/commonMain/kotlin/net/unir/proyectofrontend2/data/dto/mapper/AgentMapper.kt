package net.unir.proyectofrontend2.data.dto.mapper

import net.unir.proyectofrontend2.data.dto.response.AgentResponse
import net.unir.proyectofrontend2.data.model.Agent

fun AgentResponse.toAgent(): Agent =
    Agent(
        id = id,
        name = name,
        birthYear = birthYear,
        deathYear = deathYear,
        profilePic = profilePic,
    )

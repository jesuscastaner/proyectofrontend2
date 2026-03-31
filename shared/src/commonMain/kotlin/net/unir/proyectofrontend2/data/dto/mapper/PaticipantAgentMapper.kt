package net.unir.proyectofrontend2.data.dto.mapper

import net.unir.proyectofrontend2.data.dto.response.PaticipantAgentResponse
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.model.PaticipantAgent

fun PaticipantAgentResponse.toPaticipantAgent(agent: Agent): PaticipantAgent =
    PaticipantAgent(
        id = id,
        name = agent.name,
        role = role,
    )

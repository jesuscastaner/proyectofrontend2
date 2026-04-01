package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Agent

interface AgentApi {
    suspend fun getAgents(): List<Agent>
}

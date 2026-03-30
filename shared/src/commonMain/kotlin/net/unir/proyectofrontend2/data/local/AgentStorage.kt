package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Agent

interface AgentStorage {
    suspend fun saveAgents(newAgents: List<Agent>)
    fun getAgents(): Flow<List<Agent>>
//    fun getAgentById(id: Long): Flow<Agent?>
}

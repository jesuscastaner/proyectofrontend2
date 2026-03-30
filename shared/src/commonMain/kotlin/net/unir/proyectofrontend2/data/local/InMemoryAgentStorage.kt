package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Agent

class InMemoryAgentStorage : AgentStorage {
    private val storedAgents = MutableStateFlow(emptyList<Agent>())

    override suspend fun saveAgents(newAgents: List<Agent>) {
        storedAgents.value = newAgents
    }

    override fun getAgents(): Flow<List<Agent>> = storedAgents

//    override fun getAgentById(id: Long): Flow<Agent?> = storedAgents.map {
//        it.find { agent ->
//            agent.id == id
//        }
//    }
}

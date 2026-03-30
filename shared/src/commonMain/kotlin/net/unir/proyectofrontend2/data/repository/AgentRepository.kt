package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.AgentStorage
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.remote.LibraryApi

class AgentRepository(
    private val libraryApi: LibraryApi,
    private val agentStorage: AgentStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        agentStorage.saveAgents(libraryApi.getAgents())
    }

    fun getAgents(): Flow<List<Agent>> = agentStorage.getAgents()

    fun getAgentById(id: Long): Flow<Agent?> = agentStorage.getAgentById(id)
}

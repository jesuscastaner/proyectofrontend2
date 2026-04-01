package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Manifestation

class InMemoryManifestationStorage : ManifestationStorage {
    private val storedManifestations = MutableStateFlow(emptyList<Manifestation>())

    override suspend fun saveManifestations(newManifestations: List<Manifestation>) {
        storedManifestations.value = newManifestations
    }

    override fun getManifestations(): Flow<List<Manifestation>> = storedManifestations

    override fun getManifestationById(id: Long): Flow<Manifestation?> = storedManifestations.map {
        it.find { manifestation ->
            manifestation.id == id
        }
    }

    override fun getManifestationsByAgentId(id: Long): Flow<List<Manifestation>> =
        storedManifestations.map {
            it.filter { manifestation ->
                manifestation.agents.any { agent ->
                    agent.id == id
                }
            }
        }
}

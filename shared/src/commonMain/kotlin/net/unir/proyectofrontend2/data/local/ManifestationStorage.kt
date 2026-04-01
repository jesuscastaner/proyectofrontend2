package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Manifestation

interface ManifestationStorage {
    suspend fun saveManifestations(newManifestations: List<Manifestation>)
    fun getManifestations(): Flow<List<Manifestation>>
    fun getManifestationById(id: Long): Flow<Manifestation?>
    fun getManifestationsByAgentId(id: Long): Flow<List<Manifestation>>
}

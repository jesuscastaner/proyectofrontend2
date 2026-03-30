package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.ManifestationStorage
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.remote.LibraryApi

class ManifestationRepository(
    private val libraryApi: LibraryApi,
    private val manifestationStorage: ManifestationStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        manifestationStorage.saveManifestations(libraryApi.getManifestations())
    }

    fun getManifestations(): Flow<List<Manifestation>> = manifestationStorage.getManifestations()

    fun getManifestationById(id: Long): Flow<Manifestation?> =
        manifestationStorage.getManifestationById(id)
}

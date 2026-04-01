package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.ManifestationStorage
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.remote.ManifestationApi

class ManifestationRepository(
    private val manifestationApi: ManifestationApi,
    private val manifestationStorage: ManifestationStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        manifestationStorage.saveManifestations(manifestationApi.getManifestations())
    }

    fun getManifestations(): Flow<List<Manifestation>> = manifestationStorage.getManifestations()

    fun getManifestationById(id: Long): Flow<Manifestation?> =
        manifestationStorage.getManifestationById(id)

    fun getManifestationsByExpressionId(expressionId: Long): Flow<List<Manifestation>> =
        manifestationStorage.getManifestations().map {
            it.filter { manifestation ->
                manifestation.expressionId == expressionId
            }
        }
}

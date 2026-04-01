package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Manifestation

interface ManifestationApi {
    suspend fun getManifestations(): List<Manifestation>
}

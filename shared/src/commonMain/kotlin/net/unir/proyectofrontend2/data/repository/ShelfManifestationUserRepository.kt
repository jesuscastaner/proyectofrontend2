package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.local.ShelfManifestationUserStorage
import net.unir.proyectofrontend2.data.model.ShelfManifestationUser
import net.unir.proyectofrontend2.data.remote.ShelfManifestationUserApi

class ShelfManifestationUserRepository(
    private val api: ShelfManifestationUserApi,
    private val storage: ShelfManifestationUserStorage
) {
    fun getItemsByShelfId(shelfId: Long): Flow<List<ShelfManifestationUser>> =
        storage.getItemsByShelfId(shelfId)

    suspend fun fetchItemsByShelfId(shelfId: Long) {
        val remoteItems = api.getItemsByShelfId(shelfId)
        storage.saveItems(remoteItems)
    }
}

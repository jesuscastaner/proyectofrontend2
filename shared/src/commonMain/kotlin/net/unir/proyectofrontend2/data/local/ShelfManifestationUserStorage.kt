package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.ShelfManifestationUser

interface ShelfManifestationUserStorage {
    suspend fun saveItems(newItems: List<ShelfManifestationUser>)
    fun getItemsByShelfId(shelfId: Long): Flow<List<ShelfManifestationUser>>
}

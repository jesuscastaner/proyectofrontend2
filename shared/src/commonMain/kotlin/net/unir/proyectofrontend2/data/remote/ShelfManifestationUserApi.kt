package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.ShelfManifestationUser
interface ShelfManifestationUserApi {
    suspend fun getItemsByShelfId(shelfId: Long): List<ShelfManifestationUser>
    suspend fun addItemToShelf(item: ShelfManifestationUser): ShelfManifestationUser?
}

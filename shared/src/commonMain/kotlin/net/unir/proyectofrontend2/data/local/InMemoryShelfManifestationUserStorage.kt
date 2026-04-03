package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.ShelfManifestationUser

class InMemoryShelfManifestationUserStorage : ShelfManifestationUserStorage {
    private val storedItems = MutableStateFlow(emptyList<ShelfManifestationUser>())

    override suspend fun saveItems(newItems: List<ShelfManifestationUser>) {
        storedItems.value = newItems
    }

    override fun getItemsByShelfId(shelfId: Long): Flow<List<ShelfManifestationUser>> = storedItems.map { items ->
        items.filter { it.shelfId == shelfId }
    }
}

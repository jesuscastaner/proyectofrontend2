package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Shelf

class InMemoryShelfStorage : ShelfStorage {
    private val storedShelves = MutableStateFlow(emptyList<Shelf>())

    override suspend fun saveShelves(newShelves: List<Shelf>) {
        storedShelves.value = newShelves
    }

    override fun getShelves(): Flow<List<Shelf>> = storedShelves

    override fun getShelfById(id: Long): Flow<Shelf?> = storedShelves.map { shelves ->
        shelves.find { it.id == id }
    }
}

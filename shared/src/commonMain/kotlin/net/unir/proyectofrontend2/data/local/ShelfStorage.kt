package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Shelf

interface ShelfStorage {
    suspend fun saveShelves(newShelves: List<Shelf>)
    fun getShelves(): Flow<List<Shelf>>
    fun getShelfById(id: Long): Flow<Shelf?>
}

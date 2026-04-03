package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.local.ShelfStorage
import net.unir.proyectofrontend2.data.model.Shelf
import net.unir.proyectofrontend2.data.remote.ShelfApi

class ShelfRepository(
    private val api: ShelfApi,
    private val storage: ShelfStorage
) {

    fun getShelves(): Flow<List<Shelf>> = storage.getShelves()

    suspend fun fetchShelvesByUserId(userId: Long) {
        val remoteShelves = api.getShelvesByUserId(userId)
        storage.saveShelves(remoteShelves)
    }

    suspend fun createShelf(userId: Long, name: String) {
        val newShelf = Shelf(id = 0, userId = userId, name = name)
        val created = api.createShelf(newShelf)

        if (created != null) {
            fetchShelvesByUserId(userId)
        }
    }
}

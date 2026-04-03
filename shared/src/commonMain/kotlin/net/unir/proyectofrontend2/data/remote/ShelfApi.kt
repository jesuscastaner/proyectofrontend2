package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Shelf

interface ShelfApi {
    suspend fun getShelvesByUserId(userId: Long): List<Shelf>
    suspend fun createShelf(shelf: Shelf): Shelf?
}

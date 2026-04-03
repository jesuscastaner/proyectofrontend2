package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.Shelf


class KtorShelfApi(client: HttpClient) : AbstractKtorApi(client), ShelfApi {

    override suspend fun getShelvesByUserId(userId: Long): List<Shelf> =
        get(path = "/api/shelves/user/$userId", default = emptyList())

    override suspend fun createShelf(shelf: Shelf): Shelf? =
        post(path = "/api/shelves", body = shelf, default = null)
}

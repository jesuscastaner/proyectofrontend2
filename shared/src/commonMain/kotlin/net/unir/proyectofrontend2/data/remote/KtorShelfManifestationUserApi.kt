package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.ShelfManifestationUser


class KtorShelfManifestationUserApi(client: HttpClient) : AbstractKtorApi(client), ShelfManifestationUserApi {

    override suspend fun getItemsByShelfId(shelfId: Long): List<ShelfManifestationUser> =
        get(path = "/api/shelf-manifestation-users/shelf/$shelfId", default = emptyList())

    override suspend fun addItemToShelf(item: ShelfManifestationUser): ShelfManifestationUser? =
        post(path = "/api/shelf-manifestation-users", body = item, default = null)
}

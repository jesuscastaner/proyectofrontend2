package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.User

class KtorUserApi(
    client: HttpClient
) : AbstractKtorApi(client), UserApi {
    companion object {
        private const val BASE_ENDPOINT =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi"
    }

    override suspend fun getUsers(): List<User> =
        get("$BASE_ENDPOINT/users.json", default = emptyList())
}

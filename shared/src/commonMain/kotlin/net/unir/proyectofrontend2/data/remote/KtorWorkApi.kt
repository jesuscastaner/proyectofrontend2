package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.Work

class KtorWorkApi(
    client: HttpClient
) : AbstractKtorApi(client), WorkApi {
    companion object {
        private const val BASE_ENDPOINT =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi"
    }

    override suspend fun getWorks(): List<Work> =
        get("${BASE_ENDPOINT}/works.json", default = emptyList())
}

package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.Manifestation

class KtorManifestationApi(
    client: HttpClient
) : AbstractKtorApi(client), ManifestationApi {
    companion object {
        private const val BASE_ENDPOINT =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi"
    }

    override suspend fun getManifestations(): List<Manifestation> =
        get(path = "${BASE_ENDPOINT}/manifestations.json", default = emptyList())

//    override suspend fun createManifestation(): Manifestation =
//        post(
//            path = "",
//            body = Manifestation,
//            default = null
//        )
}

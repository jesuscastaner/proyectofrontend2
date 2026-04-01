package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.Agent

class KtorAgentApi(
    client: HttpClient
) : AbstractKtorApi(client), AgentApi {
    companion object {
        private const val BASE_ENDPOINT =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi"
    }

    override suspend fun getAgents(): List<Agent> =
        get("${BASE_ENDPOINT}/agents.json", default = emptyList())
}

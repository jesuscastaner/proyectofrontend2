package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.unir.proyectofrontend2.data.dto.response.ManifestationResponse
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work
import kotlin.coroutines.cancellation.CancellationException

class KtorLibraryApi(
    private val client: HttpClient
) : LibraryApi {

    companion object {
        private const val BASE_URL =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi/library"
    }

    override suspend fun getWorks(): List<Work> = try {
        client.get("$BASE_URL/works.json").body()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        emptyList()
    }

    override suspend fun getExpressions(): List<Expression> = try {
        client.get("$BASE_URL/expressions.json").body()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        emptyList()
    }

        // ManifestationResponse
    override suspend fun getManifestations(): List<Manifestation> = try {
        client.get("$BASE_URL/manifestations.json").body()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        emptyList()
    }

    override suspend fun getAgents(): List<Agent> = try {
        client.get("$BASE_URL/agents.json").body()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        emptyList()
    }
}

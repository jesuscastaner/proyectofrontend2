package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.unir.proyectofrontend2.data.model.User
import kotlin.coroutines.cancellation.CancellationException

class KtorUserApi(private val client: HttpClient) : UserApi {
    companion object {
        private const val BASE_URL =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi/users.json"
    }

    override suspend fun getUsers(): List<User> = try {
        client.get(BASE_URL).body()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        emptyList()
    }
}

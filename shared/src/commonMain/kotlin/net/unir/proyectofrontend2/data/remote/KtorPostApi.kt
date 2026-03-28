package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.unir.proyectofrontend2.data.model.Post
import kotlin.coroutines.cancellation.CancellationException

class KtorPostApi(private val client: HttpClient) : PostApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi/posts.json"
    }

    override suspend fun getPosts(): List<Post> {
        return try {
            client.get(API_URL).body()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            emptyList()
        }
    }
}

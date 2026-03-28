package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.unir.proyectofrontend2.data.model.Post

class KtorPostApi(private val client: HttpClient) : PostApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/posts.json"
    }

    override suspend fun getPosts(): ApiResult<List<Post>> {
        return try {
            ApiResult.Success(client.get(API_URL).body())
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    override suspend fun getPost(id: Long): ApiResult<Post> {
        return try {
            ApiResult.Success(client.get("$API_URL/$id").body())
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}

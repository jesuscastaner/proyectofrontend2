package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.unir.proyectofrontend2.data.model.Post
import kotlin.coroutines.cancellation.CancellationException

class KtorPostApi(private val client: HttpClient) : PostApi {

    override suspend fun getPosts(): ApiResult<List<Post>> {
        return try {
            ApiResult.Success(client.get("api/posts/").body())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ApiResult.Error(e)
        }
    }

    override suspend fun getPost(id: Long): ApiResult<Post> {
        return try {
            ApiResult.Success(client.get("api/posts/$id").body())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ApiResult.Error(e)
        }
    }
}

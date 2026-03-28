package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.PostStorage
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.remote.ApiResult
import net.unir.proyectofrontend2.data.remote.PostApi

class PostRepository(
    private val postApi: PostApi,
    private val postStorage: PostStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        when (val result = postApi.getPosts()) {
            is ApiResult.Success -> {
                postStorage.savePosts(result.data)
            }

            is ApiResult.Error -> {
                result.exception.printStackTrace()
            }
        }
    }

    fun getPosts(): Flow<List<Post>> = postStorage.getPosts()

    fun getPostById(id: Long): Flow<Post?> = postStorage.getPostById(id)
}

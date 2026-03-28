package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Post

interface PostApi {
    suspend fun getPosts(): ApiResult<List<Post>>
    suspend fun getPost(id: Long): ApiResult<Post>
}

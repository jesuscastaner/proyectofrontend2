package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Post

interface PostStorage {
    suspend fun savePosts(newPosts: List<Post>)
    fun getPosts(): Flow<List<Post>>
    fun getPostById(id: Long): Flow<Post?>
}

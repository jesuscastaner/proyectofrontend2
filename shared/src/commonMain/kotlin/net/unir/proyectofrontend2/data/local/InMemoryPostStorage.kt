package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Post

class InMemoryPostStorage : PostStorage {
    private val storedPosts = MutableStateFlow(emptyList<Post>())

    override suspend fun savePosts(newPosts: List<Post>) {
        storedPosts.value = newPosts
    }

    override fun getPosts(): Flow<List<Post>> = storedPosts

    override fun getPostById(id: Long): Flow<Post?> = storedPosts.map { posts ->
        posts.find { it.id == id }
    }

    override fun getPostsByUserId(userId: Long): Flow<List<Post>> = storedPosts.map { posts ->
        posts.filter { it.userId == userId }
    }
}

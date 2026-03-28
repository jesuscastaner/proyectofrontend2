package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Post

interface PostApi {
    suspend fun getPosts(): List<Post>
}

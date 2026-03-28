package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.User

interface UserApi {
    suspend fun getUsers(): List<User>
}

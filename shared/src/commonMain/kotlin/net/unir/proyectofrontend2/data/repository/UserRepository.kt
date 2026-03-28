package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.UserStorage
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.data.remote.UserApi

class UserRepository(
    private val userApi: UserApi,
    private val userStorage: UserStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        userStorage.saveUsers(userApi.getUsers())
    }

    fun getPosts(): Flow<List<User>> = userStorage.getUsers()

    fun getPostById(id: Long): Flow<User?> = userStorage.getUserById(id)
}

package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.User

class InMemoryUserStorage : UserStorage {
    private val storedUsers = MutableStateFlow(emptyList<User>())

    override suspend fun saveUsers(newUsers: List<User>) {
        storedUsers.value = newUsers
    }

    override fun getUsers(): Flow<List<User>> = storedUsers

    override fun getUserById(id: Long): Flow<User?> = storedUsers.map { users ->
        users.find { it.id == id }
    }
}

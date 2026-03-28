package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.User

interface UserStorage {
    suspend fun saveUsers(newUsers: List<User>)
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Long): Flow<User?>
}

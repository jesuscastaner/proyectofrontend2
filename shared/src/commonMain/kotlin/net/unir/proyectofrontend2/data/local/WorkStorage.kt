package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Work

interface WorkStorage {
    suspend fun saveWorks(newWorks: List<Work>)
    fun getWorks(): Flow<List<Work>>
    fun getWorkById(id: Long): Flow<Work?>
    fun getWorksByAgentId(id: Long): Flow<List<Work>>
}

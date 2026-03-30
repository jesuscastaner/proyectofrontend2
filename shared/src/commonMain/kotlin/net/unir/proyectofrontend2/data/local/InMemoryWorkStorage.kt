package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Work

class InMemoryWorkStorage : WorkStorage {
    private val storedWorks = MutableStateFlow(emptyList<Work>())

    override suspend fun saveWorks(newWorks: List<Work>) {
        storedWorks.value = newWorks
    }

    override fun getWorks(): Flow<List<Work>> = storedWorks

    override fun getWorkById(id: Long): Flow<Work?> = storedWorks.map {
        it.find { work ->
            work.id == id
        }
    }
}

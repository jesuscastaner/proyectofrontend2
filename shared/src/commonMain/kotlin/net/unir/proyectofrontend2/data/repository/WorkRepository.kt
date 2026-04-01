package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.WorkStorage
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.remote.WorkApi

class WorkRepository(
    private val workApi: WorkApi,
    private val workStorage: WorkStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        workStorage.saveWorks(workApi.getWorks())
    }

    fun getWorks(): Flow<List<Work>> = workStorage.getWorks()

    fun getWorkById(id: Long): Flow<Work?> = workStorage.getWorkById(id)
}

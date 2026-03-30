package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.ExpressionStorage
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.remote.LibraryApi

class ExpressionRepository(
    private val libraryApi: LibraryApi,
    private val expressionStorage: ExpressionStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        expressionStorage.saveExpressions(libraryApi.getExpressions())
    }

    fun getExpressions(): Flow<List<Expression>> = expressionStorage.getExpressions()

    fun getExpressionById(id: Long): Flow<Expression?> = expressionStorage.getExpressionById(id)
}

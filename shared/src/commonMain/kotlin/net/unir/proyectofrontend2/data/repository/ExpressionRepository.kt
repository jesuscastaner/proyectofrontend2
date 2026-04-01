package net.unir.proyectofrontend2.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.unir.proyectofrontend2.data.local.ExpressionStorage
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.remote.ExpressionApi

class ExpressionRepository(
    private val expressionApi: ExpressionApi,
    private val expressionStorage: ExpressionStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        expressionStorage.saveExpressions(expressionApi.getExpressions())
    }

    fun getExpressions(): Flow<List<Expression>> = expressionStorage.getExpressions()

    fun getExpressionById(id: Long): Flow<Expression?> = expressionStorage.getExpressionById(id)

    fun getExpressionsByWorkId(workId: Long): Flow<List<Expression>> =
        expressionStorage.getExpressions().map {
            it.filter { expression ->
                expression.workId == workId
            }
        }
}

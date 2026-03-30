package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Expression

class InMemoryExpressionStorage : ExpressionStorage {
    private val storedExpressions = MutableStateFlow(emptyList<Expression>())

    override suspend fun saveExpressions(newExpressions: List<Expression>) {
        storedExpressions.value = newExpressions
    }

    override fun getExpressions(): Flow<List<Expression>> = storedExpressions

    override fun getExpressionById(id: Long): Flow<Expression?> = storedExpressions.map {
        it.find { expression ->
            expression.id == id
        }
    }
}

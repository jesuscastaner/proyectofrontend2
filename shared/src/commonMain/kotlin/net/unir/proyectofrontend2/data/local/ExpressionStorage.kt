package net.unir.proyectofrontend2.data.local

import kotlinx.coroutines.flow.Flow
import net.unir.proyectofrontend2.data.model.Expression

interface ExpressionStorage {
    suspend fun saveExpressions(newExpressions: List<Expression>)
    fun getExpressions(): Flow<List<Expression>>
    fun getExpressionById(id: Long): Flow<Expression?>
}

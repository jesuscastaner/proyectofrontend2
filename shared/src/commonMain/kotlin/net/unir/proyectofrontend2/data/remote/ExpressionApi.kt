package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Expression

interface ExpressionApi {
    suspend fun getExpressions(): List<Expression>
}

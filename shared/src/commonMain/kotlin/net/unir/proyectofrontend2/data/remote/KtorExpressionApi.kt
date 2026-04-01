package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import net.unir.proyectofrontend2.data.model.Expression

class KtorExpressionApi(
    client: HttpClient
) : AbstractKtorApi(client), ExpressionApi {
    companion object {
        private const val BASE_ENDPOINT =
            "https://raw.githubusercontent.com/jesuscastaner/proyectofrontend2/main/testapi"
    }

    override suspend fun getExpressions(): List<Expression> =
        get("${BASE_ENDPOINT}/expressions.json", default = emptyList())
}

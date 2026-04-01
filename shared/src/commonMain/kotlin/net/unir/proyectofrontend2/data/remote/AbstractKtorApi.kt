package net.unir.proyectofrontend2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlin.coroutines.cancellation.CancellationException

abstract class AbstractKtorApi(
    protected val client: HttpClient
) {
    protected suspend inline fun <reified T> get(
        path: String,
        default: T
    ): T =
        safeCall(default) {
            client.get(path).body()
        } ?: default

    protected suspend inline fun <reified T> post(
        path: String,
        body: Any,
        default: T
    ): T =
        safeCall(default) {
            client.post(path) { setBody(body) }.body()
        } ?: default

    protected suspend inline fun <reified T> put(
        path: String,
        body: Any,
        default: T
    ): T =
        safeCall(default) {
            client.put(path) { setBody(body) }.body()
        } ?: default

    protected suspend inline fun <reified T> delete(
        path: String,
        default: T
    ): T =
        safeCall(default) {
            client.delete(path).body()
        } ?: default

    protected suspend inline fun <T> safeCall(
        default: T? = null,
        crossinline block: suspend () -> T
    ): T? = try {
        block()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        default
    }
}

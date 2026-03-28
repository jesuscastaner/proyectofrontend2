package net.unir.proyectofrontend2.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.unir.proyectofrontend2.data.local.InMemoryPostStorage
import net.unir.proyectofrontend2.data.local.PostStorage
import net.unir.proyectofrontend2.data.remote.KtorPostApi
import net.unir.proyectofrontend2.data.remote.PostApi
import net.unir.proyectofrontend2.data.repository.PostRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json) // json(json, contentType = ContentType.Any)
            }
        }
    }

    single<PostApi> { KtorPostApi(get()) }
    single<PostStorage> { InMemoryPostStorage() }
    single {
        PostRepository(get(), get()).apply {
            initialize()
        }
    }
}

fun initKoin() = initKoin(emptyList())

fun initKoin(extraModules: List<Module>) {
    startKoin {
        modules(
            dataModule,
            *extraModules.toTypedArray(),
        )
    }
}

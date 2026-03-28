package net.unir.proyectofrontend2

import android.app.Application
import net.unir.proyectofrontend2.di.initKoin
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.PostListViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.UserDetailsViewModel
import org.koin.dsl.module

class PostApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { PostListViewModel(get()) }
                    factory { PostDetailsViewModel(get()) }
                    factory { UserDetailsViewModel(get()) }
                }
            )
        )
    }
}

package net.unir.proyectofrontend2

import android.app.Application
import net.unir.proyectofrontend2.di.initKoin
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.PostsFeedViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.UserProfileViewModel
import org.koin.dsl.module

class PostApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory {
                        PostsFeedViewModel(
                            postRepository = get()
                        )
                    }
                    factory {
                        PostDetailsViewModel(
                            postRepository = get()
                        )
                    }
                    factory {
                        UserProfileViewModel(
                            userRepository = get(),
                            postRepository = get(),
                        )
                    }
                }
            )
        )
    }
}

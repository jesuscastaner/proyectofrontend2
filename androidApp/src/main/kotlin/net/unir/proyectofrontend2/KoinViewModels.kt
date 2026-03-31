package net.unir.proyectofrontend2

import android.app.Application
import net.unir.proyectofrontend2.di.initKoin
import net.unir.proyectofrontend2.presentation.viewmodel.ExpressionDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.ExpressionsFeedViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.ManifestationsFeedViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.PostsFeedViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.UserProfileViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.WorkDetailsViewModel
import net.unir.proyectofrontend2.presentation.viewmodel.WorksFeedViewModel
import org.koin.dsl.module

class KoinViewModels : Application() {
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
                    factory {
                        ManifestationsFeedViewModel(
                            manifestationRepository = get(),
                            expressionRepository = get(),
                            workRepository = get(),
                        )
                    }
                    factory {
                        ManifestationDetailsViewModel(
                            manifestationRepository = get(),
                            expressionRepository = get(),
                            workRepository = get(),
                        )
                    }
                    factory {
                        ExpressionsFeedViewModel(
                            expressionRepository = get(),
                            workRepository = get(),
                        )
                    }
                    factory {
                        ExpressionDetailsViewModel(
                            expressionRepository = get(),
                            workRepository = get(),
                            manifestationRepository = get(),
                        )
                    }
                    factory {
                        WorksFeedViewModel(
                            workRepository = get(),
                        )
                    }
                    factory {
                        WorkDetailsViewModel(
                            expressionRepository = get(),
                            workRepository = get(),
                        )
                    }
                }
            )
        )
    }
}

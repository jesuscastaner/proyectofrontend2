package net.unir.proyectofrontend2

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import net.unir.proyectofrontend2.ui.screens.ManifestationDetailsScreen
import net.unir.proyectofrontend2.ui.screens.ManifestationsFeedScreen
import net.unir.proyectofrontend2.ui.screens.PostDetailsScreen
import net.unir.proyectofrontend2.ui.screens.PostsFeedScreen
import net.unir.proyectofrontend2.ui.screens.UserProfileScreen

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = PostsFeedDestination
            ) {
                composable<PostsFeedDestination> {
                    PostsFeedScreen(
                        navigateToManifestationsFeed = {
                            navController.navigate(ManifestationsFeedDestination)
                        },
                        navigateToPostDetails = { id ->
                            navController.navigate(PostDetailsDestination(id))
                        },
                        navigateToUserProfile = { id ->
                            navController.navigate(UserProfileDestination(id))
                        },
                    )
                }
                composable<PostDetailsDestination> { backStackEntry ->
                    val args = backStackEntry.toRoute<PostDetailsDestination>()

                    PostDetailsScreen(
                        id = args.id,
                        navigateToPostDetails = { id ->
                            navController.navigate(PostDetailsDestination(id))
                        },
                        navigateToUserProfile = { userId ->
                            navController.navigate(UserProfileDestination(userId))
                        },
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable<UserProfileDestination> { backStackEntry ->
                    val args = backStackEntry.toRoute<UserProfileDestination>()

                    UserProfileScreen(
                        id = args.id,
                        navigateToPostDetails = { postId ->
                            navController.navigate(PostDetailsDestination(postId))
                        },
                        navigateBack = { navController.popBackStack() }
                    )
                }
                composable<ManifestationsFeedDestination> {
                    ManifestationsFeedScreen(
                        navigateToPostsFeed = {
                            navController.navigate(PostsFeedDestination)
                        },
                        navigateToManifestationDetails = { id ->
                            navController.navigate(ManifestationDetailsDestination(id))
                        },
                    )
                }
                composable<ManifestationDetailsDestination> { backStackEntry ->
                    val args = backStackEntry.toRoute<ManifestationDetailsDestination>()

                    ManifestationDetailsScreen(
                        id = args.id,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Serializable
object PostsFeedDestination

@Serializable
data class PostDetailsDestination(val id: Long)

@Serializable
data class UserProfileDestination(val id: Long)

@Serializable
object ManifestationsFeedDestination

@Serializable
data class ManifestationDetailsDestination(val id: Long)

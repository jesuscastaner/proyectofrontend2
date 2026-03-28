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
import net.unir.proyectofrontend2.ui.screens.PostDetailsScreen
import net.unir.proyectofrontend2.ui.screens.PostListScreen
import net.unir.proyectofrontend2.ui.screens.UserDetailsScreen

@Serializable
object PostListDestination

@Serializable
data class PostDetailsDestination(val id: Long)

@Serializable
data class UserDetailsDestination(val id: Long)

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = PostListDestination
            ) {
                composable<PostListDestination> {
                    PostListScreen(
                        navigateToPostDetails = { id ->
                            navController.navigate(PostDetailsDestination(id))
                        }
                    )
                }

                composable<PostDetailsDestination> { backStackEntry ->
                    val args = backStackEntry.toRoute<PostDetailsDestination>()

                    PostDetailsScreen(
                        id = args.id,
                        navigateToUserDetails = { userId ->
                            navController.navigate(UserDetailsDestination(userId))
                        },
                        navigateBack = { navController.popBackStack() }
                    )
                }

                composable<UserDetailsDestination> { backStackEntry ->
                    val args = backStackEntry.toRoute<UserDetailsDestination>()

                    UserDetailsScreen(
                        id = args.id,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

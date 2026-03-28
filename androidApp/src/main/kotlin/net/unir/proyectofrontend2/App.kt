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

@Serializable
object PostListDestination

@Serializable
data class PostDetailsDestination(val id: Long)

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
                    PostDetailsScreen(
                        id = backStackEntry.toRoute<PostDetailsDestination>().id,
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

package net.unir.proyectofrontend2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import net.unir.proyectofrontend2.ui.components.icons.BackIcon
import net.unir.proyectofrontend2.ui.components.icons.LogoutIcon
import net.unir.proyectofrontend2.ui.components.icons.ManifestationIcon
import net.unir.proyectofrontend2.ui.components.icons.OptionsIcon
import net.unir.proyectofrontend2.ui.components.icons.PostIcon
import net.unir.proyectofrontend2.ui.components.icons.UserIcon
import net.unir.proyectofrontend2.ui.screens.AgentDetailsScreen
import net.unir.proyectofrontend2.ui.screens.ExpressionDetailsScreen
import net.unir.proyectofrontend2.ui.screens.ManifestationDetailsScreen
import net.unir.proyectofrontend2.ui.screens.ManifestationsFeedScreen
import net.unir.proyectofrontend2.ui.screens.PostDetailsScreen
import net.unir.proyectofrontend2.ui.screens.PostsFeedScreen
import net.unir.proyectofrontend2.ui.screens.UserProfileScreen
import net.unir.proyectofrontend2.ui.screens.WorkDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBars(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // TODO: que solo aparezca el icono si se puede navegar hacia atras
    var canNavigateBack = true

    // TODO: controlar (en otra clase) el usuario activo
    var currentUserId = 5L

    val bottomBarItems: Map<Destination, @Composable () -> Unit> = mapOf(
        PostsFeedDestination to { PostIcon() },
        ManifestationsFeedDestination to { ManifestationIcon() },
    )
    val startDestination = bottomBarItems.keys.first()
    var selectedBottomBarTab by rememberSaveable {
        mutableIntStateOf(bottomBarItems.keys.indexOf(startDestination))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate(UserProfileDestination(currentUserId))
                            }
                        ) {
                            UserIcon()
                        }
                    }
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            BackIcon()
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* TODO: more options */ }
                    ) {
                        OptionsIcon()
                    }
                }
            )
        },
        bottomBar = {
            SecondaryTabRow(
                selectedTabIndex = selectedBottomBarTab,
            ) {
                bottomBarItems.entries.forEachIndexed { index, entry ->
                    Tab(
                        selected = selectedBottomBarTab == index,
                        onClick = {
                            navController.navigate(entry.key)
                            selectedBottomBarTab = index
                        },
                        icon = entry.value
                    )
                }
            }
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            composable<PostsFeedDestination> {
                PostsFeedScreen(
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
                    navigateToUserProfile = { id ->
                        navController.navigate(UserProfileDestination(id))
                    },
                )
            }
            composable<UserProfileDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<UserProfileDestination>()
                UserProfileScreen(
                    id = args.id,
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                    navigateToPostDetails = { id ->
                        navController.navigate(PostDetailsDestination(id))
                    },
                )
            }
            composable<ManifestationsFeedDestination> {
                ManifestationsFeedScreen(
                    navigateToManifestationDetails = { id ->
                        navController.navigate(ManifestationDetailsDestination(id))
                    },
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<ManifestationDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<ManifestationDetailsDestination>()
                ManifestationDetailsScreen(
                    id = args.id,
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                    navigateToExpressionDetails = { id ->
                        navController.navigate(ExpressionDetailsDestination(id))
                    },
                    navigateToWorkDetails = { id ->
                        navController.navigate(WorkDetailsDestination(id))
                    },
                )
            }
            composable<ExpressionDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<ExpressionDetailsDestination>()
                ExpressionDetailsScreen(
                    id = args.id,
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                    navigateToWorkDetails = { id ->
                        navController.navigate(WorkDetailsDestination(id))
                    },
                    navigateToManifestationDetails = { id ->
                        navController.navigate(ManifestationDetailsDestination(id))
                    },
                )
            }
            composable<WorkDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<WorkDetailsDestination>()
                WorkDetailsScreen(
                    id = args.id,
                    navigateToExpressionDetails = { id ->
                        navController.navigate(ExpressionDetailsDestination(id))
                    },
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<AgentDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<AgentDetailsDestination>()
                AgentDetailsScreen(
                    id = args.id,
                    navigateToManifestationDetails = { id ->
                        navController.navigate(ManifestationDetailsDestination(id))
                    },
                    navigateToExpressionDetails = { id ->
                        navController.navigate(ExpressionDetailsDestination(id))
                    },
                    navigateToWorkDetails = { id ->
                        navController.navigate(WorkDetailsDestination(id))
                    },
                )
            }
        }
    }
}

interface Destination

@Serializable
object PostsFeedDestination : Destination

@Serializable
data class PostDetailsDestination(val id: Long) : Destination

@Serializable
data class UserProfileDestination(val id: Long) : Destination

@Serializable
object ManifestationsFeedDestination : Destination

@Serializable
data class ManifestationDetailsDestination(val id: Long) : Destination

@Serializable
data class ExpressionDetailsDestination(val id: Long) : Destination

@Serializable
data class WorkDetailsDestination(val id: Long) : Destination

@Serializable
data class AgentDetailsDestination(val id: Long) : Destination

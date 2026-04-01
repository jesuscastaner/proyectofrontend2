package net.unir.proyectofrontend2.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import net.unir.proyectofrontend2.ui.screens.AgentDetailsScreen
import net.unir.proyectofrontend2.ui.screens.AgentsFeedScreen
import net.unir.proyectofrontend2.ui.screens.ExpressionsFeedScreen
import net.unir.proyectofrontend2.ui.screens.ManifestationDetailsScreen
import net.unir.proyectofrontend2.ui.screens.ManifestationsFeedScreen
import net.unir.proyectofrontend2.ui.screens.PostDetailsScreen
import net.unir.proyectofrontend2.ui.screens.PostsFeedScreen
import net.unir.proyectofrontend2.ui.screens.UserProfileScreen
import net.unir.proyectofrontend2.ui.screens.WorkDetailsScreen
import net.unir.proyectofrontend2.ui.screens.WorksFeedScreen

// TODO: refactorizar esta basura ilegible
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBars(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val bottomBarTabs = listOf(
        PostsFeedDestination,
        ManifestationsFeedDestination,
        ExpressionsFeedDestination,
        WorksFeedDestination,
        AgentsFeedDestination,
    )
    val startDestination = bottomBarTabs[1]
    var selectedBottomBarTab by rememberSaveable {
        mutableIntStateOf(bottomBarTabs.indexOf(startDestination))
    }

    // TODO: que solo aparezca el icono si se puede navegar hacia atras
    var canNavigateBack = true

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            PrimaryTabRow(
                selectedTabIndex = selectedBottomBarTab,
            ) {
                Tab(
                    selected = selectedBottomBarTab == 0,
                    onClick = {
                        navController.navigate(PostsFeedDestination)
                        selectedBottomBarTab = 0
                    },
                    text = {
                        Text(
                            "P",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Tab(
                    selected = selectedBottomBarTab == 1,
                    onClick = {
                        navController.navigate(ManifestationsFeedDestination)
                        selectedBottomBarTab = 1
                    },
                    text = {
                        Text(
                            "M",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Tab(
                    selected = selectedBottomBarTab == 2,
                    onClick = {
                        navController.navigate(ExpressionsFeedDestination)
                        selectedBottomBarTab = 2
                    },
                    text = {
                        Text(
                            "E",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Tab(
                    selected = selectedBottomBarTab == 3,
                    onClick = {
                        navController.navigate(WorksFeedDestination)
                        selectedBottomBarTab = 3
                    },
                    text = {
                        Text(
                            "W",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Tab(
                    selected = selectedBottomBarTab == 4,
                    onClick = {
                        navController.navigate(AgentsFeedDestination)
                        selectedBottomBarTab = 4
                    },
                    text = {
                        Text(
                            "A",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
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
            composable<ExpressionsFeedDestination> {
                ExpressionsFeedScreen(
                    navigateToExpressionDetails = { id ->
                        navController.navigate(ExpressionDetailsDestination(id))
                    },
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<WorksFeedDestination> {
                WorksFeedScreen(
                    navigateToWorkDetails = { id ->
                        navController.navigate(WorkDetailsDestination(id))
                    },
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<WorkDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<WorkDetailsDestination>()
                WorkDetailsScreen(
                    id = args.id,
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<AgentsFeedDestination> {
                AgentsFeedScreen(
                    navigateToAgentDetails = { id ->
                        navController.navigate(AgentDetailsDestination(id))
                    },
                )
            }
            composable<AgentDetailsDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<AgentDetailsDestination>()
                AgentDetailsScreen(
                    id = args.id,
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
object ExpressionsFeedDestination : Destination

@Serializable
data class ExpressionDetailsDestination(val id: Long) : Destination

@Serializable
object WorksFeedDestination : Destination

@Serializable
data class WorkDetailsDestination(val id: Long) : Destination

@Serializable
object AgentsFeedDestination : Destination

@Serializable
data class AgentDetailsDestination(val id: Long) : Destination

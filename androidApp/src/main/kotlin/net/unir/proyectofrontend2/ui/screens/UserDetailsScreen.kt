package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.R
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.presentation.viewmodel.UserDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetailsScreen(
    id: Long,
    navigateBack: () -> Unit,
) {
    val viewModel: UserDetailsViewModel = koinViewModel()
    val user by viewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(user != null) { userAvailable ->
        if (userAvailable) {
            UserDetails(
                user = user!!,
                onBackClick = navigateBack
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDetails(
    user: User,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            AsyncImage(
                model = user.profilePic,
                contentDescription = user.username,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            SelectionContainer {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        user.displayName,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        user.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        user.username,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        user.website,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        user.bio,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (user.verified) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Verificado",
                                tint = Color.Green
                            )
                        }
                    }
                }
            }
        }
    }
}

package net.unir.proyectofrontend2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import net.unir.proyectofrontend2.R
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.presentation.viewmodel.PostDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostDetailsScreen(
    id: Long,
    navigateBack: () -> Unit,
) {
    val viewModel: PostDetailViewModel = koinViewModel()
    val post by viewModel.post.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.setId(id)
    }

    AnimatedContent(post != null) { objectAvailable ->
        if (objectAvailable) {
            PostDetails(post!!, onBackClick = navigateBack)
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetails(
    post: Post,
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
                model = post.image,
                contentDescription = post.content,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            SelectionContainer {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        post.userId.toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(6.dp))
                    LabeledInfo(
                        stringResource(R.string.label_user),
                        post.userId.toString(),
                    )
                    LabeledInfo(
                        stringResource(R.string.label_content),
                        post.content,
                    )
                    LabeledInfo(
                        stringResource(R.string.label_is_quote),
                        post.isQuote.toString(),
                    )
                    LabeledInfo(
                        stringResource(R.string.label_is_quote),
                        post.isVisible.toString(),
                    )
                    LabeledInfo(
                        stringResource(R.string.label_created_at),
                        post.createdAt,
                    )
                    LabeledInfo(
                        stringResource(R.string.label_updated_at),
                        post.updatedAt,
                    )
                }
            }
        }
    }
}

@Composable
private fun LabeledInfo(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                append(data)
            }
        )
    }
}

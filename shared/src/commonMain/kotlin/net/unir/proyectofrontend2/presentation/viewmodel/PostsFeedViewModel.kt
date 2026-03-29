package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.repository.PostRepository

class PostsFeedViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    @NativeCoroutinesState
    val posts: StateFlow<List<Post>> = postRepository.getPosts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}

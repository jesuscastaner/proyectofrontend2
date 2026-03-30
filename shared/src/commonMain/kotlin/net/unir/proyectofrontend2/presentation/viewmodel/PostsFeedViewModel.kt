package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.repository.PostRepository

class PostsFeedViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    @NativeCoroutinesState
    val posts: StateFlow<List<Post>> = postRepository.getPosts()
        .map { list -> list.filter { it.replyToId == null } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10000),
            emptyList()
        )
}

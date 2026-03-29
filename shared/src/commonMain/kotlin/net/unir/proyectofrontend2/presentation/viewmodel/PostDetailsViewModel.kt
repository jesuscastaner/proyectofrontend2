package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.unir.proyectofrontend2.data.model.Post
import net.unir.proyectofrontend2.data.repository.PostRepository

class PostDetailsViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val post: StateFlow<Post?> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(null)
        postRepository.getPostById(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun setId(id: Long) {
        this.id.value = id
    }
}

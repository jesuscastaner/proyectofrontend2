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
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.data.repository.PostRepository
import net.unir.proyectofrontend2.data.repository.UserRepository

class UserDetailsViewModel(
    userRepository: UserRepository,
    postRepository: PostRepository,
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val user: StateFlow<User?> = id.flatMapLatest {
        val id = it ?: return@flatMapLatest flowOf(null)
        userRepository.getUserById(id)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val posts: StateFlow<List<Post>> = id.flatMapLatest { userId ->
        userId?.let { postRepository.getPostsByUserId(it) } ?: flowOf(emptyList())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setId(id: Long) {
        this.id.value = id
    }
}

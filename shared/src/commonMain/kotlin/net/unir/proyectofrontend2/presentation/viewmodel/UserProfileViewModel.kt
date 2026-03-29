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

class UserProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val user: StateFlow<User?> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(null)
        userRepository.getUserById(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val posts: StateFlow<List<Post>> = id.flatMapLatest { userId ->
        userId ?: return@flatMapLatest flowOf(emptyList())
        postRepository.getPostsByUserId(userId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setId(id: Long) {
        this.id.value = id
    }
}

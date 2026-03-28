package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.User
import net.unir.proyectofrontend2.data.repository.UserRepository

class UserListViewModel(postRepository: UserRepository) : ViewModel() {
    @NativeCoroutinesState
    val users: StateFlow<List<User>> = postRepository.getPosts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}

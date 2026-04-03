package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.unir.proyectofrontend2.data.model.Shelf
import net.unir.proyectofrontend2.data.repository.ShelfRepository

class UserShelvesViewModel(
    private val shelfRepository: ShelfRepository
) : ViewModel() {

    // El ID del usuario actual, igual que en el UserProfileViewModel
    private val userId = MutableStateFlow<Long?>(null)

    // Estado para controlar lo que el usuario escribe en el input
    val newShelfName = MutableStateFlow("")

    fun setUserId(id: Long) {
        this.userId.value = id
        // Cuando nos pasen el ID, disparamos la petición a la API
        viewModelScope.launch {
            shelfRepository.fetchShelvesByUserId(id)
        }
    }

    // El StateFlow que escucha nuestra memoria caché, compatible con iOS
    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val shelves: StateFlow<List<Shelf>> = userId.flatMapLatest { id ->
        id ?: return@flatMapLatest flowOf(emptyList())
        shelfRepository.getShelves()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onNewShelfNameChanged(name: String) {
        newShelfName.value = name
    }

    fun createShelf() {
        val name = newShelfName.value
        val currentId = userId.value

        // Evitamos crear colecciones vacías o si no tenemos el ID del usuario
        if (name.isBlank() || currentId == null) return

        viewModelScope.launch {
            shelfRepository.createShelf(userId = currentId, name = name)
            newShelfName.value = ""
        }
    }
}

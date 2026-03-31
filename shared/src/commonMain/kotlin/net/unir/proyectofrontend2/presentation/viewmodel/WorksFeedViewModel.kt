package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.repository.WorkRepository

class WorksFeedViewModel(
    private val workRepository: WorkRepository,
) : ViewModel() {
    @NativeCoroutinesState
    val works: StateFlow<List<Work>> =
        workRepository.getWorks()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}

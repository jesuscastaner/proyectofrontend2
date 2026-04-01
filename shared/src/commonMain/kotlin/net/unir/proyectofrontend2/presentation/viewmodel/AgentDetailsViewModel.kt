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
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.repository.AgentRepository

class AgentDetailsViewModel(
    private val agentRepository: AgentRepository,
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    fun setId(id: Long) {
        this.id.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val agent: StateFlow<Agent?> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(null)
        agentRepository.getAgentById(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
}

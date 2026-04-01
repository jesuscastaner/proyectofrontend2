package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.repository.AgentRepository

class AgentsFeedViewModel(
    private val agentRepository: AgentRepository,
) : ViewModel() {
    @NativeCoroutinesState
    val agents: StateFlow<List<Agent>> =
        agentRepository.getAgents()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}

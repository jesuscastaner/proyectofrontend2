package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.repository.AgentRepository
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.ManifestationRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class ManifestationCreateFormViewModel(
    private val manifestationRepository: ManifestationRepository,
    private val expressionRepository: ExpressionRepository,
    private val workRepository: WorkRepository,
    private val agentRepository: AgentRepository,
) : ViewModel() {
    @NativeCoroutinesState
    val manifestations: StateFlow<List<Manifestation>> =
        manifestationRepository.getManifestations()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    @NativeCoroutinesState
    val expressions: StateFlow<List<Expression>> = expressionRepository.getExpressions()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    @NativeCoroutinesState
    val works: StateFlow<List<Work>> = workRepository.getWorks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    @NativeCoroutinesState
    val agents: StateFlow<List<Agent>> =
        agentRepository.getAgents()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // TODO: add "val nomens" and "val languages"
}


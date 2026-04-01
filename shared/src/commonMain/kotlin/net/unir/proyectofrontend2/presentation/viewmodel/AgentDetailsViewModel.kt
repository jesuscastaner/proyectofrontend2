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
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.repository.AgentRepository
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.ManifestationRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class AgentDetailsViewModel(
    private val agentRepository: AgentRepository,
    private val workRepository: WorkRepository,
    private val expressionRepository: ExpressionRepository,
    private val manifestationRepository: ManifestationRepository,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val works: StateFlow<List<Work>> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(emptyList())
        workRepository.getWorksByAgentId(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val expressions: StateFlow<List<Expression>> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(emptyList())
        expressionRepository.getExpressionsByAgentId(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val manifestations: StateFlow<List<Manifestation>> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(emptyList())
        manifestationRepository.getManifestationsByAgentId(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}

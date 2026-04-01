package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.ManifestationRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class ExpressionDetailsViewModel(
    private val expressionRepository: ExpressionRepository,
    private val workRepository: WorkRepository,
    private val manifestationRepository: ManifestationRepository,
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    fun setId(id: Long) {
        this.id.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val expression: StateFlow<Expression?> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(null)
        expressionRepository.getExpressionById(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val work: StateFlow<Work?> = expression.flatMapLatest {
        it?.workId ?: return@flatMapLatest flowOf(null)
        workRepository.getWorkById(it.workId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val manifestations: StateFlow<List<Manifestation>> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(emptyList())
        manifestationRepository.getManifestationsByExpressionId(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val agents: StateFlow<List<PaticipantAgent>> = combine(
        expression,
        work
    ) { expression, work ->
        val expressionAgents = expression?.agents ?: emptyList()
        val workAgents = work?.agents ?: emptyList()
        expressionAgents + workAgents
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}

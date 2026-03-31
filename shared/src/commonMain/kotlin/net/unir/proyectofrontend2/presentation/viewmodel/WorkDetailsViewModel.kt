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
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Work
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class WorkDetailsViewModel(
    private val workRepository: WorkRepository,
    private val expressionRepository: ExpressionRepository,
) : ViewModel() {
    private val id = MutableStateFlow<Long?>(null)

    fun setId(id: Long) {
        this.id.value = id
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val work: StateFlow<Work?> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(null)
        workRepository.getWorkById(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val expressions: StateFlow<List<Expression>> = id.flatMapLatest {
        it ?: return@flatMapLatest flowOf(emptyList())
        expressionRepository.getExpressionsByWorkId(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}

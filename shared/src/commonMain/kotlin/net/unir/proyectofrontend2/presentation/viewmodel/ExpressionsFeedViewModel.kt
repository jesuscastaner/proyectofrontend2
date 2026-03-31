package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class ExpressionsFeedViewModel(
    private val expressionRepository: ExpressionRepository,
    private val workRepository: WorkRepository,
) : ViewModel() {
    @NativeCoroutinesState
    val expressions: StateFlow<List<Expression>> =
        expressionRepository.getExpressions()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    @NativeCoroutinesState
    val authorsMap: StateFlow<Map<Long, List<PaticipantAgent>>> =
        combine(
            expressions,
            workRepository.getWorks()
        ) { expressions, works ->
            val worksMap = works.associateBy { it.id }
            expressions.associate { expression ->
                val work = worksMap[expression.workId]
                expression.id to (
                    work?.agents?.filter {
                        it.role.equals("author", ignoreCase = true)
                    }
                        ?: emptyList()
                    )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )
}

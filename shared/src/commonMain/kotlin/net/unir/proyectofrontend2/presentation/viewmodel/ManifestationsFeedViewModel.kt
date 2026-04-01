package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.PaticipantAgent
import net.unir.proyectofrontend2.data.repository.ExpressionRepository
import net.unir.proyectofrontend2.data.repository.ManifestationRepository
import net.unir.proyectofrontend2.data.repository.WorkRepository

class ManifestationsFeedViewModel(
    private val manifestationRepository: ManifestationRepository,
    private val expressionRepository: ExpressionRepository,
    private val workRepository: WorkRepository,
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
    val authorsMap: StateFlow<Map<Long, List<PaticipantAgent>>> =
        combine(
            manifestations,
            expressionRepository.getExpressions(),
            workRepository.getWorks()
        ) { manifestations, expressions, works ->
            val expressionsMap = expressions.associateBy { it.id }
            val worksMap = works.associateBy { it.id }
            manifestations.associate { manifestation ->
                val expression = expressionsMap[manifestation.expressionId]
                val work = expression?.let { worksMap[it.workId] }
                manifestation.id to (
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

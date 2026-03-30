package net.unir.proyectofrontend2.presentation.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.repository.ManifestationRepository

class ManifestationsFeedViewModel(
    private val manifestationRepository: ManifestationRepository
) : ViewModel() {
    @NativeCoroutinesState
    val manifestations: StateFlow<List<Manifestation>> =
        manifestationRepository.getManifestations()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}

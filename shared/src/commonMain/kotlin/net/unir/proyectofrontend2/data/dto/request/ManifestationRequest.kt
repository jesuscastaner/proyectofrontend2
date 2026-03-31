package net.unir.proyectofrontend2.data.dto.request

import kotlinx.serialization.Serializable
import net.unir.proyectofrontend2.data.model.PaticipantAgent

@Serializable
data class ManifestationRequest(
    // TODO: faltan campos
    val expressionId: Long,
    val title: String,
    val publicationYear: Short?,
    val isbn: String?,
    val coverImage: String?,
    val agents: List<PaticipantAgent> = emptyList(),
)

package net.unir.proyectofrontend2.data.dto.mapper

import net.unir.proyectofrontend2.data.dto.request.ManifestationRequest
import net.unir.proyectofrontend2.data.dto.response.ManifestationResponse
import net.unir.proyectofrontend2.data.model.Manifestation

//fun ManifestationResponse.toManifestation(): Manifestation =
//    Manifestation(
//        id = id,
//        expressionId = expressionId,
//        title = title,
//        publicationYear = publicationYear,
//        isbn = isbn,
//        coverImage = coverImage,
//        agents = agents.map { it.toAgentWithRole() },
//    )

fun Manifestation.toRequest(): ManifestationRequest =
    ManifestationRequest(
        expressionId = expressionId,
        title = title,
        publicationYear = publicationYear,
        isbn = isbn,
        coverImage = coverImage,
        agents = agents,
    )

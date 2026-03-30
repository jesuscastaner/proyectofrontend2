package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Agent
import net.unir.proyectofrontend2.data.model.Expression
import net.unir.proyectofrontend2.data.model.Manifestation
import net.unir.proyectofrontend2.data.model.Work

interface LibraryApi {
    suspend fun getWorks(): List<Work>
    suspend fun getExpressions(): List<Expression>
    suspend fun getManifestations(): List<Manifestation>
    suspend fun getAgents(): List<Agent>
}

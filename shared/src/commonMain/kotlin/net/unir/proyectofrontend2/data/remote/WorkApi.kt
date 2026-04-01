package net.unir.proyectofrontend2.data.remote

import net.unir.proyectofrontend2.data.model.Work

interface WorkApi {
    suspend fun getWorks(): List<Work>
}

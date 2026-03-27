package net.unir.proyectofrontend2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

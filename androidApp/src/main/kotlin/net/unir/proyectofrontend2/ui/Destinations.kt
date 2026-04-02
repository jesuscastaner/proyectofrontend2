package net.unir.proyectofrontend2.ui

import kotlinx.serialization.Serializable

interface Destination

@Serializable
object PostsFeedDestination : Destination

@Serializable
data class PostDetailsDestination(val id: Long) : Destination

@Serializable
data class UserProfileDestination(val id: Long) : Destination

@Serializable
object ManifestationsFeedDestination : Destination

@Serializable
data class ManifestationDetailsDestination(val id: Long) : Destination

@Serializable
data class ExpressionDetailsDestination(val id: Long) : Destination

@Serializable
data class WorkDetailsDestination(val id: Long) : Destination

@Serializable
data class AgentDetailsDestination(val id: Long) : Destination

@Serializable
object ManifestationCreateFormDestination : Destination

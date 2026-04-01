package net.unir.proyectofrontend2.data.dto.mapper

import net.unir.proyectofrontend2.data.dto.response.UserResponse
import net.unir.proyectofrontend2.data.model.User

fun UserResponse.toUser(): User =
    User(
        id = id,
        agentId = agentId,
        displayName = displayName,
        username = username,
        profilePic = profilePic,
        bio = bio,
        website = website,
        isVerified = isVerified,
        isSuspended = isSuspended,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

package com.martynov.dto

import com.martynov.model.MediaModel
import com.martynov.model.UserModel

class UserResponeDto(username : String) {
    val username = username
    companion object {
        fun fromModel(model: UserModel) = UserResponeDto(
                username = model.username

        )
    }

    override fun toString(): String {
        return "username : $username"
    }
}
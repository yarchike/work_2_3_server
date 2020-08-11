package com.martynov.dto

import com.martynov.model.MediaModel
import com.martynov.model.MediaType


data class MediaResponseDto(val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: MediaModel) = MediaResponseDto(
                id = model.id,
                mediaType = model.mediaType
        )
    }
}
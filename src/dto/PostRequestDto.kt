package com.martynov.dto

data class PostRequestDto(
    val id: Long,
    val autor: String? = null,
    val postResurse: String? = null,
    var like: Int = 0,
    var isLike: Boolean =false,
    val dateRepost: Long? = null,
    val autorRepost: String? = null
)

package com.martynov.model


data class PostModel(
    val id: Long,
    val date: Long = 0,
    val autor: String,
    val postResurse: String? = null,
    var like: Int = 0,
    val comments: Int = 0,
    val share: Int = 0,
    var isLike: Boolean = false,
    val isComment: Boolean = false,
    val isShare: Boolean = false,
    val adress: String? = null,
    val coordinates: Pair<Double, Double>? = null,
    val type: PostTypes = PostTypes.Events,
    val url: String? = null,
    val dateRepost: Long? = null,
    val autorRepost: String? = null,
    var hidePost: Boolean = false

)

enum class PostTypes {
    Events, Reposts, YoutubeVideo, SponsoredPosts
}
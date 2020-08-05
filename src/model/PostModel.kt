package com.martynov.model


data class PostModel(
    val id: Long = 0,
    val date: Long = 0,
    val autor: String? = null,
    val postResurse: String? = null,
    val like: Int = 0,
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
    val hidePost: Boolean = false,
    val viewsPost: Long = 0,
    val autorId: Long = -1,
    val postIsLike: ArrayList<Long> = ArrayList()

)

enum class PostTypes {
    Events, Reposts, YoutubeVideo, SponsoredPosts
}

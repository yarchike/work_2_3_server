package com.martynov.dto

import com.martynov.model.PostModel
import com.martynov.model.PostTypes


data class PostResponseDto(
        val id: Long = 0,
        val autor: String?,
        val postResurse: String? = null,
        val date: Long = 0,
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
        var hidePost: Boolean = false,
        var viewsPost: Long = 0,
        val repostResurs:PostModel?=null

) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
                id = model.id,
                autor = model.autor,
                postResurse = model.postResurse,
                date = model.date,
                like = model.like,
                comments = model.comments,
                share = model.share,
                isLike = model.isLike,
                isComment = model.isComment,
                isShare = model.isShare,
                adress = model.adress,
                coordinates = model.coordinates,
                type = model.type,
                url = model.url,
                dateRepost = model.dateRepost,
                autorRepost = model.autorRepost,
                hidePost = model.hidePost,
                viewsPost = model.viewsPost,
                repostResurs = model.repostResurs


        )
    }
}
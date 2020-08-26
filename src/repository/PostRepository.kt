package com.martynov.repository

import com.martynov.model.AttachmentModel
import com.martynov.model.PostModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun save(item: PostModel): PostModel
    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long, userId:Long?): PostModel?
    suspend fun dislikeById(id: Long, userId:Long?): PostModel?
    suspend fun repost(item: PostModel): PostModel?
    suspend fun getfive():List<PostModel>
    suspend fun getOld(id: Long): List<PostModel>
    suspend fun newPost(postResurse: String, attachment: AttachmentModel?): List<PostModel>

}
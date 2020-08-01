package com.martynov.service

import com.martynov.dto.PostRequestDto
import com.martynov.dto.PostResponseDto
import com.martynov.model.PostModel
import com.martynov.repository.PostRepository
import io.ktor.features.NotFoundException

class PostService(private val repo: PostRepository) {
    suspend fun getAll(): List<PostResponseDto> {
        return repo.getAll().map { PostResponseDto.fromModel(it) }
    }

    suspend fun getById(id: Long): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(input: PostRequestDto): PostResponseDto {
        val model = PostModel(id = input.id, autor = input.autor, postResurse = input.postResurse)
        return PostResponseDto.fromModel(repo.save(model))
    }
}
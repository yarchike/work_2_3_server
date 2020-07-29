package com.martynov.route

import com.martynov.dto.PostRequestDto
import com.martynov.dto.PostResponseDto
import com.martynov.model.PostModel
import com.martynov.repository.PostRepository
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

fun Routing.v1() {
    val repo by kodein().instance<PostRepository>()
    route("/api/v1") {
        get("/posts") {
            val response = repo.getAll()
            for (post in response) {
                post.viewsPost = post.viewsPost + 1
            }
            call.respond(response)
        }
        get("/posts/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
        post("/posts/like") {
            val request = call.receive<PostRequestDto>()
            val model = PostModel(id = request.id)
            val response = repo.likeById(model.id) ?: throw NotFoundException()
            call.respond(response)
        }
        post("/posts/dislike") {
            val request = call.receive<PostRequestDto>()
            val model = PostModel(id = request.id)
            val response = repo.dislikeById(model.id) ?: throw NotFoundException()
            call.respond(response)
        }
        post("/posts/repost") {
            val request = call.receive<PostRequestDto>()
            val model = PostModel(id = request.id, dateRepost = request.dateRepost, autorRepost = request.autorRepost)
            val response = repo.repost(model) ?: throw NotFoundException()
            call.respond(response)
        }
        delete("/posts/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            repo.removeById(id)
            call.respondText { "Успешно удалено" }
        }
    }
}

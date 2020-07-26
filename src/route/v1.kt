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
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

fun Routing.v1(){
    val repo by kodein().instance<PostRepository>()
    route("/api/v1/posts") {
        get {
            val response = repo.getAll()
            call.respond(response)
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
        post ("/api/v1/posts") {
            val request = call.receive<PostRequestDto>()
            val model = PostModel(id = request.id, autor = request.autor, postResurse = request.postResurse, like = request.like, isLike = request.isLike)
            call.respondText { "Успешно" }

            /*val response = repo.save(model)
            call.respond(response)*/
        }


    }

}

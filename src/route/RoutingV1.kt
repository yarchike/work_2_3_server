package com.martynov.route

import com.martynov.dto.AuthenticationRequestDto
import com.martynov.dto.PostRequestDto
import com.martynov.dto.PostResponseDto
import com.martynov.dto.UserResponseDto
import com.martynov.model.PostModel
import com.martynov.model.UserModel
import com.martynov.repository.PostRepository
import com.martynov.service.UserService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

class RoutingV1(
    private val userService: UserService
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            val repo by kodein().instance<PostRepository>()
            route("/api/v1") {
                authenticate {
                    route("/me") {
                        get {
                            val me = call.authentication.principal<UserModel>()
                            call.respond(UserResponseDto.fromModel(me!!))
                        }
                    }
                    get("/posts") {
                        val response = repo.getAll()
                        call.respond(response)
                    }
                    get("/posts/{id}") {
                        val id =
                            call.parameters["id"]?.toLongOrNull()
                                ?: throw ParameterConversionException("id", "Long")
                        val model = repo.getById(id) ?: throw NotFoundException()
                        val response = PostResponseDto.fromModel(model)
                        call.respond(response)
                    }
                    post("/posts/like") {
                        val request = call.receive<PostRequestDto>()
                        val me = call.authentication.principal<UserModel>()
                        val response = repo.likeById(request.id, me?.id) ?: throw NotFoundException()
                        call.respond(response)
                    }
                    post("/posts/dislike") {
                        val request = call.receive<PostRequestDto>()
                        val me = call.authentication.principal<UserModel>()
                        val response = repo.dislikeById(request.id, me?.id) ?: throw NotFoundException()
                        call.respond(response)
                    }
                    post("/posts/repost") {
                        val request = call.receive<PostRequestDto>()
                        val model =
                            PostModel(
                                id = request.id,
                                dateRepost = request.dateRepost,
                                autorRepost = request.autorRepost
                            )
                        val response = repo.repost(model) ?: throw NotFoundException()
                        call.respond(response)
                    }
                    delete("/posts/{id}") {
                        val me = call.authentication.principal<UserModel>()

                        val id =
                            call.parameters["id"]?.toLongOrNull()
                                ?: throw ParameterConversionException("id", "Long")
                        if(me?.id == id){
                            repo.removeById(id)
                            call.respond(HttpStatusCode.OK)
                        }else{
                            call.respond(HttpStatusCode.Forbidden)
                        }


                    }
                }
                post("/registration") {
                    val input = call.receive<AuthenticationRequestDto>()
                    val response = userService.addUser(input.username, input.password)
                    call.respond(response)
                }
                post("/authentication") {
                    val input = call.receive<AuthenticationRequestDto>()
                    val response = userService.authenticate(input)
                    call.respond(response)
                }
            }
        }
    }
}

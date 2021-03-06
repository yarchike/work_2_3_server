package com.martynov.route

import com.martynov.dto.*
import com.martynov.model.PostModel
import com.martynov.model.UserModel
import com.martynov.repository.PostRepository
import com.martynov.service.FCMService
import com.martynov.service.FileService
import com.martynov.service.PostService
import com.martynov.service.UserService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

//
class RoutingV1(
        private val staticPath: String,
        //private val postService: PostService,
        private val fileService: FileService,
        private val userService: UserService,
        private  val fcmService: FCMService

) {
    fun setup(configuration: Routing) {
        with(configuration) {
            val repo by kodein().instance<PostRepository>()




            route("/api/v1") {
                static("/static") {
                    files(staticPath)
                }
                authenticate {


                    route("/media") {
                        post {
                            val multipart = call.receiveMultipart()
                            val response = fileService.save(multipart)
                            call.respond(response)

                        }
                    }
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
                    post("/posts/{id}/likes") {
                        val id = call.parameters["id"]?.toLongOrNull()
                                        ?: throw ParameterConversionException("id", "Long")
                        val me = call.authentication.principal<UserModel>()
                        val response = repo.likeById(id, me?.id) ?: throw NotFoundException()
                        if (me != null) {
                            if(response.autor != null){
                                fcmService.send(id,userService.findTokenDeviceUser(response.autor), "Ващ пост лайкнул ${me.username}")
                            }
                        }
                        print(response)
                        call.respond(response)
                    }
                    delete("/posts/{id}/likes") {
                        val id =
                                call.parameters["id"]?.toLongOrNull()
                                        ?: throw ParameterConversionException("id", "Long")
                        val me = call.authentication.principal<UserModel>()
                        val response = repo.dislikeById(id, me?.id) ?: throw NotFoundException()
                        call.respond(response)
                    }
                    post("/repost") {
                        val request = call.receive<PostResponseDto>()
                        val model =
                                PostModel(
                                        postResurse = request.postResurse,
                                        repostResurs = request.repostResurs

                                )
                        val response = repo.repost(model) ?: throw NotFoundException()
                        call.respond(response)
                    }
                    post("/posts/new") {
                        val request = call.receive<PostResponseDto>()
                        print(request.toString())
                        val me = call.authentication.principal<UserModel>()
                        val response = repo.newPost(request.postResurse.toString(), request.attachment, me?.username)
                                ?: throw NotFoundException()
                        call.respond(response)
                    }
                    delete("/posts/{id}") {
                        val me = call.authentication.principal<UserModel>()

                        val id =
                                call.parameters["id"]?.toLongOrNull()
                                        ?: throw ParameterConversionException("id", "Long")
                        if (me?.id == id) {
                            repo.removeById(id)
                            call.respond(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.Forbidden)
                        }


                    }
                    get("posts/recent") {
                        val response = repo.getfive()
                        call.respond(response)
                    }
                    post("posts/old") {
                        val id = call.receive<Long>()
                        val response = repo.getOld(id)
                        call.respond(response)

                    }
                    post("/push") {
                        val input = call.receive<TokenDeviceDto>()
                        println(input.token)
                        val input2 = call.request.header("Authorization").toString().replace("Bearer ", "")
                        println(input2)
                        val user = userService.addTokenDevice(input2, input.token)
                        println(user)
                        call.respond(user)

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
                    val tokenDevice = userService.findTokenDevice(input)
                    fcmService.send(-1,tokenDevice, "Добро пожаловать ${input.username}")
                    call.respond(response)
                }
            }
        }
    }
}


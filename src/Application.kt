package com.martynov

import com.martynov.exception.UserAddException
import com.martynov.repository.PostRepository
import com.martynov.repository.PostRepositoryMutex
import com.martynov.repository.UserRepository
import com.martynov.repository.UserRepositoryInMemoryWithMutexImpl
import com.martynov.route.RoutingV1
import com.martynov.service.JWTTokenService
import com.martynov.service.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.server.cio.EngineMain
import kotlinx.coroutines.runBlocking
import org.kodein.di.generic.*
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.naming.ConfigurationException


fun main(args: Array<String>) {
    EngineMain.main(args)
}


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
    install(KodeinFeature) {
        constant(tag = "upload-dir") with (environment.config.propertyOrNull("ncraft.upload.dir")?.getString()
                ?: throw ConfigurationException("Upload dir is not specified"))
        bind<PostRepository>() with singleton { PostRepositoryMutex() }
        bind<PasswordEncoder>() with eagerSingleton { BCryptPasswordEncoder() }
        bind<JWTTokenService>() with eagerSingleton { JWTTokenService() }
        bind<UserRepository>() with eagerSingleton { UserRepositoryInMemoryWithMutexImpl() }
        bind<UserService>() with eagerSingleton {
            UserService(instance(), instance(), instance()).apply {
                runBlocking {
                    this@apply.addUser("yarchike", "Demira5891")
                }
            }
        }
        bind<RoutingV1>() with eagerSingleton { RoutingV1(instance(tag = "upload-dir"), instance(), instance()) }
    }
    install(Authentication) {
        jwt {
            val jwtService by kodein().instance<JWTTokenService>()
            verifier(jwtService.verifier)
            val userService by kodein().instance<UserService>()

            validate {
                val id = it.payload.getClaim("id").asLong()
                userService.getModelById(id)
            }
        }
    }
    install(StatusPages) {
        exception<NotImplementedError> { e ->
            call.respond(HttpStatusCode.NotImplemented, Error("Error"))
            throw e
        }
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest)
            throw e
        }
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
        exception<UserAddException> { e ->
            call.respond(HttpStatusCode.BadRequest, Error("\"error\": Пользователь с таким логином уже зарегистрирован"))
            throw e
        }
    }
    install(Routing) {
        val routingV1 by kodein().instance<RoutingV1>()
        routingV1.setup(this)
    }

}


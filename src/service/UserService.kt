package com.martynov.service

import com.martynov.dto.AuthenticationRequestDto
import com.martynov.dto.AuthenticationResponseDto
import com.martynov.dto.PasswordChangeRequestDto
import com.martynov.exception.InvalidPasswordException
import com.martynov.exception.PasswordChangeException
import com.martynov.exception.UserAddException
import com.martynov.model.UserModel
import com.martynov.repository.UserRepository
import io.ktor.features.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder


class UserService(
    private val repo: UserRepository,
    private val tokenService: JWTTokenService,
    private val passwordEncoder: PasswordEncoder
) {
    suspend fun getModelById(id: Long): UserModel? {
        return repo.getById(id)
    }

    suspend fun changePassword(id: Long, input: PasswordChangeRequestDto) {
        val model = repo.getById(id) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.old_password, model.password)) {
            throw PasswordChangeException("Неверный пароль!")
        }
        val copy = model.copy(password = passwordEncoder.encode(input.new_password))
        repo.save(copy)


    }

    suspend fun authenticate(input: AuthenticationRequestDto): AuthenticationResponseDto {
        val model = repo.getByUsername(input.username) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.password, model.password)) {
            throw InvalidPasswordException("Неверный пароль")
        }
        val token = tokenService.generate(model.id)

        return AuthenticationResponseDto(token)
    }

    suspend fun addUser(username: String, password: String): AuthenticationResponseDto {
        val model = UserModel(
            username = username,
            password = passwordEncoder.encode(password)
        )
        val checkingIsUser = repo.addUser(model)
        if(checkingIsUser) {
            val token = tokenService.generate(model.id)
            return AuthenticationResponseDto(token)
        }
            return throw UserAddException("\"error\": Пользователь с таким логином уже зарегистрирован")
    }


}
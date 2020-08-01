package com.martynov.repository

import com.martynov.model.UserModel

interface UserRepository {
    suspend fun getAll(): List<UserModel>
    suspend fun getById(id: Long): UserModel?
    suspend fun getByIds(ids: Collection<Long>): List<UserModel>
    suspend fun getByUsername(username: String): UserModel?
    suspend fun save(item: UserModel): UserModel
    fun getSizeListUser():Int
    fun addUserList(user:UserModel)
    suspend fun addUser(item: UserModel): Boolean

}
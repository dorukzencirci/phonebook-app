package com.example.phonebookapplication.data.repository

import com.example.phonebookapplication.data.model.AddUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.remote.ApiService

class UserRepository(private val api: ApiService) {
    suspend fun getAllUsers(): List<User> {
        val response = api.getAllUsers()
        return response.data ?: emptyList()
    }

    suspend fun getUserById(id: Int): User? {
        val response = api.getUserById(id)
        return response.data
    }

    suspend fun createUser(request: AddUserRequest): User? {
        val response = api.saveUser(request)
        return response.data
    }

    suspend fun updateUser(id: Int, request: UpdateUserRequest): User? {
        val response = api.updateUser(id, request)
        return response.data
    }

    suspend fun deleteUser(id: Int): User? {
        val response = api.deleteUser(id)
        return response.data
    }

    suspend fun uploadUserImage(request: UploadImageRequest) =
        api.uploadUserImage(request).data
}
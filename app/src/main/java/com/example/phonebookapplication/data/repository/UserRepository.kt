package com.example.phonebookapplication.data.repository

import com.example.phonebookapplication.data.model.BaseResponse
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.remote.ApiService

class UserRepository(private val api: ApiService) {
    suspend fun getAllUsers(): BaseResponse<List<User>> {
        val response = api.getAllUsers()
        return response
    }

    suspend fun getUserById(id: Int): BaseResponse<User> {
        val response = api.getUserById(id)
        return response
    }

    suspend fun saveUser(request: SaveUserRequest): BaseResponse<User> {
        val response = api.saveUser(request)
        return response
    }

    suspend fun updateUser(id: String, request: UpdateUserRequest): BaseResponse<User> {
        val response = api.updateUser(id, request)
        return response
    }

    suspend fun deleteUser(id: String): BaseResponse<User> {
        val response = api.deleteUser(id)
        return response
    }

    suspend fun uploadUserImage(request: UploadImageRequest) =
        api.uploadUserImage(request).data
}
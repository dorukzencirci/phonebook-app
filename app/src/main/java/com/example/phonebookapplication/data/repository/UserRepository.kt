package com.example.phonebookapplication.data.repository

import com.example.phonebookapplication.data.model.BaseResponse
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.UploadImageResponse
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.remote.ApiService

class UserRepository(private val api: ApiService) {
    suspend fun getAllUsers(): BaseResponse<List<User>> {
        return api.getAllUsers()
    }

    suspend fun getUserById(id: Int): BaseResponse<User> {
        return api.getUserById(id)
    }

    suspend fun saveUser(request: SaveUserRequest): BaseResponse<User> {
        return api.saveUser(request)
    }

    suspend fun updateUser(id: String, request: UpdateUserRequest): BaseResponse<User> {
        return api.updateUser(id, request)
    }

    suspend fun deleteUser(id: String): BaseResponse<User> {
        return api.deleteUser(id)
    }

    suspend fun uploadImage(request: UploadImageRequest): BaseResponse<UploadImageResponse> {
        return api.uploadImage(request)
    }
}
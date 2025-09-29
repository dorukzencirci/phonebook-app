package com.example.phonebookapplication.data.repository

import com.example.phonebookapplication.data.model.BaseResponse
import com.example.phonebookapplication.data.model.GetAllUsersResponse
import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageResponse
import com.example.phonebookapplication.data.model.User
import com.example.phonebookapplication.data.remote.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepository(private val api: ApiService) {
    suspend fun getAllUsers(): BaseResponse<GetAllUsersResponse> {
        return api.getAllUsers()
    }

    suspend fun getUserById(id: String): BaseResponse<User> {
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

    suspend fun uploadImage(file: File): BaseResponse<UploadImageResponse> {
        val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        return api.uploadImage(body)
    }
}
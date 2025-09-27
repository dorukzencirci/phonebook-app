package com.example.phonebookapplication.data.remote

import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.BaseResponse
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageRequest
import com.example.phonebookapplication.data.model.UploadImageResponse
import com.example.phonebookapplication.data.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("User/getAll")
    suspend fun getAllUsers(): BaseResponse<List<User>>

    @GET("User/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): BaseResponse<User>

    @POST("User")
    suspend fun saveUser(
        @Body request: SaveUserRequest
    ): BaseResponse<User>

    @PUT("User/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body request: UpdateUserRequest
    ): BaseResponse<User>

    @POST("User/UploadImage")
    suspend fun uploadUserImage(
        @Body request: UploadImageRequest
    ): BaseResponse<UploadImageResponse>

    @DELETE("User/{id}")
    suspend fun deleteUser(
        @Path("id") userId: String
    ): BaseResponse<User>
}
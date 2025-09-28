package com.example.phonebookapplication.data.remote

import com.example.phonebookapplication.data.model.SaveUserRequest
import com.example.phonebookapplication.data.model.BaseResponse
import com.example.phonebookapplication.data.model.GetAllUsersResponse
import com.example.phonebookapplication.data.model.UpdateUserRequest
import com.example.phonebookapplication.data.model.UploadImageResponse
import com.example.phonebookapplication.data.model.User
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("User/getAll")
    suspend fun getAllUsers(): BaseResponse<GetAllUsersResponse>

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

    @Multipart
    @POST("User/UploadImage")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): BaseResponse<UploadImageResponse>

    @DELETE("User/{id}")
    suspend fun deleteUser(
        @Path("id") userId: String
    ): BaseResponse<User>
}
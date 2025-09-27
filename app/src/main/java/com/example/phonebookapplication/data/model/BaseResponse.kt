package com.example.phonebookapplication.data.model

data class BaseResponse<T>(
    val success: String?,
    val messages: List<String>?,
    val data: T?,
    val status: Int?
)

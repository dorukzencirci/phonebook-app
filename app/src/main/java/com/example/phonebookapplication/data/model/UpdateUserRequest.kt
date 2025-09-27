package com.example.phonebookapplication.data.model

data class UpdateUserRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val profileImageUrl: String
)

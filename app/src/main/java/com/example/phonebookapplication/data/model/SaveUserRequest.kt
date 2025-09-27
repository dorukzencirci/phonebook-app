package com.example.phonebookapplication.data.model

data class SaveUserRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val profileImageUrl: String?
)

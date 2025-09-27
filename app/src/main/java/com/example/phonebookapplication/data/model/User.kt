package com.example.phonebookapplication.data.model

data class User(
    val id: String?,
    val createdAt: String?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val profileImageUrl: String?,
    var savedToPhone: Boolean = false
)

package com.example.quizapp.dto

data class UserDto (
    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val role: Role
)
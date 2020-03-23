package com.example.quizapp.dto

data class UserDto (
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val role: Role
)
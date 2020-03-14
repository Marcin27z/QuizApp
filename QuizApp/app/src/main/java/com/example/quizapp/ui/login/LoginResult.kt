package com.example.quizapp.ui.login

import com.example.quizbackend.dto.Role

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Int? = null,
    val error: Int? = null,
    val role: Role? = null
)
package com.example.quizapp.retrofit

import com.example.quizapp.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("/register")
    fun register(@Body user: UserDto): Call<Boolean?>
}
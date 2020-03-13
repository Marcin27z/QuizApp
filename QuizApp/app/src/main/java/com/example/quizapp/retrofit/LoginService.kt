package com.example.quizapp.retrofit

import com.example.quizbackend.dto.UserInfo
import retrofit2.Call
import retrofit2.http.GET


interface LoginService {
    @GET("/login")
    fun basicLogin(): Call<UserInfo?>
}
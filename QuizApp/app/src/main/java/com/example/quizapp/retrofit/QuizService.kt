package com.example.quizapp.retrofit

import com.example.quizapp.dto.QuizInfo
import com.example.quizbackend.dto.UserInfo
import retrofit2.Call
import retrofit2.http.GET

interface QuizService {

    @GET("/common/quiz")
    fun getQuizzesInfo(): Call<List<QuizInfo>?>?
}
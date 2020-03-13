package com.example.quizapp.retrofit

import com.example.quizapp.dto.Quiz
import com.example.quizbackend.dto.SolutionInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TutorService {


    @POST("/tutor/createSubject/{subjectName}")
    fun createSubject(@Path("subjectName") subjectName: String)

    @POST("/tutor/addQuiz/{subjectName}")
    fun addQuizToSubject(@Body quiz: Quiz, @Path("subjectName") subjectName: String)

    @GET("/tutor/solutions/{quizName}")
    fun getSolutionsForQuiz(@Path("quizName") quizName: String): Call<List<SolutionInfo>?>?
}
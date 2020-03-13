package com.example.quizapp.retrofit

import com.example.quizapp.dto.SolutionDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {

    @GET("/student/subscribe/{subjectName}")
    fun subscribeToSubject(@Path("subjectName") subjectName: String): Call<ResponseBody>


    @POST("/student/quiz/solution")
    fun submitSolution(@Body solutionDto: SolutionDto): Call<ResponseBody>
}
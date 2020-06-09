package com.example.quizapp.retrofit

import com.example.quizapp.models.QuizInfo
import com.example.quizapp.models.SolutionDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {

    @GET("/student/subscribe/{subjectName}")
    fun subscribeToSubject(@Path("subjectName") subjectName: String): Call<Boolean>

    @POST("/student/quiz/solution")
    fun submitSolution(@Body solutionDto: SolutionDto): Call<ResponseBody>

    @GET("/student/quiz")
    fun getQuizzesInfo(): Call<List<QuizInfo>?>

    @GET("/student/solved")
    suspend fun getSolvedQuizzesInfo(): List<QuizInfo>

    @GET("/student/quiz/subject/{subjectName}")
    fun getQuizzesInfoForSubject(@Path("subjectName") subjectName: String): Call<List<QuizInfo>?>

    @GET("/student/unsubscribe/{subjectName}")
    suspend fun unsubscribeFromSubject(@Path("subjectName") subjectName: String): ResponseBody
}
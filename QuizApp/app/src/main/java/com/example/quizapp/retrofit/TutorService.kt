package com.example.quizapp.retrofit

import com.example.quizapp.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TutorService {

    @POST("/tutor/createSubject/{subjectName}")
    suspend fun createSubject(@Path("subjectName") subjectName: String): Boolean

    @POST("/tutor/deleteSubject/{subjectName}")
    suspend fun deleteSubject(@Path("subjectName") subjectName: String): Call<ResponseBody>

    @POST("/tutor/addQuiz/{subjectName}")
    suspend fun addQuizToSubject(@Body quiz: QuizDto, @Path("subjectName") subjectName: String): Boolean

    @GET("/tutor/solutions/{quizName}")
    fun getSolutionsForQuiz(@Path("quizName") quizName: String): Call<List<SolutionInfo>>

    @GET("/tutor/quiz")
    suspend fun getQuizzesInfo(): List<QuizInfoTutor>

    @GET("/tutor/quiz/subject/{subjectName}")
    suspend fun getQuizzesInfoForSubject(@Path("subjectName") subjectName: String): List<QuizInfoTutor>
}
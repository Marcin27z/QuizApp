package com.example.quizapp.retrofit

import com.example.quizapp.models.Quiz
import com.example.quizapp.models.SubjectInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonService {

    @GET("/common/subjects")
    fun getSubjects(): Call<List<SubjectInfo>?>

    @GET("/common/quiz/{quizName}")
    fun getQuiz(@Path("quizName") quizName: String): Call<Quiz?>
}
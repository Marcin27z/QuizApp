package com.example.quizapp.retrofit

import com.example.quizapp.dto.Quiz
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.dto.SubjectInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommonService {

    @GET("/common/quiz")
    fun getQuizzesInfo(): Call<List<QuizInfo>?>?

    @GET("/common/subjects")
    fun getSubjects(): Call<List<SubjectInfo>?>?

    @GET("/common/quiz/{quizName}")
    fun getQuiz(@Path("quizName") quizName: String): Call<Quiz?>?

    @GET("/quiz/subject/{subjectName}")
    fun getQuizzesInfoForSubject(@Path("subjectName") subjectName: String): Call<List<QuizInfo>?>?
}
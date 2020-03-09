package com.example.quizapp.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.QuizDao
import com.example.quizapp.dto.Quiz
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizViewModel(quizName: String) : ViewModel() {

    private var quiz: Quiz? = null

    private var currentQuestion = 0

    private val _question = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }

    val question: LiveData<String> = _question

    private val _answers = MutableLiveData<List<String>>()
    val answers: LiveData<List<String>> = _answers

    var correctAnswer = 0

    init {
        val commonService = ServiceGenerator.createService(CommonService::class.java, "marcin1", "marcin1")
        val call = commonService.getQuiz(quizName)
        call?.enqueue(object: Callback<Quiz?> {

            override fun onResponse(call: Call<Quiz?>, response: Response<Quiz?>) {
                if (response.isSuccessful) {
                    quiz = response.body()
                    if (quiz == null) {
                        println("No quiz found")
                    }
                    updateView(currentQuestion)
                } else {
                    println("Something went wrong")
                }
            }

            override fun onFailure(call: Call<Quiz?>, t: Throwable) {
                println("Cannot connect")
            }
        })
    }

    fun updateView(question: Int) {
        _question.value = quiz?.questions?.get(question)?.question
        _answers.value = quiz?.questions?.get(question)?.answers
        correctAnswer = quiz?.questions?.get(question)?.correctAnswer ?: 0
    }

    fun nextQuestion() {
        if (currentQuestion != quiz?.questions?.size?.minus(1)) {
            updateView(++currentQuestion)
        }
    }
}
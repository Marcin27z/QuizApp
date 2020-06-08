package com.example.quizapp.ui.solve

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Quiz
import com.example.quizapp.dto.SolutionDto
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.StudentService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class QuizViewModel @Inject constructor(private val commonService: CommonService, private val studentService: StudentService): ViewModel() {

    private var quiz: Quiz? = null

    private var currentQuestion = 0

    var numberOfQuestions = 0

    private val _points = MutableLiveData<Int>().apply {
        value = 0
    }
    val points: LiveData<Int> = _points

    private val _question = MutableLiveData<String>().apply {
        value = ""
    }

    val question: LiveData<String> = _question

    private val _answers = MutableLiveData<List<String>>()
    val answers: LiveData<List<String>> = _answers

    private val _quizStatus = MutableLiveData<QuizStatus>().apply {
        value = QuizStatus.EMPTY
    }
    val quizStatus: LiveData<QuizStatus> = _quizStatus

    var correctAnswer: String? = null

    fun getQuiz(quizName: String) {
        val call = commonService.getQuiz(quizName)
        call.enqueue(object: Callback<Quiz?> {

            override fun onResponse(call: Call<Quiz?>, response: Response<Quiz?>) {
                if (response.isSuccessful) {
                    quiz = response.body()
                    if (quiz == null) {
                        println("No quiz found")
                    } else {
                        if (quiz?.questions?.isNotEmpty() == true) {
                            numberOfQuestions = quiz?.questions?.size ?: 0
                            _quizStatus.value = QuizStatus.IN_PROGRESS
                            updateView(currentQuestion)
                        }
                    }
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
        _answers.value = quiz?.questions?.get(question)?.answers?.shuffled()
        correctAnswer = quiz?.questions?.get(question)?.correctAnswer
    }

    fun nextQuestion() {
        if (currentQuestion != numberOfQuestions - 1) {
            updateView(++currentQuestion)
        } else {
            _quizStatus.value = QuizStatus.FINISHED
            submitResult()
        }
    }

    fun addPoint() {
        _points.value = (_points.value ?: 0) + 1
    }

    private fun submitResult() {
        val call = studentService.submitSolution(SolutionDto().apply {
            quizName = quiz?.name ?: ""
            score = _points.value ?: 0
        })
        call.enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                } else {
                    println("Something went wrong")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Cannot connect")
            }
        })
    }
}
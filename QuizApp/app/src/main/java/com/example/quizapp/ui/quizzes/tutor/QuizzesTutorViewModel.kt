package com.example.quizapp.ui.quizzes.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.QuizService
import com.example.quizapp.retrofit.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizzesTutorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text

    private val _quizzes = MutableLiveData<List<QuizInfo>>()
    val quizzes: LiveData<List<QuizInfo>> = _quizzes

    fun getQuizzes(subjectName: String? = null) {
        val commonService = ServiceGenerator.createService(CommonService::class.java)
        val call: Call<List<QuizInfo>?>? =
            if (subjectName == null) {
                commonService.getQuizzesInfo()
            } else {
                commonService.getQuizzesInfoForSubject(subjectName)
            }
        call?.enqueue(object: Callback<List<QuizInfo>?> {

            override fun onResponse(call: Call<List<QuizInfo>?>, response: Response<List<QuizInfo>?>) {
                if (response.isSuccessful) {
                    _quizzes.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<QuizInfo>?>, t: Throwable) {

            }
        })
    }
}
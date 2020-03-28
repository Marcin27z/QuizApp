package com.example.quizapp.ui.quizzes.list.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.QuizInfoTutor
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizzesTutorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text

    private val _quizzes = MutableLiveData<List<QuizInfoTutor>>()
    val quizzes: LiveData<List<QuizInfoTutor>> = _quizzes

    fun getQuizzes(subjectName: String? = null) {
        val tutorService = ServiceGenerator.createService(TutorService::class.java)
        val call: Call<List<QuizInfoTutor>?> =
            if (subjectName == null) {
                tutorService.getQuizzesInfo()
            } else {
                tutorService.getQuizzesInfoForSubject(subjectName)
            }
        call.enqueue(object: Callback<List<QuizInfoTutor>?> {

            override fun onResponse(call: Call<List<QuizInfoTutor>?>, response: Response<List<QuizInfoTutor>?>) {
                if (response.isSuccessful) {
                    _quizzes.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<QuizInfoTutor>?>, t: Throwable) {

            }
        })
    }
}
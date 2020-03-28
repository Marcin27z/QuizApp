package com.example.quizapp.ui.quizzes.add.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Quiz
import com.example.quizapp.dto.QuizDto
import com.example.quizapp.dto.SubjectInfo
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddQuizTutorViewModel : ViewModel() {

    private val _subjectList = MutableLiveData<List<String>>()
    val subjectList: LiveData<List<String>> = _subjectList

    init {
        getSubjects()
    }

    private fun getSubjects() {
        val commonService = ServiceGenerator.createService(CommonService::class.java)
        val call = commonService.getSubjects()
        call.enqueue(object: Callback<List<SubjectInfo>?> {

            override fun onResponse(call: Call<List<SubjectInfo>?>, response: Response<List<SubjectInfo>?>) {
                if (response.isSuccessful) {
                    _subjectList.value = response.body()?.map { it.name }
                } else {

                }
            }

            override fun onFailure(call: Call<List<SubjectInfo>?>, t: Throwable) {

            }
        })
    }

    fun addQuiz(quiz: QuizDto, subject: String) {
        val tutorService = ServiceGenerator.createService(TutorService::class.java)
        val call = tutorService.addQuizToSubject(quiz, subject)
        call.enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}

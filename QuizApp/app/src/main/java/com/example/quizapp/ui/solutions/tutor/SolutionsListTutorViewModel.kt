package com.example.quizapp.ui.solutions.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import com.example.quizbackend.dto.SolutionInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolutionsListTutorViewModel : ViewModel() {

    private val _solutions = MutableLiveData<List<SolutionInfo>>()
    val solutions: LiveData<List<SolutionInfo>> = _solutions

    fun getSolutions(quizName: String) {
        val tutorService = ServiceGenerator.createService(TutorService::class.java)
        val call = tutorService.getSolutionsForQuiz(quizName)
        call.enqueue(object: Callback<List<SolutionInfo>?> {
            override fun onResponse(call: Call<List<SolutionInfo>?>, response: Response<List<SolutionInfo>?>) {
                if (response.isSuccessful) {
                    _solutions.value = response.body()
                } else {
                }
            }

            override fun onFailure(call: Call<List<SolutionInfo>?>, t: Throwable) {
            }
        })
    }
}

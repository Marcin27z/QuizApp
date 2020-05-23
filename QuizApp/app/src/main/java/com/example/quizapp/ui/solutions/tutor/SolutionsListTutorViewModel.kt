package com.example.quizapp.ui.solutions.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import com.example.quizapp.dto.SolutionInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SolutionsListTutorViewModel @Inject constructor(private val tutorService: TutorService): ViewModel() {

    private val _solutions = MutableLiveData<List<SolutionInfo>>()
    val solutions: LiveData<List<SolutionInfo>> = _solutions

    fun getSolutions(quizName: String) {
        val call = tutorService.getSolutionsForQuiz(quizName)
        call.enqueue(object: Callback<List<SolutionInfo>?> {
            override fun onResponse(call: Call<List<SolutionInfo>?>, response: Response<List<SolutionInfo>?>) {
                if (response.isSuccessful) {
                    _solutions.value = response.body()
                } else {
                    print("Not successful")
                }
            }

            override fun onFailure(call: Call<List<SolutionInfo>?>, t: Throwable) {
                print("Failed")
            }
        })
    }
}

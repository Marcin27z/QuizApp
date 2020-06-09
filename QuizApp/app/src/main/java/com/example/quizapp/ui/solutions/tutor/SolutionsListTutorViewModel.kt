package com.example.quizapp.ui.solutions.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.TutorService
import com.example.quizapp.models.SolutionInfo
import com.example.quizapp.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SolutionsListTutorViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _solutions = MutableLiveData<List<SolutionInfo>>()
    val solutions: LiveData<List<SolutionInfo>> = _solutions

    fun getSolutions(quizName: String) {
        val call = repository.getSolutionsForQuiz(quizName)
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

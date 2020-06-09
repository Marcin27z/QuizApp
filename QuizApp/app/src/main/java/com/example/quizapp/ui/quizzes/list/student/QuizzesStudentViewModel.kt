package com.example.quizapp.ui.quizzes.list.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.models.QuizInfo
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.StudentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class QuizzesStudentViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text

    private val _quizzes = MutableLiveData<List<QuizInfo>>()
    val quizzes: LiveData<List<QuizInfo>> = _quizzes

    fun getQuizzes(subjectName: String? = null) {
        val call: Call<List<QuizInfo>?>? =
        if (subjectName == null) {
            repository.getQuizzesInfo()
        } else {
            repository.getQuizzesInfoForSubject(subjectName)
        }
        call?.enqueue(object: Callback<List<QuizInfo>?> {

            override fun onResponse(call: Call<List<QuizInfo>?>, response: Response<List<QuizInfo>?>) {
                if (response.isSuccessful) {
                    _quizzes.value = response.body()?.filter { it.solutionInfo == null }
                } else {

                }
            }

            override fun onFailure(call: Call<List<QuizInfo>?>, t: Throwable) {

            }
        })
    }
}
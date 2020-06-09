package com.example.quizapp.ui.quizzes.add.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.QuizDto
import com.example.quizapp.models.SubjectInfo
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.TutorService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AddQuizTutorViewModel @Inject constructor(
    private val repository: Repository): ViewModel() {

    private val _subjectList = MutableLiveData<List<String>>()
    val subjectList: LiveData<List<String>> = _subjectList

    private val _addQuizResult = MutableLiveData<AddQuizResult>()
    val addQuizResult: LiveData<AddQuizResult> = _addQuizResult

    init {
        getSubjects()
    }

    private fun getSubjects() {
        val call = repository.getSubjects()
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
        viewModelScope.launch {
            try {
                if (repository.addQuizToSubject(quiz, subject)) {
                    _addQuizResult.value = AddQuizResult(1, null)
                } else {
                    _addQuizResult.value = AddQuizResult(null, 1)
                }
            } catch (e: Exception) {
                _addQuizResult.value = AddQuizResult(null, 1)
            }
        }
    }
}

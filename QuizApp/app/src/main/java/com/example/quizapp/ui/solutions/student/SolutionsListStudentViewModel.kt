package com.example.quizapp.ui.solutions.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.QuizInfo
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.StudentService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SolutionsListStudentViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _quizzes = MutableLiveData<List<QuizInfo>>()
    val quizzes: LiveData<List<QuizInfo>> = _quizzes

    fun getSolvedQuizzes() {
        viewModelScope.launch {
            try {
                repository.fetchSolvedQuizzesInfo()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        repository.getSolvedQuizzesInfoFlow().onEach {
            _quizzes.value = it
        }.launchIn(viewModelScope)

    }
}

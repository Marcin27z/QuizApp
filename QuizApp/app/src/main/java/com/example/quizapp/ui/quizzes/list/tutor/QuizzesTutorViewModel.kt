package com.example.quizapp.ui.quizzes.list.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.quizapp.models.QuizInfoTutor
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.TutorService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class QuizzesTutorViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text

    lateinit var quizzes: LiveData<List<QuizInfoTutor>>

    fun getQuizzes(subjectName: String? = null) {
        quizzes = liveData(Dispatchers.IO) {
            emit(if (subjectName == null) {
                repository.getQuizzesInfoTutor()
            } else {
                repository.getQuizzesInfoTutorForSubject(subjectName)
            })
        }
    }
}
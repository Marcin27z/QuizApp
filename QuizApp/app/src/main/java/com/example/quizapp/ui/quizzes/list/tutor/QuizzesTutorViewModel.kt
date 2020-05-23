package com.example.quizapp.ui.quizzes.list.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.quizapp.dto.QuizInfoTutor
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class QuizzesTutorViewModel @Inject constructor(private val tutorService: TutorService): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text

    lateinit var quizzes: LiveData<List<QuizInfoTutor>>

    fun getQuizzes(subjectName: String? = null) {
        quizzes = liveData(Dispatchers.IO) {
            emit(if (subjectName == null) {
                tutorService.getQuizzesInfo()
            } else {
                tutorService.getQuizzesInfoForSubject(subjectName)
            })
        }
    }
}
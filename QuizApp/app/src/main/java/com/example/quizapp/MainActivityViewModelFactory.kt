package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.ui.quiz.QuizViewModel
import com.example.quizbackend.dto.Role

class MainActivityViewModelFactory(val role: Role): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(role) as T
    }
}
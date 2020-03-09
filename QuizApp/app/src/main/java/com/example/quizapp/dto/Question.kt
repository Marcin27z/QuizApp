package com.example.quizapp.dto

class Question {

  lateinit var question: String

  lateinit var answers: List<String>

  var correctAnswer: Int = 0
}
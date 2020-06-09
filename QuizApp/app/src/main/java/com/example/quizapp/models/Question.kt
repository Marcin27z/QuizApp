package com.example.quizapp.models

class Question {

  var question: String = ""

  var answers: MutableList<String> = arrayListOf("", "", "", "")

  var correctAnswer: String = ""
}
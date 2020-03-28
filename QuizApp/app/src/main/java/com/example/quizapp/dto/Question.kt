package com.example.quizapp.dto

class Question {

  var question: String = ""

  var answers: MutableList<String> = arrayListOf("", "", "", "")

  var correctAnswer: String = ""
}
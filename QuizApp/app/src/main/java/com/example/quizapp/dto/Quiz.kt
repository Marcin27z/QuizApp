package com.example.quizapp.dto

class Quiz {

  var id: Long = 0

  lateinit var name: String

  lateinit var questions: MutableList<Question>
}
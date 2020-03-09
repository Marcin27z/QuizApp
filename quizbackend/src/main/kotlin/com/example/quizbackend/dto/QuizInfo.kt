package com.example.quizbackend.dto

import com.example.quizbackend.entity.Quiz

class QuizInfo(quiz: Quiz) {

  var name: String = quiz.name

  var subject: String = quiz.subject.name

}
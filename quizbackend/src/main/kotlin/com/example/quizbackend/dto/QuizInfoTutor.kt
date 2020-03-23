package com.example.quizbackend.dto

import com.example.quizbackend.entity.Quiz

class QuizInfoTutor(quiz: Quiz) {

  var name: String = quiz.name

  var subject: String = quiz.subject.name

}
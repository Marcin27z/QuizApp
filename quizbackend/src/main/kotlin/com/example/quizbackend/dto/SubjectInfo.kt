package com.example.quizbackend.dto

import com.example.quizbackend.entity.Subject

class SubjectInfo(subject: Subject) {

  var name = subject.name
}
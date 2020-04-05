package com.example.quizbackend.repository

import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.entity.Subject
import org.springframework.data.repository.CrudRepository

interface QuizRepository: CrudRepository<Quiz, Long> {

  fun findAllBySubject(subject: Subject): List<Quiz>

  fun findByName(name: String): Quiz?
}
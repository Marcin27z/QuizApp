package com.example.quizbackend.repository

import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.entity.Solution
import com.example.quizbackend.entity.User
import org.springframework.data.repository.CrudRepository

interface SolutionRepository: CrudRepository<Solution, Long> {

  fun findAllByQuiz(quiz: Quiz): MutableSet<Solution>

  fun findAllByUser(user: User): MutableSet<Solution>
}
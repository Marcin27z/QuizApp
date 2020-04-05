package com.example.quizbackend.repository

import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.entity.Solution
import com.example.quizbackend.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface SolutionRepository: CrudRepository<Solution, Long> {

  fun findAllByQuiz(quiz: Quiz): List<Solution>

  fun findAllByUser(user: User): List<Solution>

  fun findByUserAndQuiz(user: User,  quiz: Quiz): Solution?
}
package com.example.quizbackend.repository

import com.example.quizbackend.entity.Subject
import com.example.quizbackend.entity.User
import org.springframework.data.repository.CrudRepository

interface SubjectRepository: CrudRepository<Subject, Long> {

  fun findAllByUsers(user: User): List<Subject>

  fun findByName(name: String): Subject?
}
package com.example.quizbackend.repository

import com.example.quizbackend.entity.User
import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<User, Long> {

  fun findByUsername(username: String): User?
}
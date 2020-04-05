package com.example.quizbackend.security

import com.example.quizbackend.entity.User
import com.example.quizbackend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class RegisterAndLoginService(private val userRepository: UserRepository) {

  fun registerUser(user: User): Boolean {
    return if (!usernameAlreadyUsed(user.username)) {
      userRepository.save(user)
      true
    } else {
      false
    }
  }

  private fun usernameAlreadyUsed(username: String): Boolean {
    return userRepository.findByUsername(username) != null
  }
}

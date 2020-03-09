package com.example.quizbackend.controller

import com.example.quizbackend.dto.UserInfo
import com.example.quizbackend.security.RegisterAndLoginService
import com.example.quizbackend.entity.User
import com.example.quizbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class RegisterAndLoginController @Autowired
internal constructor(private val service: RegisterAndLoginService) {


  @Autowired
  private lateinit var userRepository: UserRepository

  /***
   * If login and passwd ok then sends user role.
   * @param principal - current user
   * @return User role.
   */
  @GetMapping("/login")
  fun login(principal: Principal): UserInfo? {
    return userRepository.findByUsername(principal.name)?.let {
      UserInfo(it)
    }
  }

  /***
   * Adding new user to database
   * @param user
   * @return
   */
  @PostMapping("/register")
  fun addUser(@RequestBody user: User): ResponseEntity<Boolean> {
    println("Wykonanie rejestracji uzytkownika:$user")
    user.password = BCryptPasswordEncoder().encode(user.password)
    val status = service.registerUser(user)

    return if (status) {
      ResponseEntity(true, HttpStatus.OK)
    } else {
      ResponseEntity(false, HttpStatus.NOT_ACCEPTABLE)
    }
  }
}

package com.example.quizapp.models

class UserInfo {

  lateinit var userName: String
  lateinit var role: Role
}

enum class Role {
  ROLE_STUDENT,
  ROLE_TUTOR
}
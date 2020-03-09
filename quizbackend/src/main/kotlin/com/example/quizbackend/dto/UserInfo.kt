package com.example.quizbackend.dto

import com.example.quizbackend.entity.User

class UserInfo(user: User) {

  var userName = user.username
  var role = user.role
}
package com.example.quizbackend.dto

import com.example.quizbackend.entity.Solution

class SolutionInfo(solution: Solution) {
  var score = solution.score

  var user = solution.user.username

  var name = solution.user.name

  var surname = solution.user.surname
}
package com.example.quizbackend.controller

import com.example.quizbackend.dto.SolutionInfo
import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.service.QuizService
import com.example.quizbackend.service.SolutionService
import com.example.quizbackend.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("tutor")
class TutorController {

  @Autowired
  private lateinit var subjectService: SubjectService

  @Autowired
  private lateinit var quizService: QuizService

  @Autowired
  private lateinit var solutionService: SolutionService

  @GetMapping("/hello")
  fun hello(): String {
    return "hello tutor"
  }

  @PostMapping("/createSubject/{subjectName}")
  fun createSubject(principal: Principal, @PathVariable subjectName: String) {
    subjectService.addSubject(subjectName, principal.name)
  }

  @PostMapping("/addQuiz/{subjectName}")
  fun addQuizToSubject(@RequestBody quiz: Quiz, @PathVariable subjectName: String) {
    quizService.addQuiz(quiz, subjectName)
  }

  @GetMapping("/solutions/{quizName}")
  fun getSolutionsForQuiz(@PathVariable quizName: String): List<SolutionInfo> {
    return solutionService.getSolutionsForQuiz(quizName).map {
      SolutionInfo(it)
    }
  }

}
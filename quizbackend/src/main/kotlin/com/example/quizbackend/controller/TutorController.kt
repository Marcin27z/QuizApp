package com.example.quizbackend.controller

import com.example.quizbackend.dto.QuizInfo
import com.example.quizbackend.dto.QuizInfoTutor
import com.example.quizbackend.dto.SolutionInfo
import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.fcm.FCMService
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

  @Autowired
  private lateinit var fcmService: FCMService

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
    fcmService.sendMessage(subjectName, quiz.name)
  }

  @GetMapping("/solutions/{quizName}")
  fun getSolutionsForQuiz(@PathVariable quizName: String): List<SolutionInfo> {
    return solutionService.getSolutionsForQuiz(quizName).map {
      SolutionInfo(it)
    }
  }

  @GetMapping("/quiz", produces = ["application/json"])
  fun getQuizzesInfo(principal: Principal): List<QuizInfoTutor> {
    return quizService.getAllUsersQuizzes(principal.name).map {
      QuizInfoTutor(it)
    }
  }

  @GetMapping("/quiz/subject/{subjectName}")
  fun getQuizzesInfoForSubject(@PathVariable subjectName: String): List<QuizInfoTutor> {
    return quizService.getQuizzesForSubject(subjectName).map {
      QuizInfoTutor(it)
    }
  }

  @DeleteMapping("/deleteSubject/{subjectName}")
  fun deleteSubject(principal: Principal, @PathVariable subjectName: String) {
    subjectService.deleteSubject(subjectName, principal.name)
  }
}
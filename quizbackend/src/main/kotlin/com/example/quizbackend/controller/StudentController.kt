package com.example.quizbackend.controller

import com.example.quizbackend.dto.QuizInfo
import com.example.quizbackend.dto.SolutionDto
import com.example.quizbackend.dto.SolutionInfo
import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.service.QuizService
import com.example.quizbackend.service.SolutionService
import com.example.quizbackend.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("student")
class StudentController {

  @Autowired
  private lateinit var subjectService: SubjectService

  @Autowired
  private lateinit var quizService: QuizService

  @Autowired
  private lateinit var solutionService: SolutionService

  @GetMapping("/hello")
  fun hello(): String {
    return "hello student"
  }

  @GetMapping("/subscribe/{subjectName}")
  fun subscribeToSubject(principal: Principal, @PathVariable subjectName: String): Boolean {
    return subjectService.subscribeToSubject(subjectName, principal.name)
  }

  @GetMapping("/unsubscribe/{subjectName}")
  fun unsubscribeFromSubject(principal: Principal, @PathVariable subjectName: String) {
    subjectService.unsubscribeFromSubject(subjectName, principal.name)
  }


  @PostMapping("/quiz/solution")
  fun submitSolution(principal: Principal, @RequestBody solutionDto: SolutionDto) {
    solutionService.submitSolution(principal.name, solutionDto)
  }

  @GetMapping("/quiz", produces = ["application/json"])
  fun getQuizzesInfo(principal: Principal): List<QuizInfo> {
    return quizService.getAllUsersQuizzesWithSolution(principal.name).map {
      QuizInfo(it.first, it.second?.let { solution -> SolutionInfo(solution) } )
    }
  }

  @GetMapping("/solved", produces = ["application/json"])
  fun getSolvedQuizzesInfo(principal: Principal): List<QuizInfo> {
    return quizService.getAllUsersQuizzesWithSolution(principal.name).map {
      QuizInfo(it.first, it.second?.let { solution -> SolutionInfo(solution) } )
    }.filter { it.solutionInfo != null }
  }

  @GetMapping("/quiz/subject/{subjectName}")
  fun getQuizzesInfoForSubject(principal: Principal, @PathVariable subjectName: String): List<QuizInfo> {
    return quizService.getQuizzesWithSolutionForSubject(principal.name, subjectName).map {
      QuizInfo(it.first, it.second?.let { solution -> SolutionInfo(solution)} )
    }
  }
}
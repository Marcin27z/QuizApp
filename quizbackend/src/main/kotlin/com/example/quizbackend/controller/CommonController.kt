package com.example.quizbackend.controller

import com.example.quizbackend.dto.QuizInfo
import com.example.quizbackend.dto.SolutionInfo
import com.example.quizbackend.dto.SubjectInfo
import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.fcm.FCMService
import com.example.quizbackend.service.QuizService
import com.example.quizbackend.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("common")
class CommonController {

  @Autowired
  private lateinit var subjectService: SubjectService

  @Autowired
  private lateinit var quizService: QuizService

  @Autowired
  private lateinit var fcmService: FCMService

  @GetMapping ("/subjects")
  fun getUsersSubjects(principal: Principal): List<SubjectInfo> {
    return subjectService.getUsersSubjects(principal.name).map {
      SubjectInfo(it)
    }
  }

  @GetMapping("/quiz/{quizName}")
  fun getQuiz(@PathVariable quizName: String): Quiz? {
    return quizService.getQuizByName(quizName)
  }

  @GetMapping("/quiz/mock/add/{quizName}")
  fun mockNewQuiz(@PathVariable quizName: String) {
    fcmService.sendMessage("Math", quizName)
  }

}
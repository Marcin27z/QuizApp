package com.example.quizbackend.service

import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.repository.QuizRepository
import com.example.quizbackend.repository.SubjectRepository
import com.example.quizbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuizService {

  @Autowired
  private lateinit var subjectRepository: SubjectRepository

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var quizRepository: QuizRepository

  fun addQuiz(quiz: Quiz, subjectName: String) {
    subjectRepository.findByName(subjectName)?.let {
      quiz.questions.forEach { question ->
        question.quiz = quiz
      }
      quiz.subject = it
      quizRepository.save(quiz)
    }
  }

  fun getQuizByName(quizName: String): Quiz? {
    return quizRepository.findByName(quizName)
  }

  fun getQuizzesForSubject(subjectName: String): Set<Quiz> {
    return subjectRepository.findByName(subjectName)?.quizzes ?: emptySet()
  }

  fun getAllUsersQuizzes(userName: String): Set<Quiz> {
    val subjects =  userRepository.findByUsername(userName)?.let {
      subjectRepository.findAllByUsers(it)
    }
    val result = mutableSetOf<Quiz>()
    subjects?.forEach {
      result.addAll(it.quizzes)
    }
    return result
  }
}
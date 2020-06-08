package com.example.quizbackend.service

import com.example.quizbackend.entity.Quiz
import com.example.quizbackend.entity.Solution
import com.example.quizbackend.entity.Subject
import com.example.quizbackend.entity.User
import com.example.quizbackend.repository.QuizRepository
import com.example.quizbackend.repository.SolutionRepository
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

  @Autowired
  private lateinit var solutionRepository: SolutionRepository

  fun addQuiz(quiz: Quiz, subjectName: String): Boolean {
    quizRepository.findByName(quiz.name)?.let {
      return false
    }
    subjectRepository.findByName(subjectName)?.let {
      quiz.questions.forEach { question ->
        question.quiz = quiz
      }
      quiz.subject = it
      quizRepository.save(quiz)
      return true
    }
    return false
  }

  fun getQuizByName(quizName: String): Quiz? {
    return quizRepository.findByName(quizName)
  }

  fun getQuizzesForSubject(subjectName: String): List<Quiz> {
    return subjectRepository.findByName(subjectName)?.quizzes ?: emptyList()
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

  fun getQuizzesWithSolutionForSubject(userName: String, subjectName: String): Set<Pair<Quiz, Solution?>> {
    return userRepository.findByUsername(userName)?.let {
      getQuizzesWithSolutionForSubject(it, subjectRepository.findByName(subjectName))
    } ?: emptySet()
  }

  fun getAllUsersQuizzesWithSolution(userName: String): Set<Pair<Quiz, Solution?>> {
    val user = userRepository.findByUsername(userName)
    val subjects =  user?.let {
      subjectRepository.findAllByUsers(it)
    }
    val result = mutableSetOf<Pair<Quiz, Solution?>>()
    subjects?.forEach { subject ->
      result.addAll(getQuizzesWithSolutionForSubject(user, subject))
    }
    return result
  }

  fun getQuizzesWithSolutionForSubject(user: User, subject: Subject?): Set<Pair<Quiz, Solution?>> {
    val result = mutableSetOf<Pair<Quiz, Solution?>>()
    subject?.quizzes?.forEach { quiz ->
      val quizSolution = solutionRepository.findByUserAndQuiz(user, quiz)
      result.add(Pair(quiz, quizSolution))
    }
    return result
  }
}
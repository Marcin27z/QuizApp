package com.example.quizbackend.service

import com.example.quizbackend.dto.SolutionDto
import com.example.quizbackend.entity.Solution
import com.example.quizbackend.repository.QuizRepository
import com.example.quizbackend.repository.SolutionRepository
import com.example.quizbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SolutionService {

  @Autowired
  private lateinit var solutionRepository: SolutionRepository

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var quizRepository: QuizRepository

  fun submitSolution(userName: String, solutionDto: SolutionDto) {
    userRepository.findByUsername(userName)?.let { user ->
      quizRepository.findByName(solutionDto.quizName)?.let { quiz ->
        val solution = Solution().apply {
          this.user = user
          this.quiz = quiz
          this.score = solutionDto.score
        }
        solutionRepository.save(solution)
      }
    }
  }

  fun getSolutionsForQuiz(quizName: String): Set<Solution> {
    return quizRepository.findByName(quizName)?.let { quiz ->
      solutionRepository.findAllByQuiz(quiz)
    } ?: emptySet()
  }
}
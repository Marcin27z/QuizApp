package com.example.quizapp

import com.example.quizapp.dto.Question
import com.example.quizapp.dto.Quiz
import com.example.quizapp.dto.QuizInfo

object QuizDao {

    fun getQuizzesInfos(): List<QuizInfo> {
        return listOf(
            QuizInfo().apply {
                name = "Basic colors"
                subject = "math"
            }
        )
    }

    fun getQuizById(quizId: Int): Quiz {
        return Quiz().apply {
                name = "Basic colors"
                questions = mutableListOf(
                    Question().apply {
                        question = "What color is sun?"
                        answers = listOf(
                            "Blue",
                            "Yellow",
                            "Red",
                            "Green"
                        )
                        correctAnswer = 1
                    },
                    Question().apply {
                        question = "What color is grass?"
                        answers = listOf(
                            "Blue",
                            "Yellow",
                            "Green",
                            "Red"
                        )
                        correctAnswer = 2
                    }
                )
            }
    }
}
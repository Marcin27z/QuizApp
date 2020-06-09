package com.example.quizapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solutions")
data class QuizInfo (
  @PrimaryKey
  val quizName: String = "",
  val subject: String = "",
  @Embedded
  val solutionInfo: SolutionInfo? = null
)
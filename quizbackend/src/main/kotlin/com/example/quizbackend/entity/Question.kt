package com.example.quizbackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
@Table(name = "questions")
class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  var id: Long? = null

  lateinit var question: String

  @ElementCollection
  lateinit var answers: List<String>

  lateinit var correctAnswer: String

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quiz_id")
  lateinit var quiz: Quiz
}
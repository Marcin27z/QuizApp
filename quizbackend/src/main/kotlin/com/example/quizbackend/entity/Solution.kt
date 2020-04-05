package com.example.quizbackend.entity

import javax.persistence.*

@Entity
class Solution {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long? = null

  var score: Int = 0

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quiz_id")
  lateinit var quiz: Quiz

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  lateinit var user: User

}
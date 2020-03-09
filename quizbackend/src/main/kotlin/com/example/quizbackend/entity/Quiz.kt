package com.example.quizbackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "quizzes")
class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long? = null

  lateinit var name: String

  @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade=[CascadeType.ALL])
  lateinit var questions: MutableSet<Question>

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "subject_id")
  lateinit var subject: Subject

  @JsonIgnore
  @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
  lateinit var solutions: MutableSet<Solution>
}
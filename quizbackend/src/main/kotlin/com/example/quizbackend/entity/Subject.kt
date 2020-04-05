package com.example.quizbackend.entity

import javax.persistence.*

@Entity
@Table(name = "subjects")
class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long? = null

  @Column(unique = true)
  lateinit var name: String

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "users_subjects",
      joinColumns = [JoinColumn(name = "subject_id")],
      inverseJoinColumns = [JoinColumn(name = "user_id")])
  lateinit var users: MutableList<User>

  @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
  lateinit var quizzes: List<Quiz>
}
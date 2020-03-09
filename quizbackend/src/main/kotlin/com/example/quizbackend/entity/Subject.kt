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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_subjects",
      joinColumns = [JoinColumn(name = "subject_id")],
      inverseJoinColumns = [JoinColumn(name = "user_id")])
  lateinit var users: MutableSet<User>

  @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
  lateinit var quizzes: MutableSet<Quiz>
}
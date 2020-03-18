package com.example.quizbackend.entity

import javax.persistence.*


@Entity
@Table(name = "users")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long? = null

  @Column(nullable = false, unique = true)
  lateinit var username: String

  lateinit var password: String

  lateinit var name: String

  lateinit var surname: String

  @Enumerated(EnumType.ORDINAL)
  lateinit var role: Role

  @ManyToMany(mappedBy = "users")
  lateinit var subjects: MutableSet<Subject>

  @OneToMany(mappedBy = "user")
  lateinit var solutions: MutableSet<Solution>

}

enum class Role {
  ROLE_STUDENT,
  ROLE_TUTOR
}
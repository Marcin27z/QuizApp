package com.example.quizbackend.service

import com.example.quizbackend.entity.Subject
import com.example.quizbackend.repository.SubjectRepository
import com.example.quizbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubjectService {

  @Autowired
  private lateinit var subjectRepository: SubjectRepository

  @Autowired
  private lateinit var userRepository: UserRepository

  fun addSubject(subjectName: String, userName: String): Boolean {
    subjectRepository.findByName(subjectName)?.let {
      return false
    }
    userRepository.findByUsername(userName)?.let { user ->
      val subject = Subject().apply {
        name = subjectName
        users = mutableListOf(user)
      }
      subjectRepository.save(subject)
      return true
    }
    return false
  }

  fun subscribeToSubject(subjectName: String, userName: String): Boolean {
    subjectRepository.findByName(subjectName)?.let { subject ->
      userRepository.findByUsername(userName)?.let { user ->
        subject.users.add(user)
      }
      subjectRepository.save(subject)
      return true
    }
    return false
  }

  fun getUsersSubjects(userName: String): List<Subject> {
    return userRepository.findByUsername(userName)?.subjects ?: emptyList()
  }

  fun deleteSubject(subjectName: String, username: String) {
    subjectRepository.findByName(subjectName)?.let { subject ->
      userRepository.findByUsername(username)?.let { user ->
        if (subject.users.contains(user)) {
          subjectRepository.delete(subject)
        }
      }
    }
  }

  fun unsubscribeFromSubject(subjectName: String, userName: String) {
    subjectRepository.findByName(subjectName)?.let { subject ->
      userRepository.findByUsername(userName)?.let { user ->
        subject.users.remove(user)
      }
      subjectRepository.save(subject)
    }
  }
}
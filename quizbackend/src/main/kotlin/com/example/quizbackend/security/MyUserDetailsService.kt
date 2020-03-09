package com.example.quizbackend.security

import com.example.quizbackend.entity.User
import com.example.quizbackend.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class MyUserDetailsService : UserDetailsService {

  @Autowired
  private val userRepository: UserRepository? = null

  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepository!!.findByUsername(username) ?: throw UsernameNotFoundException(username)
    return MyUserDetails(user)
  }
}

class MyUserDetails(private val user: User) : UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return mutableListOf(SimpleGrantedAuthority(user.role.toString()))
  }

  override fun isEnabled(): Boolean {
    return true
  }

  override fun getUsername(): String {
    return user.username
  }

  override fun isCredentialsNonExpired(): Boolean {
    return true
  }

  override fun getPassword(): String {
    return user.password
  }

  override fun isAccountNonExpired(): Boolean {
    return true
  }

  override fun isAccountNonLocked(): Boolean {
    return true
  }
}
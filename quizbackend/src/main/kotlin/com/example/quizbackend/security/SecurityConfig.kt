package com.example.quizbackend.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


/***
 * Configuration of application security
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Autowired
  private lateinit var myUserDetailsService: MyUserDetailsService

  @Bean
  fun encoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Throws(Exception::class)
  override fun configure(auth: AuthenticationManagerBuilder?) {
    auth!!
        .userDetailsService(myUserDetailsService)
  }

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {

    http.headers().frameOptions().sameOrigin()
//    http
//        .cors().and()
//        .csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/").permitAll()
//        .antMatchers("/h2-console/**").permitAll()
//        .antMatchers("/register").permitAll()
//        .antMatchers("/admin/**").hasAuthority("ADMIN")
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .and()
//        .logout()
//        .and()
//        .httpBasic()

    http.authorizeRequests().antMatchers("/").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/student/**").hasRole("STUDENT")
        .antMatchers("/tutor/**").hasRole("TUTOR")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .csrf().disable().formLogin().disable()
  }

  @Throws(Exception::class)
  override fun configure(web: WebSecurity?) {
    web!!
        .ignoring()
        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**")
  }
}

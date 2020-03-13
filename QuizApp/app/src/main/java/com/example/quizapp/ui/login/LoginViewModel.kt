package com.example.quizapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.LoginService
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizbackend.dto.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val loginService = ServiceGenerator.createService(LoginService::class.java, username, password)
        val call: Call<UserInfo?> = loginService.basicLogin()
        call.enqueue(object: Callback<UserInfo?> {

            override fun onResponse(call: Call<UserInfo?>, response: Response<UserInfo?> ) {
                if (response.isSuccessful) {
                    _loginResult.value = LoginResult(1, null)
                } else {
                    _loginResult.value = LoginResult(null, 1)
                }
            }

            override fun onFailure(call: Call<UserInfo?>, t: Throwable) {
                _loginResult.value = LoginResult(null, 1)
            }
        })
    }
}

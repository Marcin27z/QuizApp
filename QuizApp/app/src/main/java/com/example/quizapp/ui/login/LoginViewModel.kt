package com.example.quizapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.QuizInfo
import com.example.quizapp.retrofit.LoginService
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.models.UserInfo
import com.example.quizapp.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val serviceGenerator: ServiceGenerator, private val repository: Repository): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _offlineResults = MutableLiveData<List<QuizInfo>>()
    val offlineResults: LiveData<List<QuizInfo>> = _offlineResults

    fun login(username: String, password: String) {
        val loginService = serviceGenerator.createService(LoginService::class.java, username, password)
        val call: Call<UserInfo?> = loginService.basicLogin()
        call.enqueue(object: Callback<UserInfo?> {

            override fun onResponse(call: Call<UserInfo?>, response: Response<UserInfo?> ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    _loginResult.value = LoginResult(1, null, user?.role)
                } else {
                    _loginResult.value = LoginResult(null, 1)
                }
            }

            override fun onFailure(call: Call<UserInfo?>, t: Throwable) {
                _loginResult.value = LoginResult(null, 1)
            }
        })
    }

    fun getResultsOffline() {
        repository.getSolvedQuizzesInfoFlow().onEach {
            _offlineResults.value = it
        }.launchIn(viewModelScope)
    }
}

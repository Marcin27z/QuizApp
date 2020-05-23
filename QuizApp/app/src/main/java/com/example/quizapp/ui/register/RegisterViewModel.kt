package com.example.quizapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Role
import com.example.quizapp.dto.UserDto
import com.example.quizapp.retrofit.RegisterService
import com.example.quizapp.retrofit.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val registerService: RegisterService): ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(name: String, surname: String, login: String, password: String, role: Role) {
        val call: Call<Boolean?> = registerService.register(UserDto(name, surname, login, password, role))
        call.enqueue(object: Callback<Boolean?> {

            override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user == true) {
                        _registerResult.value = RegisterResult(1, null)
                    } else {
                        _registerResult.value = RegisterResult(null, 1)
                    }
                } else {
                    _registerResult.value = RegisterResult(null, 1)
                }
            }

            override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                _registerResult.value = RegisterResult(null, 1)
            }
        })
    }
}

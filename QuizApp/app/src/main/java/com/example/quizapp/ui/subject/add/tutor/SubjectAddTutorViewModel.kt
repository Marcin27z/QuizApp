package com.example.quizapp.ui.subject.add.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectAddTutorViewModel : ViewModel() {

    private val _addStatus = MutableLiveData<Boolean>()
    val addStatus: LiveData<Boolean> = _addStatus

    fun addSubject(name: String) {
        val tutorService = ServiceGenerator.createService(TutorService::class.java)
        val call = tutorService.createSubject(name)
        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    _addStatus.value = true
                } else {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    fun resetStatus() {
        _addStatus.value = null
    }
}

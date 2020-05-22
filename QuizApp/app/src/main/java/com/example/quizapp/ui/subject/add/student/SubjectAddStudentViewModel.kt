package com.example.quizapp.ui.subject.add.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.StudentService
import com.example.quizapp.retrofit.TutorService
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectAddStudentViewModel : ViewModel() {

    private val _addStatus = MutableLiveData<Boolean>()
    val addStatus: LiveData<Boolean> = _addStatus

    fun addSubject(name: String) {
        val studentService = ServiceGenerator.createService(StudentService::class.java)
        val call = studentService.subscribeToSubject(name)
        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    _addStatus.value = true
                    FirebaseMessaging.getInstance().subscribeToTopic(name)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                            }
                        }
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

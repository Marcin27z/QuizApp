package com.example.quizapp.ui.subject.add.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.StudentService
import com.example.quizapp.retrofit.TutorService
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SubjectAddStudentViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _addStatus = MutableLiveData<Boolean>()
    val addStatus: LiveData<Boolean> = _addStatus

    fun addSubject(name: String) {
        val call = repository.subscribeToSubject(name)
        call.enqueue(object: Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    if (response.body() == true) {
                        _addStatus.value = true
                        FirebaseMessaging.getInstance().subscribeToTopic(name)
                            .addOnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                }
                            }
                    } else {
                        _addStatus.value = false
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
            }
        })
    }

    fun resetStatus() {
        _addStatus.value = null
    }
}

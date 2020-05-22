package com.example.quizapp.ui.subject.list.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.dto.SubjectInfo
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.StudentService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectListStudentViewModel : ViewModel() {


    private val _subjects = MutableLiveData<List<SubjectInfo>>()
    val subjects: LiveData<List<SubjectInfo>> = _subjects

    init {
        getSubjects()
    }

    fun getSubjects() {
        val commonService = ServiceGenerator.createService(CommonService::class.java)
        val call = commonService.getSubjects()
        call.enqueue(object: Callback<List<SubjectInfo>?> {

            override fun onResponse(call: Call<List<SubjectInfo>?>, response: Response<List<SubjectInfo>?>) {
                if (response.isSuccessful) {
                    _subjects.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<SubjectInfo>?>, t: Throwable) {

            }
        })
    }

    fun deleteSubject(subjectName: String) {
        val studentService = ServiceGenerator.createService(StudentService::class.java)
        viewModelScope.launch {
            studentService.unsubscribeFromSubject(subjectName)
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic(subjectName)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                }
            }
    }
}

package com.example.quizapp.ui.home.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.SubjectInfo
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.CommonService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeStudentViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun subscribeToSubjects() {
        val call = repository.getSubjects()
        call.enqueue(object : Callback<List<SubjectInfo>?> {

            override fun onResponse(
                call: Call<List<SubjectInfo>?>,
                response: Response<List<SubjectInfo>?>
            ) {
                if (response.isSuccessful) {
                    val firebaseInstance = FirebaseMessaging.getInstance()
                    response.body()?.forEach {
                        firebaseInstance.subscribeToTopic(it.name)
                            .addOnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                }
                            }
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<SubjectInfo>?>, t: Throwable) {

            }
        })
    }

    fun unsubscribeFromSubjects() {
        val call = repository.getSubjects()
        call.enqueue(object : Callback<List<SubjectInfo>?> {

            override fun onResponse(
                call: Call<List<SubjectInfo>?>,
                response: Response<List<SubjectInfo>?>
            ) {
                if (response.isSuccessful) {
                    val firebaseInstance = FirebaseMessaging.getInstance()
                    response.body()?.forEach {
                        firebaseInstance.unsubscribeFromTopic(it.name)
                            .addOnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                }
                            }
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<SubjectInfo>?>, t: Throwable) {

            }
        })
    }

    fun clearDatabase() {
        viewModelScope.launch {
            repository.clearDatabase()
        }
    }

    fun fetchResults() {
        viewModelScope.launch {
            repository.fetchSolvedQuizzesInfo()
        }
    }
}
package com.example.quizapp.ui.subject.list.student

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.models.SubjectInfo
import com.example.quizapp.repository.Repository
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.StudentService
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SubjectListStudentViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    private val _subjects = MutableLiveData<List<SubjectInfo>>()
    val subjects: LiveData<List<SubjectInfo>> = _subjects

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    init {
        getSubjects()
    }

    fun getSubjects() {
        val call = repository.getSubjects()
        call.enqueue(object: Callback<List<SubjectInfo>?> {

            override fun onResponse(call: Call<List<SubjectInfo>?>, response: Response<List<SubjectInfo>?>) {
                if (response.isSuccessful) {
                    _subjects.value = response.body()
                    if (response.body().isNullOrEmpty()) {
                        _message.value = 0
                            SpannableString("You have no subjects.\nPlease click + to add new subject.").apply {
                            setSpan(StyleSpan(BOLD), 35, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    } else {
                        _message.value = 1
                    }

                } else {
                    _message.value = 2
                }
            }

            override fun onFailure(call: Call<List<SubjectInfo>?>, t: Throwable) {
                _message.value = 2
            }
        })
    }

    fun deleteSubject(subjectName: String) {
        viewModelScope.launch {
            try {
                repository.unsubscribeFromSubject(subjectName)
                getSubjects()
            } catch (e: Exception) {

            }
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic(subjectName)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                }
            }
    }
}

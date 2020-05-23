package com.example.quizapp.ui.subject.list.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.SubjectInfo
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SubjectListTutorViewModel @Inject constructor(private val commonService: CommonService): ViewModel() {

    private val _subjects = MutableLiveData<List<SubjectInfo>>()
    val subjects: LiveData<List<SubjectInfo>> = _subjects

    init {
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
}

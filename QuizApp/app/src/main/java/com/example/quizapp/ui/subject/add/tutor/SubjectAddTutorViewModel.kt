package com.example.quizapp.ui.subject.add.tutor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.retrofit.ServiceGenerator
import com.example.quizapp.retrofit.TutorService
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SubjectAddTutorViewModel @Inject constructor(private val tutorService: TutorService): ViewModel() {

    private val _addStatus = MutableLiveData<Boolean>()
    val addStatus: LiveData<Boolean> = _addStatus

    fun addSubject(name: String) {
        viewModelScope.launch {
            try {
                _addStatus.value = tutorService.createSubject(name)
            } catch (e: Exception) {
                _addStatus.value = false
            }
        }
    }

    fun resetStatus() {
        _addStatus.value = null
    }
}

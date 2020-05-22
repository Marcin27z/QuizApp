package com.example.quizapp.ui.subject.list

interface SubjectListItemListener {

    fun onItemClick(subjectName: String)

    fun onDeleteButtonClick(subjectName: String)
}
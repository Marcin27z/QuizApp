package com.example.quizapp.ui.subject.add.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.quizapp.R
import kotlinx.android.synthetic.main.subject_add_student_fragment.*

class SubjectAddStudentFragment : Fragment() {

    private lateinit var viewModel: SubjectAddStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subject_add_student_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubjectAddStudentViewModel::class.java)
        addButton.setOnClickListener {
            viewModel.addSubject(subjectNameEditText.text.toString())
        }
        viewModel.addStatus.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.resetStatus()
                findNavController().navigate(SubjectAddStudentFragmentDirections.actionSubjectAddStudentFragmentToSubjectListStudentFragment())
            }
        })
    }

}

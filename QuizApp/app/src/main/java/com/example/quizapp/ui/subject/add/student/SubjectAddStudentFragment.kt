package com.example.quizapp.ui.subject.add.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.subject_add_student_fragment.*
import javax.inject.Inject

class SubjectAddStudentFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SubjectAddStudentViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subject_add_student_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addStatus.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.resetStatus()
                closeKeyboard()
                findNavController().navigate(SubjectAddStudentFragmentDirections.actionSubjectAddStudentFragmentToSubjectListStudentFragment())
            } else if (it == false) {
                viewModel.resetStatus()
                Toast.makeText(context, R.string.cannot_add_subject, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addButton.setOnClickListener {
            viewModel.addSubject(subjectNameEditText.text.toString())
        }

        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            closeKeyboard()
            findNavController().popBackStack()
        }
    }

}

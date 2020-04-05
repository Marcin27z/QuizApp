package com.example.quizapp.ui.subject.add.tutor

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import kotlinx.android.synthetic.main.subject_add_tutor_fragment.*

class SubjectAddTutorFragment : Fragment() {

    private lateinit var viewModel: SubjectAddTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subject_add_tutor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubjectAddTutorViewModel::class.java)
        addButton.setOnClickListener {
            viewModel.addSubject(subjectNameEditText.text.toString())
        }
        viewModel.addStatus.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.resetStatus()
                findNavController().navigate(SubjectAddTutorFragmentDirections.actionSubjectAddTutorFragmentToSubjectListTutorFragment())
            }
        })
        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }

}

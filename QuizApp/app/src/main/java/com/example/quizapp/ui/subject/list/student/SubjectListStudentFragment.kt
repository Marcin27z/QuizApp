package com.example.quizapp.ui.subject.list.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.quizapp.R
import com.example.quizapp.ui.subject.list.SubjectListAdapter
import kotlinx.android.synthetic.main.subject_list_student_fragment.*
import kotlinx.android.synthetic.main.subject_list_student_fragment.subjectList

class SubjectListStudentFragment : Fragment() {

    private lateinit var viewModel: SubjectListStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subject_list_student_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSubjectButton.setOnClickListener {
            findNavController().navigate(SubjectListStudentFragmentDirections.actionSubjectListStudentFragmentToSubjectAddStudentFragment())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubjectListStudentViewModel::class.java)
        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            subjectList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter =
                    SubjectListAdapter(it) { subjectName ->
                        findNavController().navigate(SubjectListStudentFragmentDirections.actionSubjectListStudentFragmentToQuizzesStudentFragment(subjectName))
                    }
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
    }

}

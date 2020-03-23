package com.example.quizapp.ui.solutions.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration

import com.example.quizapp.R
import kotlinx.android.synthetic.main.solutions_list_student_fragment.*

class SolutionsListStudentFragment : Fragment() {

    private lateinit var viewModel: SolutionsListStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solutions_list_student_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SolutionsListStudentViewModel::class.java)
        viewModel.getSolvedQuizzes()
        viewModel.quizzes.observe(viewLifecycleOwner, Observer {
            solvedQuizzesRecycler.apply {
                adapter = SolutionsListStudentAdapter(it)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
    }

}

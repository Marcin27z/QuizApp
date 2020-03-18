package com.example.quizapp.ui.solutions.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.quizapp.R
import kotlinx.android.synthetic.main.solutions_list_tutor_fragment.*

class SolutionsListTutorFragment : Fragment() {

    private lateinit var viewModel: SolutionsListTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solutions_list_tutor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SolutionsListTutorViewModel::class.java)
        viewModel.getSolutions(arguments?.getString("quizName")!!)
        viewModel.solutions.observe(viewLifecycleOwner, Observer {
            solutionsList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = SolutionsListTutorAdapter(it)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
    }

}

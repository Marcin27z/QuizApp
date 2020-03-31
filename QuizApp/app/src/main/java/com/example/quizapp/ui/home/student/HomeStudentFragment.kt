package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import kotlinx.android.synthetic.main.fragment_home_student.view.*

class HomeStudentFragment : Fragment() {

    private lateinit var homeStudentViewModel: HomeStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
        homeStudentViewModel =
            ViewModelProvider(this).get(HomeStudentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_student, container, false)
        root.quizzes.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToQuizzesStudentFragment())
        }
        root.subjects.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToSubjectListStudentFragment())
        }
        root.logOut.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToLoginFragment(false))
        }
        root.resultsButton.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToSolutionsListStudentFragment())
        }
        return root
    }
}
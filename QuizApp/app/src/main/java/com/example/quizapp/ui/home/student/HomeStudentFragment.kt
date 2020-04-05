package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.ui.login.LoginFragmentArgs
import kotlinx.android.synthetic.main.fragment_home_student.view.*

class HomeStudentFragment : Fragment() {

    private lateinit var homeStudentViewModel: HomeStudentViewModel

    private val args by navArgs<HomeStudentFragmentArgs>()

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
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToLoginFragment(false, null, null))
        }
        root.resultsButton.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToSolutionsListStudentFragment())
        }

        args.subject?.let {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToQuizzesStudentFragment(it))
        }
        return root
    }
}
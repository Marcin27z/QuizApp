package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home_student.view.*
import javax.inject.Inject

class HomeStudentFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeStudentViewModel> { factory }

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
        val root = inflater.inflate(R.layout.fragment_home_student, container, false)
        root.quizzes.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToQuizzesStudentFragment())
        }
        root.subjects.setOnClickListener {
            findNavController().navigate(HomeStudentFragmentDirections.actionHomeStudentFragmentToSubjectListStudentFragment())
        }
        root.logOut.setOnClickListener {
            viewModel.unsubscribeFromSubjects()
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
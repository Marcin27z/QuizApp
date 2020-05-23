package com.example.quizapp.ui.home.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home_tutor.view.*
import javax.inject.Inject

class HomeTutorFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeTutorViewModel> { factory }

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
            ViewModelProvider(this).get(HomeTutorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_tutor, container, false)
        root.quizzes.setOnClickListener {
                findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToQuizzesTutorFragment())
        }
        root.subjects.setOnClickListener {
                findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToSubjectListTutorFragment())
        }
        root.logOut.setOnClickListener {
            findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToLoginFragment(false, null, null))
        }
        return root
    }
}
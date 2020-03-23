package com.example.quizapp.ui.home.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import kotlinx.android.synthetic.main.fragment_home_tutor.view.*

class HomeTutorFragment : Fragment() {

    private lateinit var homeTutorViewModel: HomeTutorViewModel

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
        homeTutorViewModel =
            ViewModelProvider(this).get(HomeTutorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_tutor, container, false)
        root.quizzes.setOnClickListener {
                findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToQuizzesTutorFragment())
        }
        root.subjects.setOnClickListener {
                findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToSubjectListTutorFragment())
        }
        root.logOut.setOnClickListener {
            findNavController().navigate(HomeTutorFragmentDirections.actionHomeTutorFragmentToLoginFragment())
        }
        return root
    }
}
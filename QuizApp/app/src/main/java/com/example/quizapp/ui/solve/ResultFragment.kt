package com.example.quizapp.ui.solve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment: DaggerFragment() {

    private val args by navArgs<ResultFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvScore.text = "${args.points} / ${args.totalPoints}"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuizzesStudentFragment())
                }
            })

        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = args.quizName
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuizzesStudentFragment())
        }
    }
}
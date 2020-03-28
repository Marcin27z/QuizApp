package com.example.quizapp.ui.quizzes.list.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.ui.quizzes.list.QuizzesRecyclerAdapter
import kotlinx.android.synthetic.main.app_bar_quiz.view.*
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.view.*

class QuizzesTutorFragment : Fragment() {

    private lateinit var quizzesViewModel: QuizzesTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizzesViewModel =
            ViewModelProvider(this).get(QuizzesTutorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quizzes_tutor, container, false)
        root.quizzes_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter =
                QuizzesRecyclerAdapter(it.map {
                    QuizInfo().apply {
                        name = it.name; subject = it.subject
                    }
                }) { quizName ->
                    findNavController().navigate(
                        QuizzesTutorFragmentDirections.actionQuizzesTutorFragmentToSolutionsListTutorFragment(
                            quizName
                        )
                    )
                }
        })
        root.addQuizButton.setOnClickListener {
            findNavController().navigate(QuizzesTutorFragmentDirections.actionQuizzesTutorFragmentToAddQuizTutorFragment())
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizzesViewModel.getQuizzes(arguments?.getString("subjectName"))
    }
}
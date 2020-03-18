package com.example.quizapp.ui.quizzes.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.ui.quizzes.QuizzesRecyclerAdapter
import com.example.quizapp.ui.quizzes.student.QuizzesStudentViewModel
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
            layoutManager = LinearLayoutManager(this.context)
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter =
                QuizzesRecyclerAdapter(it) { quizName ->
                    findNavController().navigate(
                        QuizzesTutorFragmentDirections.actionQuizzesTutorFragmentToSolutionsListTutorFragment(
                            quizName
                        )
                    )
                }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizzesViewModel.getQuizzes(arguments?.getString("subjectName"))
    }
}
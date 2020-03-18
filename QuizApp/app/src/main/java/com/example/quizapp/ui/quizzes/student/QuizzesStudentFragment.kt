package com.example.quizapp.ui.quizzes.student

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.ui.quiz.QuizActivity
import com.example.quizapp.ui.quizzes.QuizzesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_quizzes_student.view.*

class QuizzesStudentFragment : Fragment() {

    private lateinit var quizzesViewModel: QuizzesStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizzesViewModel =
            ViewModelProvider(this).get(QuizzesStudentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quizzes_student, container, false)
        root.quizzes_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter =
                QuizzesRecyclerAdapter(it) { quizName ->
                    val intent = Intent(activity, QuizActivity::class.java)
                    intent.putExtra("quizName", quizName)
                    startActivity(intent)
                }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizzesViewModel.getQuizzes(arguments?.getString("subjectName"))
    }
}
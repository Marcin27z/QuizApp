package com.example.quizapp.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.QuizDao
import com.example.quizapp.R
import kotlinx.android.synthetic.main.fragment_quizzes.view.*

class QuizzesFragment : Fragment() {

    private lateinit var quizzesViewModel: QuizzesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizzesViewModel =
            ViewModelProvider(this).get(QuizzesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quizzes, container, false)
        root.quizzes_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter = QuizzesRecyclerAdapter(it)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizzesViewModel.getQuizzes()
    }
}
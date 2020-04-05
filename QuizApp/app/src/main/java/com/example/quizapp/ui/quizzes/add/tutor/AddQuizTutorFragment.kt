package com.example.quizapp.ui.quizzes.add.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import com.example.quizapp.dto.Question
import com.example.quizapp.dto.QuizDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_quiz_tutor_fragment.*

class AddQuizTutorFragment : Fragment() {

    private lateinit var viewModel: AddQuizTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_quiz_tutor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddQuizTutorViewModel::class.java)
        val quizAdapter = NewQuizAdapter()
        quiz.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = quizAdapter
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        viewModel.subjectList.observe(viewLifecycleOwner, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                context!!,
                R.layout.material_spinner_item,
                it
                )
            subjectDropdown.setAdapter(adapter)
        })

        add_button.setOnClickListener {
            quizAdapter.questions.add(Question())
            quizAdapter.notifyDataSetChanged()
        }

        saveButton.setOnClickListener {
            val quiz = QuizDto().apply {
                name = nameEditText.text.toString()
                questions = quizAdapter.questions
            }
            val subject = subjectDropdown.text.toString()
            viewModel.addQuiz(quiz, subject)
        }

        viewModel.addQuizResult.observe(viewLifecycleOwner, Observer {
            if (it.success != null) {
                findNavController().navigate(AddQuizTutorFragmentDirections.actionAddQuizTutorFragmentToQuizzesTutorFragment())
            } else if (it.error != null) {
                Snackbar.make(view!!, "Cannot add quiz", Snackbar.LENGTH_SHORT).show()
            }
        })

        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }
}

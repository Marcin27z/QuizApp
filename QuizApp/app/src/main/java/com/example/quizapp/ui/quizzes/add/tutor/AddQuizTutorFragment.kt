package com.example.quizapp.ui.quizzes.add.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.example.quizapp.models.Question
import com.example.quizapp.models.QuizDto
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_quiz_tutor_fragment.*
import javax.inject.Inject

class AddQuizTutorFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AddQuizTutorViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_quiz_tutor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

        nameEditText.addTextChangedListener {
            nameLayout.error = null
        }

        saveButton.setOnClickListener {
            if (nameEditText.text.toString().isNotEmpty()) {
                val quiz = QuizDto().apply {
                    name = nameEditText.text.toString()
                    questions = quizAdapter.questions
                }
                val subject = subjectDropdown.text.toString()
                viewModel.addQuiz(quiz, subject)
            } else {
                nameLayout.error = resources.getString(R.string.name_cannot_be_empty)
            }
        }

        viewModel.addQuizResult.observe(viewLifecycleOwner, Observer {
            if (it.success != null) {
                closeKeyboard()
                findNavController().navigate(AddQuizTutorFragmentDirections.actionAddQuizTutorFragmentToQuizzesTutorFragment())
            } else if (it.error != null) {
                Snackbar.make(view!!, resources.getString(R.string.cannot_add_quiz), Snackbar.LENGTH_SHORT).show()
            }
        })

        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            closeKeyboard()
            mainActivity.onBackPressed()
        }
    }
}

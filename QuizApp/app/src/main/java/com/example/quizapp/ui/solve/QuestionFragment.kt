package com.example.quizapp.ui.solve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.ui.popup.PopUp
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.question_fragment.*
import kotlinx.android.synthetic.main.question_fragment.question
import javax.inject.Inject

class QuestionFragment : DaggerFragment(), PopUp.OnTouchListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<QuizViewModel> { factory }

    private lateinit var popUp: PopUp

    private val args by navArgs<QuestionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getQuiz(args.quizName)
        viewModel.answers.observe(viewLifecycleOwner, Observer {
            answer1.text = it[0]
            answer2.text = it[1]
            answer3.text = it[2]
            answer4.text = it[3]
        })
        viewModel.question.observe(viewLifecycleOwner, Observer {
            question.text = it
        })

        val onAnswerClickListener = View.OnClickListener {
            if ((it as Button).text == viewModel.correctAnswer) {
                onCorrectAnswerClick()
            } else {
                onWrongAnswerClick()
            }
        }
        answer1.setOnClickListener(onAnswerClickListener)
        answer2.setOnClickListener(onAnswerClickListener)
        answer3.setOnClickListener(onAnswerClickListener)
        answer4.setOnClickListener(onAnswerClickListener)

        viewModel.quizStatus.observe(viewLifecycleOwner, Observer {
            if (it == QuizStatus.FINISHED) {
                findNavController().navigate(QuestionFragmentDirections.actionQuestionFragmentToResultFragment(args.quizName, viewModel.points.value ?: 0, viewModel.numberOfQuestions))
            }
        })

        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = args.quizName
        toolbar.setNavigationOnClickListener {
            val dialog = ExitDialogFragment(object : DialogResultListener {
                override fun onPositive() {
                    viewModel.submitResult()
                    findNavController().navigate(QuestionFragmentDirections.actionQuestionFragmentToHomeStudentFragment())
                }

                override fun onNegative() {

                }
            })
            activity?.let {
                dialog.show(it.supportFragmentManager, "exit")
            }
        }
    }

    private fun onWrongAnswerClick() {
//        Toast.makeText(this.context, "Wrong Answer", Toast.LENGTH_LONG).show()
        popUp = PopUp(activity!!.layoutInflater.inflate(R.layout.popup_window, null), activity!!)
        popUp.show(this, resources.getString(R.string.wrong))
    }

    private fun onCorrectAnswerClick() {
//        Toast.makeText(this.context, "Correct Answer", Toast.LENGTH_LONG).show()
        popUp = PopUp(activity!!.layoutInflater.inflate(R.layout.popup_window, null), activity!!)
        popUp.show(this, resources.getString(R.string.correct))
        viewModel.addPoint()
    }

    override fun tap() {
        popUp.dismiss()
        viewModel.nextQuestion()
    }

}

package com.example.quizapp.ui.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val factory = QuizViewModelFactory(intent.extras?.getString("quizName") ?: "")
        viewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

        viewModel.quizStatus.observe(this, Observer {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            when (it) {
                QuizStatus.FINISHED -> {
                    fragmentTransaction.replace(R.id.quiz_content, ResultFragment())
                }
                QuizStatus.IN_PROGRESS -> {
                    fragmentTransaction.replace(R.id.quiz_content, QuestionFragment())
                }
                QuizStatus.EMPTY -> {

                }
            }
            fragmentTransaction.commit()
        })
    }

}

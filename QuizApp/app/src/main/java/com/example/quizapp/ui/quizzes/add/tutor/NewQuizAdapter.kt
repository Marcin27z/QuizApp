package com.example.quizapp.ui.quizzes.add.tutor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.Question
import kotlinx.android.synthetic.main.question_item.view.*

class NewQuizAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var questions = mutableListOf(Question())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_item, parent, false) as ConstraintLayout
            return QuestionViewHolder(view).apply {
//                setIsRecyclable(false)
            }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val questionHolder = holder as QuestionViewHolder
        val itemView = questionHolder.itemView
        val currentQuestion = questions[position]
        itemView.questionNumberTextView.text = holder.itemView.resources.getString(R.string.question_number, position + 1)
        itemView.questionEditText.addTextChangedListener {
            currentQuestion.question = it.toString()
        }
        itemView.firstAnswerEditText.addTextChangedListener {
            currentQuestion.answers[0] = it.toString()
            currentQuestion.correctAnswer = it.toString()
        }
        itemView.secondAnswerEditText.addTextChangedListener {
            currentQuestion.answers[1] = it.toString()
        }
        itemView.thirdAnswerEditText.addTextChangedListener {
            currentQuestion.answers[2] = it.toString()
        }
        itemView.fourthAnswerEditText.addTextChangedListener {
            currentQuestion.answers[3] = it.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class QuestionViewHolder(itemView: ConstraintLayout) : RecyclerView.ViewHolder(itemView) {

}
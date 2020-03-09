package com.example.quizapp.ui.quizzes

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.ui.quiz.QuizActivity
import kotlinx.android.synthetic.main.quizzes_recycler_item.view.*

class QuizzesRecyclerAdapter(val quizzes: List<QuizInfo>): RecyclerView.Adapter<QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quizzes_recycler_item, parent, false) as ConstraintLayout
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.itemView.title.text = quizzes[position].name
        holder.itemView.subject.text = quizzes[position].subject
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuizActivity::class.java)
            intent.putExtra("quizName", quizzes[position].name)
            holder.itemView.context.startActivity(intent)
        }


    }
}

class QuizViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
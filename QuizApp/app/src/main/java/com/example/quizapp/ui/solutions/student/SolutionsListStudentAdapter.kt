package com.example.quizapp.ui.solutions.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.models.QuizInfo
import kotlinx.android.synthetic.main.solution_student_recycler_item.view.*

class SolutionsListStudentAdapter(private val solutions: List<QuizInfo>): RecyclerView.Adapter<SolutionsListStudentAdapter.SolutionStudentViewHolder>() {

    class SolutionStudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(solution: QuizInfo) {
            itemView.apply {
                tvTitle.text = solution.quizName
                tvSubject.text = solution.subject
                tvScore.text = "${solution.solutionInfo?.score} / ${solution.solutionInfo?.maxScore}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionStudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.solution_student_recycler_item, parent, false) as ConstraintLayout
        return SolutionStudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return solutions.size
    }

    override fun onBindViewHolder(holder: SolutionStudentViewHolder, position: Int) {
        holder.bind(solutions[position])
    }

}


package com.example.quizapp.ui.solutions.tutor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.SolutionInfo
import kotlinx.android.synthetic.main.solution_tutor_recycler_item.view.*

class SolutionsListTutorAdapter(private val solutionsList: List<SolutionInfo>): RecyclerView.Adapter<SolutionTutorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionTutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.solution_tutor_recycler_item, parent, false) as ConstraintLayout
        return SolutionTutorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return solutionsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SolutionTutorViewHolder, position: Int) {
        val solution = solutionsList[position]
        holder.itemView.user.text = "${solution.name} ${solution.surname}"
        holder.itemView.score.text = "${solution.score} / ${solution.maxScore}"
    }
}

class SolutionTutorViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
package com.example.quizapp.ui.solutions.tutor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.QuizInfo
import com.example.quizapp.dto.SolutionInfo
import kotlinx.android.synthetic.main.solution_tutor_recycler_item.view.*

class SolutionsListTutorAdapter(private val solutionsList: List<SolutionInfo>): RecyclerView.Adapter<SolutionTutorViewHolder>(),
    Filterable {

    private var filteredSolutionsList = solutionsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionTutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.solution_tutor_recycler_item, parent, false) as ConstraintLayout
        return SolutionTutorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredSolutionsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SolutionTutorViewHolder, position: Int) {
        val solution = filteredSolutionsList[position]
        holder.itemView.user.text = "${solution.name} ${solution.surname}"
        holder.itemView.score.text = "${solution.score} / ${solution.maxScore}"
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val queryString = query.toString().toLowerCase()
                if (queryString.isEmpty()) {
                    return FilterResults().apply {
                        values = solutionsList
                    }
                }
                return FilterResults().apply {
                    values = solutionsList.filter { "${it.name} ${it.surname}".toLowerCase().contains(queryString) }
                }
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                filteredSolutionsList = filterResults!!.values as List<SolutionInfo>
                notifyDataSetChanged()
            }

        }
    }
}

class SolutionTutorViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
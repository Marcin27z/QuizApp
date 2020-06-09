package com.example.quizapp.ui.quizzes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.models.QuizInfo
import kotlinx.android.synthetic.main.quizzes_recycler_item.view.*

class QuizzesRecyclerAdapter(
    private val quizzes: List<QuizInfo>,
    private val clickFunction: (String) -> Unit
) : RecyclerView.Adapter<QuizViewHolder>(), Filterable {

    private var filteredQuizzes = quizzes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quizzes_recycler_item, parent, false) as ConstraintLayout
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredQuizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.itemView.title.text = filteredQuizzes[position].quizName
        holder.itemView.subject.text = filteredQuizzes[position].subject
        holder.itemView.setOnClickListener {
            clickFunction(holder.itemView.title.text.toString())
        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val queryString = query.toString().toLowerCase()
                if (queryString.isEmpty()) {
                    return FilterResults().apply {
                        values = quizzes
                    }
                }
                return FilterResults().apply {
                    values = quizzes.filter { it.quizName.toLowerCase().contains(queryString) }
                }
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                filteredQuizzes = filterResults!!.values as List<QuizInfo>
                notifyDataSetChanged()
            }

        }
    }
}

class QuizViewHolder(itemView: ConstraintLayout) : RecyclerView.ViewHolder(itemView) {

}
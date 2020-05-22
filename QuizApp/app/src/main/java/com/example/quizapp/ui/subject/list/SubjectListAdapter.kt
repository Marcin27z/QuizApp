package com.example.quizapp.ui.subject.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.SubjectInfo
import kotlinx.android.synthetic.main.subject_recycler_item.view.*

class SubjectListAdapter(private val subjectList: List<SubjectInfo>, private val itemListener: SubjectListItemListener): RecyclerView.Adapter<SubjectViewHolder>(),
    Filterable {

    private var filteredSubjectList = subjectList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_recycler_item, parent, false) as ConstraintLayout
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredSubjectList.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.itemView.subjectTitle.text = filteredSubjectList[position].name
        holder.itemView.setOnClickListener {
            itemListener.onItemClick(holder.itemView.subjectTitle.text.toString())
        }
        holder.itemView.deleteButton.setOnClickListener {

        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val queryString = query.toString().toLowerCase()
                if (queryString.isEmpty()) {
                    return FilterResults().apply {
                        values = subjectList
                    }
                }
                return FilterResults().apply {
                    values = subjectList.filter { it.name.toLowerCase().contains(queryString) }
                }
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                filteredSubjectList = filterResults!!.values as List<SubjectInfo>
                notifyDataSetChanged()
            }

        }
    }
}

class SubjectViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
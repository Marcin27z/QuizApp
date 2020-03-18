package com.example.quizapp.ui.subject.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.SubjectInfo
import kotlinx.android.synthetic.main.subject_recycler_item.view.*

class SubjectListAdapter(private val subjectList: List<SubjectInfo>, private val clickHandler: (String) -> Unit): RecyclerView.Adapter<SubjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_recycler_item, parent, false) as ConstraintLayout
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.itemView.subjectTitle.text = subjectList[position].name
        holder.itemView.setOnClickListener {
            clickHandler(holder.itemView.subjectTitle.text.toString())
        }
    }
}

class SubjectViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
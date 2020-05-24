package com.example.quizapp.ui.subject.list.student

import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.dto.SubjectInfo
import com.example.quizapp.ui.subject.list.SubjectListItemListener
import kotlinx.android.synthetic.main.subject_recycler_item.view.*
import kotlinx.android.synthetic.main.subject_recycler_item.view.subjectTitle
import kotlinx.android.synthetic.main.subject_recycler_student_item.view.*

class SubjectListStudentAdapter(private val subjectList: List<SubjectInfo>,
                                private val activity: AppCompatActivity,
                                private val itemListener: SubjectListItemListener,
                                private val deleteSubjectsCallback: (List<SubjectInfo>) -> Unit):
    RecyclerView.Adapter<SubjectViewHolder>(), Filterable, ActionMode.Callback {

    private var multiSelect = false

    private val selectedItems = arrayListOf<SubjectInfo>()

    private var filteredSubjectList = subjectList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_recycler_student_item, parent, false) as ConstraintLayout
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredSubjectList.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val currentSubject = filteredSubjectList[position]
        holder.itemView.subjectTitle.text = currentSubject.name
        holder.itemView.setOnClickListener {
            if (multiSelect) {
                selectItem(holder, currentSubject)
            } else {
                itemListener.onItemClick(holder.itemView.subjectTitle.text.toString())
            }
        }
        holder.itemView.setOnLongClickListener {
            if (!multiSelect) {
                multiSelect = true
                notifyDataSetChanged()
                selectItem(holder, currentSubject)
                activity.startSupportActionMode(this@SubjectListStudentAdapter)
                true
            } else {
                false
            }
        }
        holder.itemView.cbSelect.visibility = if (multiSelect) {
            View.VISIBLE
        } else {
            View.GONE
        }
        holder.itemView.cbSelect.isChecked = selectedItems.contains(currentSubject)
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

    private fun selectItem(holder: SubjectViewHolder, subjectInfo: SubjectInfo) {
        // If the "selectedItems" list contains the item, remove it and set it's state to normal
        if (selectedItems.contains(subjectInfo)) {
            selectedItems.remove(subjectInfo)
            holder.itemView.cbSelect.isChecked = false
        } else {
            // Else, add it to the list and add a darker shade over the image, letting the user know that it was selected
            selectedItems.add(subjectInfo)
            holder.itemView.cbSelect.isChecked = true
        }
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_delete) {
            // Delete button is clicked, handle the deletion and finish the multi select process
            deleteSubjectsCallback(selectedItems)
            mode?.finish()
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val inflater: MenuInflater? = mode?.menuInflater
        inflater?.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // finished multi selection
        multiSelect = false
        selectedItems.clear()
        notifyDataSetChanged()
    }
}

class SubjectViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {

}
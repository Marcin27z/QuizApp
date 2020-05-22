package com.example.quizapp.ui.subject.list.tutor

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import com.example.quizapp.ui.subject.list.SubjectListAdapter
import com.example.quizapp.ui.subject.list.SubjectListItemListener
import kotlinx.android.synthetic.main.subject_list_student_fragment.*
import kotlinx.android.synthetic.main.subject_list_student_fragment.subjectList

class SubjectListTutorFragment : Fragment() {

    private lateinit var viewModel: SubjectListTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.subject_list_tutor_fragment, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSubjectButton.setOnClickListener {
            findNavController().navigate(SubjectListTutorFragmentDirections.actionSubjectListTutorFragmentToSubjectAddTutorFragment())
        }
        subjectList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && addSubjectButton.visibility == View.VISIBLE) {
                    addSubjectButton.hide()
                } else if (dy < 0 && addSubjectButton.visibility != View.VISIBLE) {
                    addSubjectButton.show()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubjectListTutorViewModel::class.java)
        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            subjectList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter =
                    SubjectListAdapter(it, object: SubjectListItemListener {
                        override fun onItemClick(subjectName: String) {
                            findNavController().navigate(SubjectListTutorFragmentDirections.actionSubjectListTutorFragmentToQuizzesTutorFragment(subjectName))
                        }

                        override fun onDeleteButtonClick(subjectName: String) {
                            // TODO("Not yet implemented")
                        }
                    })
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (subjectList.adapter as SubjectListAdapter).filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

}

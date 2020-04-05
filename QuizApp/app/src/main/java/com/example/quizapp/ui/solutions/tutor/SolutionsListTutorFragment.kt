package com.example.quizapp.ui.solutions.tutor

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.MainActivity

import com.example.quizapp.R
import com.example.quizapp.ui.quizzes.list.QuizzesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.*
import kotlinx.android.synthetic.main.solutions_list_tutor_fragment.*
import kotlinx.android.synthetic.main.solutions_list_tutor_fragment.toolbar

class SolutionsListTutorFragment : Fragment() {

    private lateinit var viewModel: SolutionsListTutorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.solutions_list_tutor_fragment, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SolutionsListTutorViewModel::class.java)
        viewModel.getSolutions(arguments?.getString("quizName")!!)
        viewModel.solutions.observe(viewLifecycleOwner, Observer {
            solutionsList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = SolutionsListTutorAdapter(it)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
        val mainActivity = activity as MainActivity
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
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
                (solutionsList.adapter as SolutionsListTutorAdapter).filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }
}

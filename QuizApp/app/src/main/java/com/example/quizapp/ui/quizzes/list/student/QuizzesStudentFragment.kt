package com.example.quizapp.ui.quizzes.list.student

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.ui.login.LoginViewModel
import com.example.quizapp.ui.quizzes.list.QuizzesRecyclerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quizzes_student.*
import kotlinx.android.synthetic.main.fragment_quizzes_student.quizzes_list
import kotlinx.android.synthetic.main.fragment_quizzes_student.toolbar
import kotlinx.android.synthetic.main.fragment_quizzes_student.view.*
import kotlinx.android.synthetic.main.fragment_quizzes_student.view.toolbar
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.*
import javax.inject.Inject

class QuizzesStudentFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val quizzesViewModel by viewModels<QuizzesStudentViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(QuizzesStudentFragmentDirections.actionQuizzesStudentFragmentToHomeStudentFragment())
                }
            })

        val root = inflater.inflate(R.layout.fragment_quizzes_student, container, false)
        root.quizzes_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter =
                QuizzesRecyclerAdapter(it) { quizName ->
                    findNavController().navigate(QuizzesStudentFragmentDirections.actionQuizzesStudentFragmentToQuestionFragment(quizName))
                }
        })
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizzesViewModel.getQuizzes(arguments?.getString("subjectName"))
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
                (quizzes_list.adapter as QuizzesRecyclerAdapter).filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = (activity as MainActivity)
        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainActivity.onBackPressed()
        }
    }
}
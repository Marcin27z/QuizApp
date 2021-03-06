package com.example.quizapp.ui.quizzes.list.tutor

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.models.QuizInfo
import com.example.quizapp.ui.quizzes.list.QuizzesRecyclerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.*
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.view.*
import kotlinx.android.synthetic.main.fragment_quizzes_tutor.view.quizzes_list
import javax.inject.Inject


class QuizzesTutorFragment : DaggerFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val quizzesViewModel by viewModels<QuizzesTutorViewModel> { factory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizzesViewModel.getQuizzes(arguments?.getString("subjectName"))
        val root = inflater.inflate(R.layout.fragment_quizzes_tutor, container, false)
        root.quizzes_list.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        quizzesViewModel.quizzes.observe(viewLifecycleOwner, Observer {
            root.quizzes_list.adapter =
                QuizzesRecyclerAdapter(it.map {
                    QuizInfo(it.name, it.subject)
                }) { quizName ->
                    findNavController().navigate(
                        QuizzesTutorFragmentDirections.actionQuizzesTutorFragmentToSolutionsListTutorFragment(
                            quizName
                        )
                    )
                }
        })
        root.addQuizButton.setOnClickListener {
            findNavController().navigate(QuizzesTutorFragmentDirections.actionQuizzesTutorFragmentToAddQuizTutorFragment())
        }

        root.quizzes_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && root.addQuizButton.visibility == View.VISIBLE) {
                    root.addQuizButton.hide()
                } else if (dy < 0 && root.addQuizButton.visibility != View.VISIBLE) {
                    root.addQuizButton.show()
                }
            }
        })
        setHasOptionsMenu(true)
        return root
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
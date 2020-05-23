package com.example.quizapp.di.modules

import com.example.quizapp.ui.home.student.HomeStudentFragment
import com.example.quizapp.ui.home.tutor.HomeTutorFragment
import com.example.quizapp.ui.login.LoginFragment
import com.example.quizapp.ui.quizzes.add.tutor.AddQuizTutorFragment
import com.example.quizapp.ui.quizzes.add.tutor.AddQuizTutorFragmentDirections
import com.example.quizapp.ui.quizzes.list.student.QuizzesStudentFragment
import com.example.quizapp.ui.quizzes.list.tutor.QuizzesTutorFragment
import com.example.quizapp.ui.register.RegisterFragment
import com.example.quizapp.ui.solutions.student.SolutionsListStudentFragment
import com.example.quizapp.ui.solutions.tutor.SolutionsListTutorFragment
import com.example.quizapp.ui.solve.QuestionFragment
import com.example.quizapp.ui.solve.ResultFragment
import com.example.quizapp.ui.subject.add.student.SubjectAddStudentFragment
import com.example.quizapp.ui.subject.add.tutor.SubjectAddTutorFragment
import com.example.quizapp.ui.subject.list.student.SubjectListStudentFragment
import com.example.quizapp.ui.subject.list.tutor.SubjectListTutorFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun homeStudentFragment(): HomeStudentFragment

    @ContributesAndroidInjector
    internal abstract fun homeTutorFragment(): HomeTutorFragment

    @ContributesAndroidInjector
    internal abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun addQuizTutorFragment(): AddQuizTutorFragment

    @ContributesAndroidInjector
    internal abstract fun quizzesStudentFragment(): QuizzesStudentFragment

    @ContributesAndroidInjector
    internal abstract fun quizzesTutorFragment(): QuizzesTutorFragment

    @ContributesAndroidInjector
    internal abstract fun registerFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun solutionsListStudentFragment(): SolutionsListStudentFragment

    @ContributesAndroidInjector
    internal abstract fun solutionsListTutorFragment(): SolutionsListTutorFragment

    @ContributesAndroidInjector
    internal abstract fun questionFragment(): QuestionFragment

    @ContributesAndroidInjector
    internal abstract fun resultFragment(): ResultFragment

    @ContributesAndroidInjector
    internal abstract fun subjectAddStudentFragment(): SubjectAddStudentFragment

    @ContributesAndroidInjector
    internal abstract fun subjectAddTutorFragment(): SubjectAddTutorFragment

    @ContributesAndroidInjector
    internal abstract fun subjectListStudentFragment(): SubjectListStudentFragment

    @ContributesAndroidInjector
    internal abstract fun subjectListTutorFragment(): SubjectListTutorFragment
}
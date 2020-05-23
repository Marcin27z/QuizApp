package com.example.quizapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.quizapp.ui.home.student.HomeStudentViewModel
import com.example.quizapp.ui.home.tutor.HomeTutorViewModel
import com.example.quizapp.ui.login.LoginViewModel
import com.example.quizapp.ui.quizzes.add.tutor.AddQuizTutorViewModel
import com.example.quizapp.ui.quizzes.list.student.QuizzesStudentViewModel
import com.example.quizapp.ui.quizzes.list.tutor.QuizzesTutorViewModel
import com.example.quizapp.ui.register.RegisterViewModel
import com.example.quizapp.ui.solutions.student.SolutionsListStudentViewModel
import com.example.quizapp.ui.solutions.tutor.SolutionsListTutorViewModel
import com.example.quizapp.ui.solve.QuizViewModel
import com.example.quizapp.ui.subject.add.student.SubjectAddStudentViewModel
import com.example.quizapp.ui.subject.add.tutor.SubjectAddTutorViewModel
import com.example.quizapp.ui.subject.list.student.SubjectListStudentViewModel
import com.example.quizapp.ui.subject.list.tutor.SubjectListTutorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(key = HomeStudentViewModel::class)
    abstract fun bindHomeStudentViewModel(viewModel: HomeStudentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = HomeTutorViewModel::class)
    abstract fun bindHomeTutorViewModel(viewModel: HomeTutorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = AddQuizTutorViewModel::class)
    abstract fun bindAddQuizTutorViewModel(viewModel: AddQuizTutorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = QuizzesStudentViewModel::class)
    abstract fun bindQuizzesStudentViewModel(viewModel: QuizzesStudentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = QuizzesTutorViewModel::class)
    abstract fun bindQuizzesTutorViewModel(viewModel: QuizzesTutorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SolutionsListStudentViewModel::class)
    abstract fun bindSolutionsListStudentViewModel(viewModel: SolutionsListStudentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SolutionsListTutorViewModel::class)
    abstract fun bindSolutionsListTutorViewModel(viewModel: SolutionsListTutorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = QuizViewModel::class)
    abstract fun bindQuizViewModel(viewModel: QuizViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(key = ResultViewModel::class)
//    abstract fun bindLoginViewModel(viewModel: ResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SubjectAddStudentViewModel::class)
    abstract fun bindSubjectAddStudentViewModel(viewModel: SubjectAddStudentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SubjectAddTutorViewModel::class)
    abstract fun bindSubjectAddTutorViewModel(viewModel: SubjectAddTutorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SubjectListStudentViewModel::class)
    abstract fun bindSubjectListStudentViewModel(viewModel: SubjectListStudentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SubjectListTutorViewModel::class)
    abstract fun bindSubjectListTutorViewModel(viewModel: SubjectListTutorViewModel): ViewModel
}
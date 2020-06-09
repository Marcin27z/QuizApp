package com.example.quizapp.di.modules

import com.example.quizapp.repository.Repository
import com.example.quizapp.repository.local.AppDatabase
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.StudentService
import com.example.quizapp.retrofit.TutorService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(commonService: CommonService, studentService: StudentService, tutorService: TutorService, appDatabase: AppDatabase) =
        Repository(commonService, studentService, tutorService, appDatabase.solutionsDao())
}
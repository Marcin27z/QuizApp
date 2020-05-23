package com.example.quizapp.di.modules

import android.content.Context
import com.example.quizapp.QuizApplication
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    fun providesContext(application: QuizApplication): Context =
        application.applicationContext
}
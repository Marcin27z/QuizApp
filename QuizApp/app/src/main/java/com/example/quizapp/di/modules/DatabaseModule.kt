package com.example.quizapp.di.modules

import android.content.Context
import com.example.quizapp.repository.local.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
}
package com.example.quizapp.di.components

import com.example.quizapp.QuizApplication
import com.example.quizapp.di.modules.*
import com.example.quizapp.di.modules.ActivityModule
import com.example.quizapp.di.modules.FragmentModule
import com.example.quizapp.di.modules.ViewModelFactoryModule
import com.example.quizapp.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ContextModule::class,
    ViewModelModule::class,
    ViewModelFactoryModule::class,
    RemoteModule::class
])
interface AppComponent: AndroidInjector<QuizApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<QuizApplication>()
}
package com.example.quizapp.di.modules

import android.content.Context
import com.example.quizapp.retrofit.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun providesServiceGenerator(context: Context) = ServiceGenerator(context)

    @Provides
    fun providesCommonService(serviceGenerator: ServiceGenerator) =
        serviceGenerator.createService(CommonService::class.java)

    @Provides
    fun providesLoginService(serviceGenerator: ServiceGenerator) =
        serviceGenerator.createService(LoginService::class.java)


    @Provides
    fun providesRegisterService(serviceGenerator: ServiceGenerator) =
        serviceGenerator.createService(RegisterService::class.java)


    @Provides
    fun providesStudentService(serviceGenerator: ServiceGenerator) =
        serviceGenerator.createService(StudentService::class.java)


    @Provides
    fun providesTutorService(serviceGenerator: ServiceGenerator) =
        serviceGenerator.createService(TutorService::class.java)

}
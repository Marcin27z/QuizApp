package com.example.quizapp.retrofit

import android.text.TextUtils
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object ServiceGenerator {
    private var mUsername: String? = null
    private var mPassword: String? = null

    const val API_BASE_URL = "http://192.168.1.13:8080"
    private val httpClient = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
    private var retrofit = builder.build()
    fun <S> createService(serviceClass: Class<S>?): S {
        return createService(serviceClass, null, null)
    }

    fun storeCredentials(username: String, password: String) {
        mUsername = username
        mPassword = password
    }

    fun <S> createService(
        serviceClass: Class<S>?, username: String? = mUsername, password: String? = mPassword
    ): S {
        if (!TextUtils.isEmpty(username)
            && !TextUtils.isEmpty(password)
        ) {
            val authToken: String = Credentials.basic(username, password)
            return createService(serviceClass, authToken)
        }
        return createService(serviceClass, null)
    }

    fun <S> createService(
        serviceClass: Class<S>?, authToken: String?
    ): S {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken!!)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }
        return retrofit.create(serviceClass)
    }
}
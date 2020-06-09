package com.example.quizapp.retrofit

import android.content.Context
import android.text.TextUtils
import com.example.quizapp.R
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class ServiceGenerator(context: Context) {
    private var mUsername: String? = null
    private var mPassword: String? = null

    private val API_BASE_URL = "https://192.168.1.11:8443"
    private val httpClient = getUnsafeOkHttpClientBuilder(context)
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
        builder.client(httpClient.build())
        retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}

fun getUnsafeOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
    val cert: InputStream = context.resources.openRawResource(R.raw.root_ca)
    val ca: Certificate
    ca = cert.use { cert ->
        cf.generateCertificate(cert)
    }

    // creating a KeyStore containing our trusted CAs
    val keyStoreType: String = KeyStore.getDefaultType()
    val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
    keyStore.load(null, null)
    keyStore.setCertificateEntry("ca", ca)

    // creating a TrustManager that trusts the CAs in our KeyStore
    val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
    tmf.init(keyStore)

    // creating an SSLSocketFactory that uses our TrustManager
    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(null, tmf.trustManagers, null)

    // Get hold of the default trust manager

    // Get hold of the default trust manager
    var x509Tm: X509TrustManager? = null
    for (tm in tmf.trustManagers) {
        if (tm is X509TrustManager) {
            x509Tm = tm
            break
        }
    }


    val builder = OkHttpClient.Builder()
    builder.sslSocketFactory(sslContext.socketFactory, x509Tm)
    builder.hostnameVerifier { _, _ -> true }

    // creating a RestAdapter using the custom client
    return builder
}

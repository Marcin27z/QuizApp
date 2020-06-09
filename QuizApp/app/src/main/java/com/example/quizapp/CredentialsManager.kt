package com.example.quizapp

import android.R.attr
import android.content.IntentSender.SendIntentException
import android.widget.Toast
import com.example.quizapp.dto.Role
import com.example.quizapp.ui.login.LoginViewModel
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class CredentialsManager(val activity: MainActivity, val loginViewModel: LoginViewModel) {

    private val RC_READ = 1337
    private val RC_SAVE = 1338

    private var mCredentialsClient: CredentialsClient

    init {
        val options = CredentialsOptions.Builder().forceEnableSaveDialog().build()
        mCredentialsClient = Credentials.getClient(activity, options)
    }

    fun requestCredentials() {
        val mCredentialRequest = CredentialRequest.Builder()
            .setPasswordLoginSupported(true)
            .build()
        mCredentialsClient.request(mCredentialRequest).addOnCompleteListener(
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    // See "Handle successful credential requests"
                    onCredentialRetrieved(task.result!!.credential)
                    return@OnCompleteListener
                }

                val e = task.exception
                if (e is ResolvableApiException) {
                    // This is most likely the case where the user has multiple saved
                    // credentials and needs to pick one. This requires showing UI to
                    // resolve the read request.
                    resolveResult(e, RC_READ)
                } else if (e is ApiException) {
                    // The user must create an account or sign in manually.
                    val code = e.statusCode
                    // ...
                }
            })


    }

    private fun resolveResult(rae: ResolvableApiException, requestCode: Int) {
        try {
            rae.startResolutionForResult(activity, requestCode)
        } catch (e: SendIntentException) {
        }
    }


    fun onCredentialRetrieved(credential: Credential?) {
        val accountType = credential!!.accountType
        if (accountType == null) {
            // Sign the user in with information from the Credential.
            signIn(credential.id, credential.password!!)
        }

    }

    fun attemptAutoLogin() {
        requestCredentials()
    }

    fun signIn(login: String, password: String) {
        loginViewModel.login(login, password)
    }

    fun saveCredentials(login: String, password: String) {
        val credential: Credential = Credential.Builder(login)
            .setPassword(password)
            .build()

        mCredentialsClient.save(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                return@addOnCompleteListener
            }
            val e = task.exception
            if (e is ResolvableApiException) {
                // Try to resolve the save request. This will prompt the user if
                // the credential is new.
                try {
                    e.startResolutionForResult(activity, RC_SAVE)
                } catch (exception: SendIntentException) {
                    // Could not resolve the request
                }
            } else {
                // Request has no resolution
            }
        }
    }
}



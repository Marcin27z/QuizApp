package com.example.quizapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.CredentialsManager
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.example.quizapp.dto.Role
import com.example.quizapp.retrofit.ServiceGenerator
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.solution_tutor_recycler_item.*


class LoginFragment : Fragment() {

    private val RC_READ = 1337
    private val RC_SAVE = 1338

    private val args by navArgs<LoginFragmentArgs>()

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    lateinit var mCredentialsManager: CredentialsManager

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_READ) {
            if (resultCode == Activity.RESULT_OK) {
                val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
                mCredentialsManager.onCredentialRetrieved(credential)
            } else {
                Toast.makeText(activity, "Credential Read Failed", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == RC_SAVE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(activity, "Credentials saved", Toast.LENGTH_SHORT).show();
            } else {
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })

        loginViewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        mCredentialsManager = CredentialsManager(activity as MainActivity, loginViewModel)
        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed()
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.role!!)
            }
        })

        login.setOnClickListener {
            closeKeyboard()
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }
        goToRegisterButton.setOnClickListener {
            closeKeyboard()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        if (args.autoLogin) {
            mCredentialsManager.attemptAutoLogin()
        }
    }

    private fun updateUiWithUser(role: Role) {
        ServiceGenerator.storeCredentials(username.text.toString(), password.text.toString())
        if (username.text!!.isNotEmpty()) {
            mCredentialsManager.saveCredentials(username.text.toString(), password.text.toString())
        }
        if (role == Role.ROLE_STUDENT) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment(args.quiz, args.topic))
        } else if (role == Role.ROLE_TUTOR) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeTutorFragment())
        }
    }

    private fun showLoginFailed() {
//        Toast.makeText(activity, "Cannot login", Toast.LENGTH_SHORT).show()
        Snackbar.make(view!!, "Cannot login", Snackbar.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

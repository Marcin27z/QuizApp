package com.example.quizapp.ui.login

import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.example.quizapp.dto.Role
import com.example.quizapp.retrofit.ServiceGenerator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
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
    }

    private fun updateUiWithUser(role: Role) {
        ServiceGenerator.storeCredentials(username.text.toString(), password.text.toString())
        if (role == Role.ROLE_STUDENT) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment())
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

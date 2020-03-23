package com.example.quizapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.example.quizapp.dto.Role
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameEditText.addTextChangedListener {
            if (it.toString() != "") {
                nameLayout.error = null
            }
        }
        surnameEditText.addTextChangedListener {
            if (it.toString() != "") {
                surnameLayout.error = null
            }
        }
        loginEditText.addTextChangedListener {
            if (it.toString() != "") {
                loginLayout.error = null
            }
        }
        passwordEditText.addTextChangedListener {
            if (it.toString() != "") {
                passwordLayout.error = null
            }
            if (passwordRepeatLayout.error == "Passwords don't match") {
                passwordRepeatLayout.error = null
            }
        }
        passwordRepeatEditText.addTextChangedListener {
            if (it.toString() != "") {
                passwordRepeatLayout.error = null
            }
            if (passwordLayout.error == "Passwords don't match") {
                passwordLayout.error = null
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerButton.setOnClickListener {
            if (validateForm())
                closeKeyboard()
                loading.visibility = View.VISIBLE
                viewModel.register(
                    nameEditText.text.toString(),
                    surnameEditText.text.toString(),
                    loginEditText.text.toString(),
                    passwordEditText.text.toString(),
                    if (roleRadioGroup.findViewById<RadioButton>(roleRadioGroup.checkedRadioButtonId).text.toString() == "Student") {
                        Role.ROLE_STUDENT
                    } else
                        Role.ROLE_TUTOR
                )
        }
        viewModel.registerResult.observe(viewLifecycleOwner, Observer {
            loading.visibility = View.GONE
            if (it.success != null) {
                finishRegister()
            }
            if (it.error != null) {
                showRegisterFailed()
            }
        })
    }

    private fun showRegisterFailed() {
//        Toast.makeText(context, "Registration failed", Toast.LENGTH_LONG).show()
        Snackbar.make(view!!, "Registration failed", Snackbar.LENGTH_SHORT).show()
    }

    private fun finishRegister() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun validateForm(): Boolean {
        var error = false
        if (nameEditText.text.isNullOrEmpty()) {
            nameLayout.error = "Please fill name"
            error = true
        }
        if (surnameEditText.text.isNullOrEmpty()) {
            surnameLayout.error = "Please fill surname"
            error = true
        }
        if (loginEditText.text.isNullOrEmpty()) {
            loginLayout.error = "Please fill login"
            error = true
        }
        if (passwordEditText.text.isNullOrEmpty()) {
            passwordLayout.error = "Please fill password"
            error = true
        } else if (passwordEditText.text.toString() != passwordRepeatEditText.text.toString()){
            passwordLayout.error = "Passwords don't match"
            error = true
        }
        if (passwordRepeatEditText.text.isNullOrEmpty()) {
            passwordRepeatLayout.error = "Please fill repeat password"
            error = true
        } else if (passwordEditText.text.toString() != passwordRepeatEditText.text.toString()){
            passwordRepeatLayout.error = "Passwords don't match"
            error = true
        }
        return !error
    }
}

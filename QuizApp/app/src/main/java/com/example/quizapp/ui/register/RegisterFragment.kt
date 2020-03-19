package com.example.quizapp.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener

import com.example.quizapp.R
import com.example.quizapp.dto.Role
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        registerButton.setOnClickListener {
            if (validateForm())
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
        return error
    }
}

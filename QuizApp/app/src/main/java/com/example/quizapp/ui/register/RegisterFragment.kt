package com.example.quizapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.quizapp.R
import com.example.quizapp.closeKeyboard
import com.example.quizapp.models.Role
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.register_fragment.*
import javax.inject.Inject

class RegisterFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<RegisterViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
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
            if (passwordRepeatLayout.error == resources.getString(R.string.passwords_dont_match)) {
                passwordRepeatLayout.error = null
            }
        }
        passwordRepeatEditText.addTextChangedListener {
            if (it.toString() != "") {
                passwordRepeatLayout.error = null
            }
            if (passwordLayout.error == resources.getString(R.string.passwords_dont_match)) {
                passwordLayout.error = null
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerButton.setOnClickListener {
            if (validateForm()) {
                closeKeyboard()
                loading.visibility = View.VISIBLE
                viewModel.register(
                    nameEditText.text.toString(),
                    surnameEditText.text.toString(),
                    loginEditText.text.toString(),
                    passwordEditText.text.toString(),
                    if (roleRadioGroup.findViewById<RadioButton>(roleRadioGroup.checkedRadioButtonId).text.toString() == resources.getString(R.string.student_mode)) {
                        Role.ROLE_STUDENT
                    } else
                        Role.ROLE_TUTOR
                )
            }
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
        Snackbar.make(view!!, resources.getString(R.string.registration_failed), Snackbar.LENGTH_SHORT).show()
    }

    private fun finishRegister() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(false, null, null))
    }

    private fun validateForm(): Boolean {
        var error = false
        if (nameEditText.text.isNullOrEmpty()) {
            nameLayout.error = resources.getString(R.string.please_fill_name)
            error = true
        }
        if (surnameEditText.text.isNullOrEmpty()) {
            surnameLayout.error = resources.getString(R.string.please_fill_surname)
            error = true
        }
        if (loginEditText.text.isNullOrEmpty()) {
            loginLayout.error = resources.getString(R.string.please_fill_login)
            error = true
        }
        if (passwordEditText.text.isNullOrEmpty()) {
            passwordLayout.error = resources.getString(R.string.please_fill_password)
            error = true
        } else if (passwordEditText.text.toString() != passwordRepeatEditText.text.toString()) {
            passwordLayout.error = resources.getString(R.string.passwords_dont_match)
            error = true
        }
        if (passwordRepeatEditText.text.isNullOrEmpty()) {
            passwordRepeatLayout.error = resources.getString(R.string.please_fill_repeat_password)
            error = true
        } else if (passwordEditText.text.toString() != passwordRepeatEditText.text.toString()) {
            passwordRepeatLayout.error = resources.getString(R.string.passwords_dont_match)
            error = true
        }
        return !error
    }
}

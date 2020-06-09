package com.example.quizapp.ui.solve

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.quizapp.R

class ExitDialogFragment(private val listener: DialogResultListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(resources.getString(R.string.are_you_sure))
                .setPositiveButton(resources.getString(R.string.yes)
                ) { _, _ -> listener.onPositive() }
                .setNegativeButton(resources.getString(R.string.no)
                ) { _, _ -> listener.onNegative() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

interface DialogResultListener {
    fun onPositive()

    fun onNegative()
}
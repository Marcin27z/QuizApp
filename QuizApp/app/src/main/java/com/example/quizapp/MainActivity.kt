package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModel> { factory }

    val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent.getStringExtra("topic")?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        intent.getStringExtra("quiz")?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        if (viewModel.once) {
            viewModel.once = false
            navController.setGraph(R.navigation.mobile_navigation, intent.extras)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1337 || requestCode == 1338) {
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment
                ?.onActivityResult(requestCode, resultCode, data)
        }
    }

}

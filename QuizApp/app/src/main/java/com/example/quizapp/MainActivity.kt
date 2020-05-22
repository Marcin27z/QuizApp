package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

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
        navController.setGraph(R.navigation.mobile_navigation, intent.extras)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1337 || requestCode == 1338) {
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment
                ?.onActivityResult(requestCode, resultCode, data)
        }
    }

}

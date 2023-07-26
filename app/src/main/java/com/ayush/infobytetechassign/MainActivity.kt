package com.ayush.infobytetechassign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayush.infobytetechassign.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding: ActivityMainBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnLogIn.setOnClickListener {
            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null) {
            startActivity(Intent(this@MainActivity, FinalActivity::class.java))
            finish()
        }
    }
}
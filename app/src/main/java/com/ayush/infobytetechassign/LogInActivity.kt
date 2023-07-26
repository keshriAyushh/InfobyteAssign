package com.ayush.infobytetechassign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ayush.infobytetechassign.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private var _binding: ActivityLogInBinding? = null

    val binding: ActivityLogInBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private lateinit var email: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.btnSignIn.setOnClickListener {
            if (binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString()
                    .isNotEmpty()
            ) {

                signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )

            } else {
                Toast.makeText(
                    this@LogInActivity,
                    "Empty fields are not allowed!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
            finish()
        }

        binding.btnSignInUsingPhone.setOnClickListener {
            startActivity(Intent(this@LogInActivity, OtpActivity::class.java))
        }

        binding.tvForgotPass.setOnClickListener {
            startActivity(Intent(this@LogInActivity, ForgotPasswordActivity::class.java))
        }
    }

    private fun init() {
        email = binding.etEmail.text.toString()
        pass = binding.etPassword.text.toString()

        auth = FirebaseAuth.getInstance()
    }

    private fun signInWithEmailAndPassword(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent: Intent = Intent(this@LogInActivity, FinalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LogInActivity, "Sign in failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
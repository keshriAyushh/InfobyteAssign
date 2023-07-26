package com.ayush.infobytetechassign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ayush.infobytetechassign.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null

    private val binding: ActivitySignUpBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val TAG1: String = "SignUpActivityAuth"

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var conf_pass: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {

            if (
                binding.etName.text.toString().isNotEmpty() &&
                binding.etEmail.text.toString().isNotEmpty() &&
                binding.etPassword.text.toString().isNotEmpty() &&
                binding.etConfirm.text.toString().isNotEmpty()
            ) {
                if (isPassValid()) {
                    createAccountWithEmailAndPassword(
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                } else {
                    Toast.makeText(this@SignUpActivity, "Passwords don't match!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this@SignUpActivity,
                    "Empty fields are not allowed!",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    private fun createAccountWithEmailAndPassword(name: String, email: String, pass: String) {

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this@SignUpActivity, FinalActivity::class.java))
                } else {
                    Toast.makeText(this@SignUpActivity, "Authentication Failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Log.d(TAG1, it.toString())
                Toast.makeText(this@SignUpActivity, "Failed!", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(this@SignUpActivity, "Authentication Cancelled", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()

        name = binding.etName.text.toString()
        pass = binding.etPassword.text.toString()
        email = binding.etEmail.text.toString()
        conf_pass = binding.etConfirm.text.toString()
    }

    private fun isPassValid() =
        (binding.etPassword.text.toString() == binding.etConfirm.text.toString())
}
package com.ayush.infobytetechassign

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayush.infobytetechassign.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var _binding: ActivityForgotPasswordBinding? = null
    val binding: ActivityForgotPasswordBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()

        auth = FirebaseAuth.getInstance()

        binding.btnSend.setOnClickListener {
            val emailAddress = binding.etEmail.text.toString()
            if(emailAddress.isNotEmpty()) {
                auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("ForgotPassActivity", "Email sent.")
                        }
                        updateUi()
                    }
            } else  {
                Toast.makeText(this@ForgotPasswordActivity, "You must enter an email to continue!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun initUi() {
        binding.cl.visibility = View.VISIBLE
        binding.cl2.visibility = View.GONE
    }
    private fun updateUi() {
        binding.cl.visibility = View.GONE
        binding.cl2.visibility = View.VISIBLE
    }
}
package com.ayush.infobytetechassign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayush.infobytetechassign.databinding.ActivityFinalBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.MainScope

class FinalActivity : AppCompatActivity() {

    private var _binding: ActivityFinalBinding? = null

    private val binding: ActivityFinalBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvUserDetails.text = "Email: ${auth.currentUser?.email}"

        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@FinalActivity, MainActivity::class.java))
            finish()
        }
    }
}
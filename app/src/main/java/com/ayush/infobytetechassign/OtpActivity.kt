package com.ayush.infobytetechassign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ayush.infobytetechassign.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private var _binding: ActivityOtpBinding? = null

    val binding: ActivityOtpBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private var phone: String? = null
    private lateinit var credential: PhoneAuthCredential
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        init()

        binding.btnGenerateOtp.setOnClickListener {

            phone = "+91${binding.etPhoneNumber.text}"

            if (phone!!.isNotEmpty() || phone!!.isNotBlank()) {

                if (phone!!.length == 13) {
                    changeUI()
                    getOtp()
                } else {
                    Toast.makeText(this, "Please enter a valid Phone Number!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnVerifyOtp.setOnClickListener {
            credential = PhoneAuthProvider.getCredential(storedVerificationId, binding.etOtp.text.toString())
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun getOtp() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        auth.useAppLanguage()
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(p0)
        }

        override fun onVerificationFailed(p0: FirebaseException) {

            if (p0 is FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(this@OtpActivity, "Bad Credentials", Toast.LENGTH_SHORT)
                    .show()
            } else if (p0 is FirebaseTooManyRequestsException) {
                Toast.makeText(
                    this@OtpActivity,
                    "Too many requests. Please try again after a while! ",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            changeUI()
            storedVerificationId = p0
            resendToken = p1
        }
    }

    private fun signInWithPhoneAuthCredential(p0: PhoneAuthCredential) {
        auth.signInWithCredential(p0)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Authentication Successful!", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@OtpActivity, FinalActivity::class.java))
                } else {
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid OTP!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }

    private fun initUi() {
        val spStr = SpannableString(binding.text2.text.toString())

        val color = ContextCompat.getColor(this@OtpActivity, R.color.btn2)

        spStr.setSpan(
            ForegroundColorSpan(color),
            20,
            37,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.clGenerateOtp.visibility = View.VISIBLE
        binding.btnGenerateOtp.visibility = View.VISIBLE
        binding.etPhoneNumber.visibility = View.VISIBLE
        binding.animation1.visibility = View.VISIBLE

        binding.clVerifyOtp.visibility = View.GONE
        binding.btnVerifyOtp.visibility = View.GONE
        binding.etOtp.visibility = View.GONE
        binding.animation2.visibility = View.GONE

    }

    private fun changeUI() {
        binding.clGenerateOtp.visibility = View.GONE
        binding.btnGenerateOtp.visibility = View.GONE
        binding.etPhoneNumber.visibility = View.GONE
        binding.animation1.visibility = View.GONE

        binding.clVerifyOtp.visibility = View.VISIBLE
        binding.btnVerifyOtp.visibility = View.VISIBLE
        binding.etOtp.visibility = View.VISIBLE
        binding.animation2.visibility = View.VISIBLE
    }
    private fun init() {
        auth = FirebaseAuth.getInstance()

        phone = binding.etPhoneNumber.text.toString()
    }
}
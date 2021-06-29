package com.steve.firebaselogin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.steve.firebaselogin.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private var binding:ActivitySecondBinding?=null
    lateinit var progressBar:ProgressDialog
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        progressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        binding?.signUp?.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        binding?.forgotPassword?.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        binding?.facebokBtn?.setOnClickListener {
            loginWithFacebook()
        }
        binding?.loginBtn?.setOnClickListener {
            val email = binding?.loginEmail?.text.toString()
            val password = binding?.loginPassword?.text.toString()
            if (email.isEmpty()) {
                binding?.loginEmail?.error = "Enter Email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding?.loginPassword?.error = "Enter Password"
                return@setOnClickListener
            }
            loginUser(email, password)
        }

    }
    private fun loginWithFacebook() {

    }
    private fun loginUser(email: String, password: String) {
        progressBar.setMessage("Please Wait...")
        progressBar.show()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                progressBar.setMessage("Please Wait...")
                progressBar.show()
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Succesfully created user: ${it.result?.user?.uid}")
                progressBar.hide()
                val intent = Intent(this, ThirdActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Main", "Failed created user: ${it.message}")
            }
    }

    }









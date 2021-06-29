package com.steve.firebaselogin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.steve.firebaselogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    lateinit var mAuth: FirebaseAuth
    lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        mAuth= FirebaseAuth.getInstance()
        progress= ProgressDialog(this)

        binding?.login?.setOnClickListener {
            val intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }

        binding?.registerBtn?.setOnClickListener {
            val name=binding?.name?.text.toString().trim()
            val email=binding?.registerEmail?.text.toString().trim()
            val password=binding?.registerPassword?.text.toString()

            if (name.isEmpty()){
                binding?.name?.error="enter name"
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding?.registerEmail?.error="enter your email"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding?.registerPassword?.error="Enter your password"
                return@setOnClickListener
            }
            register(email,password)
        }
    }

    private fun register(email: String, password: String) {
        progress.setMessage("Please wait")
        progress.show()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully created user: ${it.result!!.user!!.uid}")
                val intent=Intent(this,SecondActivity::class.java)
                startActivity(intent)
                progress.hide()

            }
            .addOnFailureListener{
                Log.d("Main","Failed to create a user ${it.message}")
                Toast.makeText(this,"Authentication failed",Toast.LENGTH_LONG).show()
            }
    }
}

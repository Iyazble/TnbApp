package com.example.tnbcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Enable clickable links for GitHub URL
        val githubUrl = findViewById<TextView>(R.id.githubUrl)
        githubUrl.movementMethod = LinkMovementMethod.getInstance()
    }
    }

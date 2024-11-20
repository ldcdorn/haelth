package com.github.ldcdorn.haelth.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.ldcdorn.haelth.MainActivity
import com.github.ldcdorn.haelth.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding verwenden
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button referenzieren
        val startButton: Button = binding.startButton
        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Schließt LoginActivity, sodass der User nicht zurück navigieren kann
        }
    }
}
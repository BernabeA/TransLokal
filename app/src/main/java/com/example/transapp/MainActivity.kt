package com.example.transapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.transapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        binding.buttonPangasinan.setOnClickListener {
            startActivity(Intent(this, TranslateActivity::class.java).putExtra("language", "Pangasinan"))
        }

        binding.buttonIlocano.setOnClickListener {
            startActivity(Intent(this, TranslateActivity::class.java).putExtra("language", "Ilocano"))
        }

        binding.btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}

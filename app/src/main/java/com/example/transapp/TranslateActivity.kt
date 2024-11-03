package com.example.transapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.transapp.databinding.ActivityTranslateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTranslateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val targetLanguage = intent.getStringExtra("language")
        targetLanguage?.let {
            binding.targetLanguageTextView.text = it
        }

        binding.buttonTranslate.setOnClickListener {
            val sourceText = binding.editTextSource.text.toString().trim()
            if (sourceText.isNotEmpty() && targetLanguage != null) {
                // Get the category from the database
                getCategoryForText(sourceText) { category ->
                    translateText(sourceText, targetLanguage, category)
                }
            } else {
                binding.textViewTranslation.text = "Please enter text to translate."
            }
        }

        binding.btnBack1.setOnClickListener {
            onBackPressed()
        }

        binding.btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun getCategoryForText(sourceText: String, callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.getCategory(sourceText)
                Log.d("TranslateActivity", "Response: $response")

                if (response.category != null) {
                    withContext(Dispatchers.Main) {
                        callback(response.category)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(null)
                    }
                }
            } catch (e: Exception) {
                Log.e("TranslateActivity", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@TranslateActivity, "Failed to retrieve category", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }
        }
    }


    private fun translateText(sourceText: String, targetLanguage: String, category: String?) {
        // Create the translation request
        val request = TranslationRequest(
            source_text = sourceText,
            source_language = "Tagalog",
            target_language = targetLanguage,
            category = category
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.translate(request)

                withContext(Dispatchers.Main) {
                    binding.textViewTranslation.text = if (category != null) {
                        "${response.translated_text}  ($category)"
                    } else {
                        response.translated_text
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@TranslateActivity, "Translation failed", Toast.LENGTH_SHORT).show()
                    binding.textViewTranslation.text = "No Translation Available"
                }
            }
        }
    }
}

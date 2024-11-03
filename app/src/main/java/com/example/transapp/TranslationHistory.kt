package com.example.transapp

data class TranslationHistory(
    val source_text: String,
    val translated_text: String,
    val source_language: String,
    val target_language: String
)

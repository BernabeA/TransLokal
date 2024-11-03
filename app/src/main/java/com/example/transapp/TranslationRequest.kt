package com.example.transapp

data class TranslationRequest(
    val source_text: String,
    val source_language: String,
    val target_language: String,
    val category: String?
)

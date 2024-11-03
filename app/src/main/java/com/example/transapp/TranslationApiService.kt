package com.example.transapp

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TranslationApiService {
    @POST("translate")
    suspend fun translate(@Body request: TranslationRequest): TranslationResponse

    @GET("history")
    suspend fun getHistory(): List<TranslationHistory>

    @GET("get-category/{word}")
    suspend fun getCategory(@Path("word") word: String): CategoryResponse
}

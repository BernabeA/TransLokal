package com.example.transapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack2.setOnClickListener {
            onBackPressed()
        }

        setupRecyclerView()
        fetchTranslationHistory()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(emptyList())
        binding.recyclerViewHistory.adapter = historyAdapter
    }

    private fun fetchTranslationHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val historyList = ApiClient.apiService.getHistory()
                withContext(Dispatchers.Main) {

                    val reversedList = historyList.reversed()
                    historyAdapter.updateData(reversedList)
                }
            } catch (e: Exception) {
                Log.e("HistoryActivity", "Error fetching history: ${e.message}")
                withContext(Dispatchers.Main) {
                }
            }
        }
    }
}

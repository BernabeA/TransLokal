package com.example.transapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transapp.databinding.ItemHistoryBinding

class HistoryAdapter(private var historyList: List<TranslationHistory>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.binding.textViewSource.text = "From: ${historyItem.source_text} (${historyItem.source_language})"
        holder.binding.textViewTranslated.text = "To: ${historyItem.translated_text} (${historyItem.target_language})"
    }


    override fun getItemCount() = historyList.size

    fun updateData(newHistoryList: List<TranslationHistory>) {
        historyList = newHistoryList
        notifyDataSetChanged()
    }
}

package com.example.mobileapplicationtermp.History

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapplicationtermp.Data.AppDatabase

class HistoryViewModelF(
    private val historyContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            val dbInstance = AppDatabase.getInstance(historyContext)
            return HistoryViewModel(dbInstance) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

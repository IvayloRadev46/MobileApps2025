package com.example.mobileapplicationtermp.Calculator

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapplicationtermp.Data.AppDatabase

class CalculatorViewModelF(
    private val providerContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            val dbConnection = AppDatabase.getInstance(providerContext)
            return CalculatorViewModel(dbConnection) as T
        }

        throw IllegalArgumentException("Unsupported ViewModel type")
    }
}

package com.example.mobileapplicationtermp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mobileapplicationtermp.Calculator.CalculatorScreen
import com.example.mobileapplicationtermp.Calculator.CalculatorViewModel
import com.example.mobileapplicationtermp.Calculator.CalculatorViewModelF
import com.example.mobileapplicationtermp.ui.theme.MobileApplicationTermPTheme
import android.content.Intent
import com.example.mobileapplicationtermp.History.HistoryActivity
class MainActivity : ComponentActivity() {

    private val calculatorVM: CalculatorViewModel by viewModels {
        CalculatorViewModelF(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobileApplicationTermPTheme {
                CalculatorScreen(
                    viewModel = calculatorVM,
                    onHistoryClick = { openHistoryActivity() },
                    onShare = { shareText ->
                        shareResultExternally(shareText)
                    }
                )
            }
        }
    }

    private fun openHistoryActivity() {
        val historyIntent = Intent(this, HistoryActivity::class.java)
        startActivity(historyIntent)
    }

    private fun shareResultExternally(textToShare: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
        startActivity(Intent.createChooser(shareIntent, "Сподели BMI резултат"))
    }
}


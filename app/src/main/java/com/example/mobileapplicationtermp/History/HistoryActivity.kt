package com.example.mobileapplicationtermp.History

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.createChooser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mobileapplicationtermp.ui.theme.MobileApplicationTermPTheme


class HistoryActivity : ComponentActivity() {

    private val historyVm: HistoryViewModel by viewModels {
        HistoryViewModelF(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobileApplicationTermPTheme {
                HistoryScreen(
                    viewModel = historyVm,
                    onBack = { finish() },
                    onShare = { shareText ->
                        val intent = Intent(ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(EXTRA_TEXT, shareText)
                        }
                        startActivity(createChooser(intent, "Сподели чрез"))
                    }
                )
            }
        }
    }
}

package com.rahman.aplikasihitungkata.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.rahman.aplikasihitungkata.navigation.SetupNavGraph
import com.rahman.aplikasihitungkata.ui.theme.model.AppTheme
import com.rahman.aplikasihitungkata.ui.theme.theme.Typography
import com.rahman.aplikasihitungkata.ui.theme.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dataStore = SettingsDataStore(this)
        setContent {

            val selectedTheme = dataStore.themeFlow.collectAsState(initial = AppTheme.SYSTEM).value

            Aplikasihitungkata2Theme(appTheme = selectedTheme) {
                SetupNavGraph()
            }
        }
    }

    @Composable
    fun Aplikasihitungkata2Theme(
        appTheme: AppTheme = AppTheme.SYSTEM,
        content: @Composable () -> Unit
    ) {

        val colorScheme = when (appTheme) {
            AppTheme.DARK -> darkColorScheme()
            AppTheme.LIGHT -> lightColorScheme()
            AppTheme.BLUE -> lightColorScheme(
                primary = Color(0xFF2196F3),
                onPrimary = Color.White,
                background = Color(0xFFE3F2FD),
                onBackground = Color.Black
            )

            AppTheme.GREEN -> lightColorScheme(
                primary = Color(0xFF4CAF50),
                onPrimary = Color.White,
                background = Color(0xFFE8F5E9),
                onBackground = Color.Black
            )

            AppTheme.PURPLE -> lightColorScheme( // custom ungu
                primary = Color(0xFF9C27B0),
                onPrimary = Color.White,
                background = Color(0xFFF3E5F5),
                onBackground = Color.Black
            )


            AppTheme.SYSTEM -> if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}


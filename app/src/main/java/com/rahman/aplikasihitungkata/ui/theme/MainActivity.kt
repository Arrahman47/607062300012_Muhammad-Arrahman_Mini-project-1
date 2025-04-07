package com.rahman.aplikasihitungkata.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rahman.aplikasihitungkata.navigation.SetupNavGraph
import com.rahman.aplikasihitungkata.ui.theme.theme.Aplikasihitungkata2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aplikasihitungkata2Theme {
                SetupNavGraph()
            }
        }
    }
}



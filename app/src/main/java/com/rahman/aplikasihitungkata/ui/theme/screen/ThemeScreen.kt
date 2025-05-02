package com.rahman.aplikasihitungkata.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rahman.aplikasihitungkata.ui.theme.model.AppTheme
import com.rahman.aplikasihitungkata.ui.theme.theme.Aplikasihitungkata2Theme
import com.rahman.aplikasihitungkata.ui.theme.util.SettingsDataStore
import kotlinx.coroutines.launch

@Composable
fun ThemeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val coroutineScope = rememberCoroutineScope()

    val themes = AppTheme.entries.toTypedArray()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Pilih Tema", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        themes.forEach { theme ->
            Button(
                onClick = {
                    coroutineScope.launch {
                        dataStore.saveTheme(theme)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(theme.displayName)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ThemeScreenPreview() {
    Aplikasihitungkata2Theme {
        ThemeScreen(rememberNavController())
    }
}

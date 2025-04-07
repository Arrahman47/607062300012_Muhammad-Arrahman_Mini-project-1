package com.rahman.aplikasihitungkata.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rahman.aplikasihitungkata.R
import com.rahman.aplikasihitungkata.ui.theme.theme.Aplikasihitungkata2Theme

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController) {

    val inputText = navController.previousBackStackEntry
        ?.savedStateHandle?.get<String>("inputText") ?: ""
    val wordCount = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Int>("wordCount") ?: 0
    val charCount = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Int>("charCount") ?: 0
    val charNoSpaceCount = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Int>("charNoSpaceCount") ?: 0
    val sentenceCount = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Int>("sentenceCount") ?: 0

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.detail_analisis)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = stringResource(R.string.teks_input),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = inputText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }


            Text(
                text = stringResource(R.string.hasil_analisis),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )


            DetailStatistikItem(
                label = stringResource(R.string.total_kata),
                value = wordCount.toString()
            )

            DetailStatistikItem(
                label = stringResource(R.string.total_karakter),
                value = charCount.toString()
            )

            DetailStatistikItem(
                label = stringResource(R.string.total_karakter_no_space),
                value = charNoSpaceCount.toString()
            )

            DetailStatistikItem(
                label = stringResource(R.string.total_kalimat),
                value = sentenceCount.toString()
            )


            if (wordCount > 0) {
                DetailStatistikItem(
                    label = stringResource(R.string.rata_panjang_kata),
                    value = String.format("%.1f", charNoSpaceCount.toFloat() / wordCount)
                )
            }

            if (sentenceCount > 0) {
                DetailStatistikItem(
                    label = stringResource(R.string.kata_per_kalimat),
                    value = String.format("%.1f", wordCount.toFloat() / sentenceCount)
                )
            }


            Button(
                onClick = {
                    shareDetailedAnalysis(
                        context = context,
                        inputText = inputText,
                        wordCount = wordCount,
                        charCount = charCount,
                        charNoSpaceCount = charNoSpaceCount,
                        sentenceCount = sentenceCount,
                        avgWordLength = if (wordCount > 0) charNoSpaceCount.toFloat() / wordCount else 0f,
                        wordsPerSentence = if (sentenceCount > 0) wordCount.toFloat() / sentenceCount else 0f
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan_analisis))
            }
        }
    }
}

@Composable
fun DetailStatistikItem(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun shareDetailedAnalysis(
    context: Context,
    inputText: String,
    wordCount: Int,
    charCount: Int,
    charNoSpaceCount: Int,
    sentenceCount: Int,
    avgWordLength: Float,
    wordsPerSentence: Float
) {
    val shareText = context.getString(
        R.string.share_detail_template,
        inputText,
        wordCount,
        charCount,
        charNoSpaceCount,
        sentenceCount,
        String.format("%.1f", avgWordLength),
        String.format("%.1f", wordsPerSentence)
    )

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        setType("text/plain")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.bagikan_via)))
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Aplikasihitungkata2Theme {
        DetailScreen(rememberNavController())
    }
}
package com.rahman.aplikasihitungkata.ui.theme.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rahman.aplikasihitungkata.R
import com.rahman.aplikasihitungkata.navigation.Screen
import com.rahman.aplikasihitungkata.ui.theme.theme.Aplikasihitungkata2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    var showMenu by rememberSaveable { mutableStateOf(false) }
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {

                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.menu_overflow)
                        )
                    }


                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {

                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.tentang_aplikasi)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                showMenu = false
                                navController.navigate(Screen.About.route)
                            }
                        )


                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (isDarkTheme)
                                        stringResource(R.string.love)
                                    else
                                        stringResource(R.string.face)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (isDarkTheme)
                                        Icons.Outlined.Face
                                    else
                                        Icons.Outlined.FavoriteBorder,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                isDarkTheme = !isDarkTheme
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding), navController)
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, navController: NavHostController) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var inputError by rememberSaveable { mutableStateOf(false) }

    var wordCount by rememberSaveable { mutableIntStateOf(0) }
    var charCount by rememberSaveable { mutableIntStateOf(0) }
    var charNoSpaceCount by rememberSaveable { mutableIntStateOf(0) }
    var sentenceCount by rememberSaveable { mutableIntStateOf(0) }
    var isResultDisplayed by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.gambar_aplikasiku),
            contentDescription = stringResource(id = R.string.app_logo_desc),
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )


        Text(
            text = stringResource(id = R.string.word_counter_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                inputError = false
            },
            label = { Text(text = stringResource(R.string.input_text)) },
            supportingText = { ErrorHint(inputError) },
            isError = inputError,
            minLines = 5,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )



        Button(
            onClick = {
                if (inputText.isBlank()) {
                    inputError = true
                    return@Button
                }

                wordCount = countWords(inputText)
                charCount = inputText.length
                charNoSpaceCount = inputText.replace(" ", "").length
                sentenceCount = countSentences(inputText)

                isResultDisplayed = true
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }


        if (isResultDisplayed) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.hasil_perhitungan),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = stringResource(R.string.word_count, wordCount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = stringResource(R.string.char_count, charCount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = stringResource(R.string.char_no_space_count, charNoSpaceCount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = stringResource(R.string.sentence_count, sentenceCount),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }


            Button(
                onClick = {

                    navController.currentBackStackEntry?.savedStateHandle?.set("inputText", inputText)
                    navController.currentBackStackEntry?.savedStateHandle?.set("wordCount", wordCount)
                    navController.currentBackStackEntry?.savedStateHandle?.set("charCount", charCount)
                    navController.currentBackStackEntry?.savedStateHandle?.set("charNoSpaceCount", charNoSpaceCount)
                    navController.currentBackStackEntry?.savedStateHandle?.set("sentenceCount", sentenceCount)

                    navController.navigate(Screen.About.route)
                },
                modifier = Modifier.padding(top = 16.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.lihat_detail))
            }


            OutlinedButton(
                onClick = {
                    shareTextAnalysis(
                        context = context,
                        inputText = inputText,
                        wordCount = wordCount,
                        charCount = charCount,
                        charNoSpaceCount = charNoSpaceCount,
                        sentenceCount = sentenceCount
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}




private fun countWords(text: String): Int {
    return text.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }.size
}


private fun countSentences(text: String): Int {
    if (text.isBlank()) return 0

    val sentencePattern = Regex("[.!?]+")
    val sentences = text.split(sentencePattern).filter { it.isNotEmpty() }
    return sentences.size
}

@SuppressLint("StringFormatInvalid")
private fun shareTextAnalysis(
    context: Context,
    inputText: String,
    wordCount: Int,
    charCount: Int,
    charNoSpaceCount: Int,
    sentenceCount: Int
) {
    val shareText = context.getString(
        R.string.share_template,
        inputText,
        wordCount,
        charCount,
        charNoSpaceCount,
        sentenceCount
    )

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.bagikan_via)))
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Aplikasihitungkata2Theme {
        MainScreen(rememberNavController())
    }
}
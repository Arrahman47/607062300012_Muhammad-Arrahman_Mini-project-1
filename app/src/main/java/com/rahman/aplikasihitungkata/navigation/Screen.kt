package com.rahman.aplikasihitungkata.navigation


sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("detailScreen")
}

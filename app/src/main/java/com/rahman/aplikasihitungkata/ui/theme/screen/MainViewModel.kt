package com.rahman.aplikasihitungkata.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.aplikasihitungkata.ui.theme.database.KataDao
import com.rahman.aplikasihitungkata.ui.theme.model.Kata
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    dao: KataDao
) : ViewModel() {

    val data: StateFlow<List<Kata>> = dao.getKata()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}

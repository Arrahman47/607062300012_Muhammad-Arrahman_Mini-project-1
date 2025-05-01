package com.rahman.aplikasihitungkata.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.aplikasihitungkata.ui.theme.database.KataDao
import com.rahman.aplikasihitungkata.ui.theme.model.Kata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KataDao) : ViewModel() {

    fun insert(nama: String, genre: String, rilis: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val kata = Kata(
                nama = nama,
                genre = genre,
                rilis = rilis
            )

            viewModelScope.launch(Dispatchers.IO) {
                dao.insert(kata)
            }
        }
    }

    suspend fun getKata(id: Long): Kata? {
        return dao.getKataById(id)
    }
    fun update(id: Long, nama: String, genre: String, rilis: String) {
        val kata = Kata(
            id = id,
            nama = nama,
            genre = genre,
            rilis = rilis
        )
        viewModelScope.launch(Dispatchers.IO){
            dao.update(kata)
        }
    }
    fun delete (id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

}

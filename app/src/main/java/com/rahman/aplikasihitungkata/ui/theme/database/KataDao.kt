package com.rahman.aplikasihitungkata.ui.theme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rahman.aplikasihitungkata.ui.theme.model.Kata
import kotlinx.coroutines.flow.Flow

@Dao
interface KataDao {

    @Insert
    suspend fun insert(kata: Kata)

    @Update
    suspend fun update(kata: Kata)

    @Query("SELECT * FROM kata ORDER BY nama ASC")
    fun getKata(): Flow<List<Kata>>

    @Query("SELECT * FROM kata WHERE id = :id")
    suspend fun getKataById(id: Long): Kata?

    @Query("DELETE FROM kata WHERE id = :id")
    suspend fun deleteById(id: Long)
}

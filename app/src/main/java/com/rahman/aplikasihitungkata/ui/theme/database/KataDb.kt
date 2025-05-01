package com.rahman.aplikasihitungkata.ui.theme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahman.aplikasihitungkata.ui.theme.model.Kata

@Database(entities = [Kata::class], version = 2, exportSchema = false)
abstract class KataDb : RoomDatabase() {

    abstract val dao: KataDao

    companion object {

        @Volatile
        private var INSTANCE: KataDb? = null

        fun getInstance(context: Context): KataDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KataDb::class.java,
                        "kata.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

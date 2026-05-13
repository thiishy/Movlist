package com.thiishy.movlist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Filme::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmeDao(): FilmeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                context.applicationContext, AppDatabase::class.java, "db_filmes"
                            ).fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
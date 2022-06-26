package com.example.flashcards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flashcards.model.Card
import com.example.flashcards.model.Converters
import com.example.flashcards.model.Deck

@Database(entities = [Card::class, Deck::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
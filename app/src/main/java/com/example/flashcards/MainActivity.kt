package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.flashcards.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logdbinfo()
    }

    private fun cleardb() {
        this.lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(applicationContext).deckDao().removeAll()
            AppDatabase.getDatabase(applicationContext).cardDao().removeAll()
        }
    }

    private fun logdbinfo() {
        this.lifecycleScope.launch(Dispatchers.IO) {
            val decksCount = AppDatabase.getDatabase(applicationContext).deckDao().getAllNotLive().size
            val cardsCount = AppDatabase.getDatabase(applicationContext).cardDao().getAllNotLive().size
            Log.i(TAG, "Database has $decksCount decks and $cardsCount cards")
        }
    }
}
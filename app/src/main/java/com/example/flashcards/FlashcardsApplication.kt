package com.example.flashcards

import android.app.Application
import com.example.flashcards.data.AppDatabase

class FlashcardsApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}
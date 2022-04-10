package com.example.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when user taps Learn button */
    fun goLearn(view: View) {
        val intent = Intent(this, LearnActivity::class.java)
        startActivity(intent)
    }

    /** Called when user taps Decks button */
    fun goDecks(view: View) {
        val intent = Intent(this, DecksActivity::class.java)
        startActivity(intent)
    }

    /** Called when user taps New Flashcard button */
    fun goNewFlashcard(view: View) {
        val intent = Intent(this, NewFlashcardActivity::class.java)
        startActivity(intent)
    }
}
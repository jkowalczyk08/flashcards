package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.adapter.DeckItemAdapter
import com.example.flashcards.data.DecksDatasource

class DecksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)

        val allDecksDataset = DecksDatasource.loadAllDecks()

        val recyclerView = findViewById<RecyclerView>(R.id.all_decks_recycler_view)
        recyclerView.adapter = DeckItemAdapter(this, allDecksDataset)
    }
}
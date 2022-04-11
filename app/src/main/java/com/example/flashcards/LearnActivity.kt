package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.adapter.LearnDeckItemAdapter
import com.example.flashcards.data.DecksDatasource

class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        // Initialize active decks data
        val activeDecksDataset = DecksDatasource.loadActiveDecks();

        val recyclerView = findViewById<RecyclerView>(R.id.active_decks_recycler_view)
        recyclerView.adapter = LearnDeckItemAdapter(this, activeDecksDataset);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true)
    }
}
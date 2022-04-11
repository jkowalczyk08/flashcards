package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.adapter.CardItemAdapter
import com.example.flashcards.adapter.DeckItemAdapter
import com.example.flashcards.data.CardsDatasource
import com.example.flashcards.data.DecksDatasource
import com.example.flashcards.data.EXTRA_SELECTED_DECK

class SingleDeckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_deck)

        val cardsDataset = CardsDatasource.getCardsFromDeck(intent.getStringExtra(
            EXTRA_SELECTED_DECK))

        val recyclerView = findViewById<RecyclerView>(R.id.deck_cards)
        recyclerView.adapter = CardItemAdapter(this, cardsDataset)
    }
}
package com.example.flashcards.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.SingleDeckActivity
import com.example.flashcards.data.DeckEntity
import com.example.flashcards.data.EXTRA_SELECTED_DECK
import com.example.flashcards.model.Deck

class DeckEntityItemAdapter(
    private val context: Context,
    private val dataset: List<DeckEntity>
    ) : RecyclerView.Adapter<DeckEntityItemAdapter.DeckEntityItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Deck object.
    class DeckEntityItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val deckButton: Button = view.findViewById(R.id.enter_deck_button)

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckEntityItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.deck_list_item, parent, false)

        return DeckEntityItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DeckEntityItemViewHolder, position: Int) {
        val deck = dataset[position]
        holder.deckButton.text = deck.name
        holder.deckButton.setOnClickListener {
            val intent = Intent(context, SingleDeckActivity::class.java).apply {
                putExtra(EXTRA_SELECTED_DECK, deck.name)
            }
            context.startActivity(intent)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}

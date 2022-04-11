package com.example.flashcards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Card

class CardItemAdapter(
    private val context: Context,
    private val dataset: List<Card>
    ) : RecyclerView.Adapter<CardItemAdapter.CardItemViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just an Affirmation object.
        class CardItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val frontTextView: TextView = view.findViewById(R.id.front_text)
            val backTextView: TextView = view.findViewById(R.id.back_text)
        }

        /**
         * Create new views (invoked by the layout manager)
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
            // create a new view
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_list_item, parent, false)

            return CardItemViewHolder(adapterLayout)
        }

        /**
         * Replace the contents of a view (invoked by the layout manager)
         */

        // TODO: show entire card, with front, back and edit button
        override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
            val card = dataset[position]
            holder.frontTextView.text = card.front
            holder.backTextView.text = card.back
        }

        /**
         * Return the size of your dataset (invoked by the layout manager)
         */
        override fun getItemCount() = dataset.size
}
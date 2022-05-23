package com.example.flashcards.fragments.decks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Deck

class DecksListAdapter : RecyclerView.Adapter<DecksListAdapter.DeckViewHolder>() {

    private var decksList = emptyList<Deck>()

    class DeckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val enterDeckButton = itemView.findViewById<Button>(R.id.enter_deck_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        return DeckViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.deck_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val currentItem = decksList[position]
        holder.enterDeckButton.text = currentItem.deckName

        holder.enterDeckButton.setOnClickListener {
            val action = DecksFragmentDirections.actionDecksFragmentToEditDeckFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return decksList.size
    }

    fun setData(decks: List<Deck>) {
        this.decksList = decks
        notifyDataSetChanged()
    }


}
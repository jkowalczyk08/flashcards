package com.example.flashcards.fragments.bluetooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.fragments.decks.DecksFragmentDirections
import com.example.flashcards.model.Deck

class ShareDeckListAdapter(private val decksToShareFragment: DecksToShareFragment) : RecyclerView.Adapter<ShareDeckListAdapter.ShareDeckViewHolder>() {

    private var decksList = emptyList<Deck>()

    class ShareDeckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val selectDeckButton = itemView.findViewById<Button>(R.id.enter_deck_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareDeckViewHolder {
        return ShareDeckViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.deck_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ShareDeckViewHolder, position: Int) {
        val currentItem = decksList[position]
        holder.selectDeckButton.text = currentItem.deckName

        holder.selectDeckButton.setOnClickListener {
            // TODO: share selected deck
            decksToShareFragment.shareDeck(currentItem)
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
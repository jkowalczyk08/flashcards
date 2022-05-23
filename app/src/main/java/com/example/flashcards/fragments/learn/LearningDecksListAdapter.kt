package com.example.flashcards.fragments.learn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Deck

class LearningDecksListAdapter : RecyclerView.Adapter<LearningDecksListAdapter.LearningDeckViewHolder>() {

    private var decksList = emptyList<Deck>()

    class LearningDeckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val enterDeckButton = itemView.findViewById<Button>(R.id.enter_deck_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningDeckViewHolder {
        return LearningDeckViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.deck_list_item), parent, false))
    }

    override fun onBindViewHolder(holder: LearningDeckViewHolder, position: Int) {
        val currentItem = decksList[position]
        holder.enterDeckButton.text = currentItem.deckName

        holder.enterDeckButton.setOnClickListener {
            val action = DecksToLearnFragmentDirections.actionDecksToLearnFragmentToLearnDeckFragment(currentItem)
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
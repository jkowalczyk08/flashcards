package com.example.flashcards.fragments.decks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Card
import com.example.flashcards.model.Deck

class CardsListAdapter(private val deck: Deck): RecyclerView.Adapter<CardsListAdapter.CardViewHolder>() {

    private var cardsList = emptyList<Card>()

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val front = itemView.findViewById<TextView>(R.id.front_text)
        val back = itemView.findViewById<TextView>(R.id.back_text)
        val editCardButton = itemView.findViewById<ImageButton>(R.id.edit_card_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardsList[position]
        holder.front.text = currentItem.front
        holder.back.text = currentItem.back

        holder.editCardButton.setOnClickListener {
            val action = EditDeckFragmentDirections.actionEditDeckFragmentToEditCardFragment(deck, currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun setData(cards: List<Card>) {
        this.cardsList = cards
        notifyDataSetChanged()
    }
}
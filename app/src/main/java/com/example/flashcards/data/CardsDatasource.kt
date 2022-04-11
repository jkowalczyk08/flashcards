package com.example.flashcards.data

import com.example.flashcards.model.Card
import kotlin.NullPointerException

class CardsDatasource {

    companion object {
        fun getCardsFromDeck(name: String?) : List<Card> {
            if(name == null) {
                throw NullPointerException()
            }
            val result = mutableListOf<Card>()
            for(i in 1..20) {
                result.add(Card("all f$i", "all b$i"))
            }

            return result;
        }
    }
}
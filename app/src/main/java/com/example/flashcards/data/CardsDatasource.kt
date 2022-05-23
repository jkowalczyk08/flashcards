package com.example.flashcards.data

import com.example.flashcards.model.oldCard
import kotlin.NullPointerException

class CardsDatasource {

    companion object {
        fun getCardsFromDeck(name: String?) : List<oldCard> {
            if(name == null) {
                throw NullPointerException()
            }
            val result = mutableListOf<oldCard>()
            for(i in 1..20) {
                result.add(oldCard("all front$i", "all back$i"))
            }

            return result;
        }
    }
}
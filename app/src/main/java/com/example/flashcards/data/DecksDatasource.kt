package com.example.flashcards.data

import com.example.flashcards.model.*

class DecksDatasource {

    companion object {
        //TODO: finish implementation
        fun saveProgress(card: Card, progress: Progress) {
            card.active = progress != Progress.EASY
        }

        fun loadActiveDecks() : List<Deck> {
            // TODO: finish implementation to get truly active decks
            return listOf(
                Deck("deck1"),
                Deck("deck2"),
                Deck("deck3"),
                Deck("deck4"),
                Deck("deck5"),
                Deck("deck6"),
                Deck("deck7"),
                Deck("deck8"),
                Deck("deck9"),
                Deck("deck10"),
                Deck("deck11"),
                Deck("deck12"),
                Deck("deck13"),
                Deck("deck14"),
            )
        }

        fun getDeckForName(name: String) : Deck {
            // TODO: finish implementation
            val deck = Deck(name)
            for(i in 1..5) {
                deck.add(Card("f$i", "b$i"))
            }
            return deck;
        }

        // TODO: finish implementation
        fun getLearningList(name: String?) : Learnable {
            if(name !is String) throw NullPointerException()
            val deck = getDeckForName(name)

            return LearningList(deck)
        }
    }




}
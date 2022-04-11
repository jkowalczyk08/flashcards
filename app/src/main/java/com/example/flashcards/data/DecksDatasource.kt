package com.example.flashcards.data

import com.example.flashcards.model.*

const val EXTRA_SELECTED_DECK = "com.example.flashcards.DECK"

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

        fun loadAllDecks() : List<Deck> {
            // TODO: finish implementation to get truly active decks
            return listOf(
                Deck("all deck1"),
                Deck("all deck2"),
                Deck("all deck3"),
                Deck("all deck4"),
                Deck("all deck5"),
                Deck("all deck6"),
                Deck("all deck7"),
                Deck("all deck8"),
                Deck("all deck9"),
                Deck("all deck10"),
                Deck("all deck11"),
                Deck("all deck12"),
                Deck("all deck13"),
                Deck("all deck14"),
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
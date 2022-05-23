package com.example.flashcards.data

import com.example.flashcards.model.*

const val EXTRA_SELECTED_DECK = "com.example.flashcards.DECK"

class DecksDatasource {

    companion object {
        //TODO: finish implementation
        fun saveProgress(oldCard: oldCard, progress: Progress) {
            oldCard.active = progress != Progress.EASY
        }

        fun loadActiveDecks() : List<oldDeck> {
            // TODO: finish implementation to get truly active decks
            return listOf(
                oldDeck("deck1"),
                oldDeck("deck2"),
                oldDeck("deck3"),
                oldDeck("deck4"),
                oldDeck("deck5"),
                oldDeck("deck6"),
                oldDeck("deck7"),
                oldDeck("deck8"),
                oldDeck("deck9"),
                oldDeck("deck10"),
                oldDeck("deck11"),
                oldDeck("deck12"),
                oldDeck("deck13"),
                oldDeck("deck14"),
            )
        }

        fun loadAllDecks() : List<oldDeck> {
            // TODO: finish implementation to get truly active decks
            return listOf(
                oldDeck("all deck1"),
                oldDeck("all deck2"),
                oldDeck("all deck3"),
                oldDeck("all deck4"),
                oldDeck("all deck5"),
                oldDeck("all deck6"),
                oldDeck("all deck7"),
                oldDeck("all deck8"),
                oldDeck("all deck9"),
                oldDeck("all deck10"),
                oldDeck("all deck11"),
                oldDeck("all deck12"),
                oldDeck("all deck13"),
                oldDeck("all deck14"),
            )
        }

        fun getDeckForName(name: String) : oldDeck {
            // TODO: finish implementation
            val deck = oldDeck(name)
            for(i in 1..5) {
                deck.add(oldCard("front$i", "back$i"))
            }
            return deck;
        }

//        // TODO: finish implementation
//        fun getLearningList(name: String?) : Learnable {
//            if(name !is String) throw NullPointerException()
//            val deck = getDeckForName(name)
//
//            return LearningList(deck)
//        }
    }
}
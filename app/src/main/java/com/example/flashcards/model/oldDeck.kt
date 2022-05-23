package com.example.flashcards.model

data class oldDeck(var name: String) {
    var oldCards : MutableList<oldCard> = mutableListOf()

    constructor(name: String, oldCards: MutableList<oldCard>) : this(name) {
        this.oldCards = oldCards
    }

    fun add(oldCard: oldCard) : oldDeck {
        oldCards.add(oldCard)
        return this
    }
}
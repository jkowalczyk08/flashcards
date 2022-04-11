package com.example.flashcards.model

import java.util.*

data class Deck(var name: String) {
    var cards : MutableList<Card> = mutableListOf()

    constructor(name: String, cards: MutableList<Card>) : this(name) {
        this.cards = cards
    }

    fun add(card: Card) : Deck {
        cards.add(card)
        return this
    }
}
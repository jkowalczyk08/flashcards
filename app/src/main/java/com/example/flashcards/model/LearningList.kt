package com.example.flashcards.model

import com.example.flashcards.data.DecksDatasource

class LearningList() : Learnable {
    var list = mutableListOf<Card>()
    var index = -1
    var activeCount = 0

    constructor(deck: Deck) : this() {
        for(card in deck.cards) {
            if(card.isActive()) {
                list.add(card)
                activeCount++
            }
        }
    }


    override fun hasNext(): Boolean {
        return activeCount > 0
    }

    override fun next(): Card {
        if(!hasNext()) throw IndexOutOfBoundsException()

        do {
            index++
            if(index >= list.size) {
                index = 0
            }
        } while (!list[index].isActive())

        return list[index]
    }

    // TODO: maybe its better to store those cards in separate list, and then update all cards at once?
    override fun saveAnswer(progress: Progress) {
        DecksDatasource.saveProgress(list[index], progress)
        if(progress == Progress.EASY) {
            activeCount--
        }
    }

}
package com.example.flashcards.model

import java.util.*

data class Deck(var name: String) {
    private var cards : MutableList<Card> = mutableListOf()
    private var learningQueue: MutableList<Card>? = null
    private var learningIndex = 0
    private var learningFinished = false;

    constructor(name: String, cards: MutableList<Card>) : this(name) {
        this.cards = cards
    }

    fun add(card: Card) : Deck {
        cards.add(card)
        return this
    }

    fun isFinished() : Boolean {
        return learningFinished
    }

    private fun createLearningQueue() : MutableList<Card> {
        var q = mutableListOf<Card>()
        for(c: Card in cards) {
            if(c.isActive()) {
                q.add(c)
            }
        }
        learningFinished = q.isEmpty()
        learningIndex = 0
        return q
    }

    fun getNextCard(): Card {
        if(learningQueue == null) {
            learningQueue = createLearningQueue()
        }
        if(learningQueue!!.isEmpty()) {
            // TODO: learningActivity should display some nice messages etc
        }
        return learningQueue!![learningIndex]
    }

    fun saveProgress(progress: Progress) {
        learningQueue?.get(learningIndex)?.saveProgress(progress)
        learningIndex++
        if(learningIndex >= learningQueue?.size!!) {
            learningQueue = createLearningQueue();
        }
    }
}
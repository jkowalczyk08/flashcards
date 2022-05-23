package com.example.flashcards.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class LearningList(val scoreCalculator: ScoreCalculator) : Learnable {

    val TAG = "LearningList"

    var list = mutableListOf<LearningCard>()
    var index = -1
    var activeCount = 0

    constructor(activeCards: List<Card>, scoreCalculator: ScoreCalculator) : this(scoreCalculator) {
        for(card in activeCards) {
            list.add(LearningCard(card, false))
        }
        activeCount = activeCards.size
    }


    override fun hasNext(): Boolean {
        return activeCount > 0
    }

    override fun next(): LearningCard {
        if(!hasNext()) throw IndexOutOfBoundsException()

        do {
            index++
            if(index >= list.size) {
                index = 0
            }
        } while (list[index].finished)

        return list[index]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun logAnswer(progress: Progress) {
        val evaluation = scoreCalculator.evaluateAnswer(progress, list[index].card.score)
        if(evaluation.finished) {
            list[index].finished = true
            activeCount--
        }
        list[index].card.updateEvaluation(evaluation)
    }

    override fun getCards() : List<Card> {
        val cards = mutableListOf<Card>()
        for(learningCard in list) {
            cards.add(learningCard.card)
        }
        return cards
    }
}
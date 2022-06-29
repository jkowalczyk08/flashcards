package com.example.flashcards.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

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
        var prevRev = list[index].card.prevRevision
        if(evaluation.finished) {
            list[index].finished = true
            activeCount--
            prevRev = Calendar.getInstance().time
        }
        list[index].card.updateEvaluation(evaluation, prevRev)
    }

    override fun getCards() : List<Card> {
        val cards = mutableListOf<Card>()
        for(learningCard in list) {
            cards.add(learningCard.card)
        }
        return cards
    }
}
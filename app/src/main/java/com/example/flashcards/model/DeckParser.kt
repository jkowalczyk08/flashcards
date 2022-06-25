package com.example.flashcards.model

import android.util.Log

class DeckParser {

    private val TAG = "DeckParser"


    fun deckToShareableString(deckName: String, cards: List<Card>) : String {

        val cardsStringBuilder = StringBuilder()

        // TODO: ensure these symbols do not appear in decks' names or cards' fields
        cardsStringBuilder.append("$deckName$$")
        for(card in cards) {
            cardsStringBuilder.append(card.front + "##" + card.back + "@@")
        }
        cardsStringBuilder.append("^^")

        Log.i(TAG, "string from $deckName, $cards is: $cardsStringBuilder")

        return cardsStringBuilder.toString()
    }

    fun ShareableStringToDeck(string: String) : Pair<String, List<PrimitiveCard>> {

        val stringRepresentation = string.substring(0, string.length - 4)

        val deckRepresentation = stringRepresentation.split("$$")
        val deckName = deckRepresentation[0]

        val cardsRepresentation = deckRepresentation[1].split("@@")
        val cards : MutableList<PrimitiveCard> = mutableListOf()

        for(cardString in cardsRepresentation) {
            val front = cardString.split("##")[0]
            val back = cardString.split("##")[1]
            cards.add(PrimitiveCard(front, back))
        }

        Log.i(TAG, "pair from $string is: deckName=$deckName, cards=$cards")

        return Pair(deckName, cards)
    }
}
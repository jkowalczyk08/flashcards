package com.example.flashcards.model

import android.util.Log

class DeckParser {

    val END_OF_DECK = "^"
    val END_OF_NAME = "$"
    val END_OF_CARD = "@"
    val MIDDLE_OF_CARD = "#"

    private val TAG = "DeckParser"


    fun deckToShareableString(deckName: String, cards: List<Card>) : String {

        val cardsStringBuilder = StringBuilder()

        // TODO: ensure these symbols do not appear in decks' names or cards' fields
        cardsStringBuilder.append("$deckName$END_OF_NAME")
        for(card in cards) {
            cardsStringBuilder.append("${card.front}$MIDDLE_OF_CARD${card.back}$END_OF_CARD")
        }
        cardsStringBuilder.append(END_OF_DECK)

        Log.i(TAG, "string from $deckName, $cards is: $cardsStringBuilder")

        return cardsStringBuilder.toString()
    }

    fun shareableStringToDeck(string: String) : Pair<String, List<PrimitiveCard>> {

        val stringRepresentation = string.substring(0, string.length - 2)

        val deckRepresentation = stringRepresentation.split(END_OF_NAME)

        Log.d(TAG, "string representation is: $stringRepresentation \n deck representation is: $deckRepresentation")

        val deckName = deckRepresentation[0]

        val deckIsEmpty = deckRepresentation.size < 2
        val cards : MutableList<PrimitiveCard> = mutableListOf()

        if(!deckIsEmpty) {
            val cardsRepresentation = deckRepresentation[1].split(END_OF_CARD)
            for (cardString in cardsRepresentation) {
                val front = cardString.split(MIDDLE_OF_CARD)[0]
                val back = cardString.split(MIDDLE_OF_CARD)[1]
                cards.add(PrimitiveCard(front, back))
            }
        }

        Log.i(TAG, "pair from $string is: deckName=$deckName, cards=$cards")
        return Pair(deckName, cards)
    }

    fun stringWithoutIncorrectSymbols(string: String) : Boolean {
        return !(
                string.contains(END_OF_DECK) ||
                string.contains(END_OF_CARD) ||
                string.contains(END_OF_NAME) ||
                string.contains(MIDDLE_OF_CARD)
                )
    }
}
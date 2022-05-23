package com.example.flashcards.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.flashcards.model.Card
import com.example.flashcards.data.CardDao
import java.util.*


class CardRepository(private val cardDao: CardDao) {

    private val TAG = "CardRepository"

    fun getAllFromDeck(deckId: Int) : LiveData<List<Card>> {
        Log.i(TAG, "getting all cards from deck $deckId")
        Log.i(TAG, "all cards are: ${cardDao.getAllFromDeck(deckId)}")
        return cardDao.getAllFromDeck(deckId)
    }

    fun getActiveFromDeck(deckId: Int): List<Card> {
        Log.i(TAG, "getting active cards from deck $deckId")
        Log.i(TAG, "all active card from deck $deckId are ${cardDao.getRevisionFromDeck(deckId, Calendar.getInstance().time)}")
        return cardDao.getRevisionFromDeck(deckId, Calendar.getInstance().time)
    }

    suspend fun addCard(card: Card) {
        Log.i(TAG, "adding card $card")
        cardDao.addCard(card)
    }

    suspend fun updateCards(cards: List<Card>) {
        Log.i(TAG, "updating cards $cards")
        cardDao.updateCards(cards)
    }

    suspend fun updateCard(card: Card) {
        Log.i(TAG, "updating card $card")
        cardDao.updateCard(card)
        Log.i(TAG, "cards after update: ${cardDao.getAllNotLive()}")
    }

    suspend fun deleteCard(card: Card) {
        Log.i(TAG, "deleting card $card")
        cardDao.deleteCard(card)
        Log.i(TAG, "cards after delete: ${cardDao.getAllNotLive()}")
    }
}
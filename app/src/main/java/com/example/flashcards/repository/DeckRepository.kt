package com.example.flashcards.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.flashcards.data.CardDao
import com.example.flashcards.model.Deck
import com.example.flashcards.data.DeckDao
import java.util.*

class DeckRepository(private val deckDao: DeckDao, private val cardDao: CardDao) {

    private val TAG = "DeckRepository"

    //val getAllActive: LiveData<List<Deck>> = deckDao.getAllFromIdList(cardDao.getDeckIdsForRevision(Calendar.getInstance().time))
    val getAll: LiveData<List<Deck>> = deckDao.getAll()

    fun getAllActive(): LiveData<List<Deck>> {
        Log.i(TAG, "getting all active decks")

        val activeIds = cardDao.getDeckIdsForRevision(Calendar.getInstance().time)
        Log.i(TAG, "active deck's ids are: $activeIds")
        Log.i(TAG, "returning decks: ${deckDao.getAllFromIdList(activeIds)}")
        return deckDao.getAllFromIdList(activeIds)
    }

    suspend fun addDeck(deck: Deck) {
        Log.i(TAG, "adding deck $deck")
        deckDao.addDeck(deck)
    }

    suspend fun deleteDeck(deck: Deck) {
        Log.i(TAG, "deleting deck $deck")
        deckDao.deleteDeck(deck)
    }

}
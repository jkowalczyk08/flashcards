package com.example.flashcards.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.model.Card
import com.example.flashcards.model.Deck
import com.example.flashcards.model.DeckParser
import com.example.flashcards.model.PrimitiveCard
import com.example.flashcards.repository.CardRepository
import com.example.flashcards.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "DeckViewModel"

    val getAll: LiveData<List<Deck>>
    val getAllActive: LiveData<List<Deck>>
    private val repository: DeckRepository
    private val cardRepository: CardRepository

    init {
        val deckDao = AppDatabase.getDatabase(application).deckDao()
        val cardDao = AppDatabase.getDatabase(application).cardDao()
        repository = DeckRepository(deckDao, cardDao)
        cardRepository = CardRepository(cardDao)
        getAll = repository.getAll
        getAllActive = repository.getAllActive()
    }

    /*
    * Adds deck in the background
     */
    fun addDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDeck(deck)
        }
    }

    fun deleteDeck(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDeck(deck)
        }
    }

    private fun getDecksIdsForName(deckName: String) : List<Int> {
        return repository.getDecksIdsForName(deckName)
    }

    fun importDeck(deckName: String, primitiveCards: List<PrimitiveCard>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDeck(Deck(0, deckName, 1))
            try {
                val deckId = repository.getDecksIdsForName(deckName)[0]

                for(card in primitiveCards) {
                    cardRepository.addCard(Card(0, deckId, card.front, card.back, 0,  Calendar.getInstance().time))
                }
            } catch(e: Exception) {
                Log.e(TAG, "imported deck was not added to database", e)
            }
        }
    }

    fun correctName(deckName: String): Boolean {
        return nonemptyName(deckName) && correctSymbols(deckName) && uniqueName(deckName)
    }

    fun correctSymbols(deckName: String): Boolean {
        return DeckParser().stringWithoutIncorrectSymbols(deckName)
    }

    fun uniqueName(deckName: String): Boolean {
        return getDecksIdsForName(deckName).isEmpty()
    }

    fun nonemptyName(deckName: String): Boolean {
        return deckName.isNotEmpty()
    }
}
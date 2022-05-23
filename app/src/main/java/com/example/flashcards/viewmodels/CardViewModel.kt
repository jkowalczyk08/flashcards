package com.example.flashcards.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.model.Card
import com.example.flashcards.repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CardRepository

    init {
        val cardDao = AppDatabase.getDatabase(application).cardDao()
        repository = CardRepository(cardDao)
    }

    fun getAllFromDeck(deckId: Int) : LiveData<List<Card>> {
        return repository.getAllFromDeck(deckId)
    }

    /*
    * Adds card in the background
     */
    fun addCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCard(card)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCard(card)
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCard(card)
        }
    }
}
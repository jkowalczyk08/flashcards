package com.example.flashcards.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.model.Deck
import com.example.flashcards.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    val getAll: LiveData<List<Deck>>
    val getAllActive: LiveData<List<Deck>>
    private val repository: DeckRepository

    init {
        val deckDao = AppDatabase.getDatabase(application).deckDao()
        val cardDao = AppDatabase.getDatabase(application).cardDao()
        repository = DeckRepository(deckDao, cardDao)
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
}
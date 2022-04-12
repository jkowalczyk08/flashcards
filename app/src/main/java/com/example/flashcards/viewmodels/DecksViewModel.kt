package com.example.flashcards.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.data.DeckDao
import com.example.flashcards.data.DeckEntity

class DecksViewModel(private val deckDao: DeckDao) : ViewModel() {

    fun getAllDecks(): List<DeckEntity> = deckDao.getAll()
}

class DeckViewModelFactory(private val deckDao: DeckDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DecksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DecksViewModel(deckDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
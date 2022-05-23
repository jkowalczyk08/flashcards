package com.example.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flashcards.model.Deck

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks ORDER BY deck_name")
    fun getAll() : LiveData<List<Deck>>

    @Query("SELECT * FROM decks WHERE id IN (:decksIds) ORDER BY deck_name")
    fun getAllFromIdList(decksIds: List<Int>) : LiveData<List<Deck>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDeck(deck: Deck)

    @Delete
    suspend fun deleteDeck(deck: Deck)

    @Query("DELETE FROM decks")
    suspend fun removeAll()

    @Query("SELECT * FROM decks")
    suspend fun getAllNotLive() : List<Deck>
}
package com.example.flashcards.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DeckDao {
    @Query("SELECT * FROM deckEntity ORDER BY name")
    fun getAll() : List<DeckEntity>


}
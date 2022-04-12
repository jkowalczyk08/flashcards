package com.example.flashcards.data

import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface CardDao {
    @Query("SELECT * FROM cardEntity WHERE deck_name = :deckName ORDER BY score DESC")
    fun getAllFromDeck(deckName: String): List<CardEntity>

    @Query("SELECT * FROM cardEntity WHERE deck_name = :deckName AND next_revision < :date ORDER BY score DESC")
    fun getActiveFromDeck(deckName: String, date: Date) : List<CardEntity>
}
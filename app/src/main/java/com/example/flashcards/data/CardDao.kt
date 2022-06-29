package com.example.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flashcards.model.Card
import java.util.*

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE deck_id = :deckId ORDER BY score DESC")
    fun getAllFromDeck(deckId: Int): LiveData<List<Card>>

    @Query("SELECT * FROM cards WHERE deck_id = :deckId AND next_revision <= :revisionDate ORDER BY score DESC")
    fun getRevisionFromDeck(deckId: Int, revisionDate: Date): List<Card>

    @Query("SELECT DISTINCT deck_id FROM cards WHERE next_revision <= :revisionDate")
    fun getDeckIdsForRevision(revisionDate: Date): List<Int>

    @Query("SELECT * FROM cards WHERE deck_id = :deckId")
    fun getAllFromDeckNotLive(deckId: Int): List<Card>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card: Card)


    @Query("DELETE FROM cards")
    suspend fun removeAll()

    @Update
    suspend fun updateCards(cards: List<Card>)

    @Query("SELECT * FROM cards")
    suspend fun getAllNotLive() : List<Card>

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * FROM cards WHERE next_revision <= :date")
    fun getAllNextRevisionSmallerThan(date: Date): List<Card>

    @Query("SELECT * FROM cards WHERE prev_revision >= :date")
    fun getAllPrevRevisionBiggerThan(date: Date): List<Card>
}
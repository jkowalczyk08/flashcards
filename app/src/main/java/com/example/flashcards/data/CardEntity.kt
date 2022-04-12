package com.example.flashcards.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = DeckEntity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("deck_name"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class CardEntity(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "deck_name") var deckName: String,
    @NonNull @ColumnInfo(name = "front") var front: String,
    @NonNull @ColumnInfo(name = "back") var back: String,
    @NonNull @ColumnInfo(name = "score") var score: Int,
    @NonNull @ColumnInfo(name = "next_revision") var nextRevision: Long
    )

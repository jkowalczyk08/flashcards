package com.example.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeckEntity(
    @PrimaryKey var name: String,
    )

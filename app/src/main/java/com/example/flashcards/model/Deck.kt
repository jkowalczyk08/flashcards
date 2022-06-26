package com.example.flashcards.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "decks", indices = [Index(value = [ "deck_name" ], unique = true)])
data class Deck(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "deck_name") var deckName: String,
    @NonNull @ColumnInfo(name = "is_active") var isActive: Int
    ) : Parcelable

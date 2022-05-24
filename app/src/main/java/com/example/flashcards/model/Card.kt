package com.example.flashcards.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "cards",
    foreignKeys = [ForeignKey(
        entity = Deck::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("deck_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "deck_id") var deckId: Int,
    @NonNull @ColumnInfo(name = "front") var front: String,
    @NonNull @ColumnInfo(name = "back") var back: String,
    @NonNull @ColumnInfo(name = "score") var score: Int,
    @NonNull @ColumnInfo(name = "next_revision") var nextRevision: Date
    ) : Parcelable {

    fun updateEvaluation(evaluation: Evaluation) {
        score = evaluation.newScore
        nextRevision = evaluation.newRevisionDate
    }
}

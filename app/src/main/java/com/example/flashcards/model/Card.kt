package com.example.flashcards.model

data class Card(var front: String, var back: String) {

    var active = true;

    fun isActive() : Boolean {
        return active
    }

    fun saveProgress(progress: Progress) {
        active = progress != Progress.EASY
    }
}

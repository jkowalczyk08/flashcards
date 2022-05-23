package com.example.flashcards.model

interface Learnable {
    fun hasNext() : Boolean
    fun next() : LearningCard
    fun logAnswer(progress: Progress)
    fun getCards() : List<Card>
}
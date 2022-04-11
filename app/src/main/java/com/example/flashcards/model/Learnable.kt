package com.example.flashcards.model

interface Learnable {
    fun hasNext() : Boolean
    fun next() : Card
    fun saveAnswer(progress: Progress)
}
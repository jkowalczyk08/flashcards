package com.example.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.flashcards.data.DecksDatasource
import com.example.flashcards.model.Deck
import com.example.flashcards.model.Progress
import org.w3c.dom.Text

const val EXTRA_SELECTED_DECK = "com.example.flashcards.DECK"

class LearnDeckActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_deck)

        // initialize deck, text and buttons
        learn()
    }

    private fun moveToFinishedActivity() {
        val intent = Intent(this, FinishedLearningActivity::class.java)
        startActivity(intent)
    }

    private fun learn() {
        val deckToLearn: Deck = DecksDatasource().loadDeckForName(
            intent.getStringExtra(EXTRA_SELECTED_DECK)
        )

        if(deckToLearn.isFinished()) {
            moveToFinishedActivity()
        }
        var card = deckToLearn.getNextCard()

        val easyBtn = findViewById<Button>(R.id.easy_button)
        val mediumBtn = findViewById<Button>(R.id.medium_button)
        val hardBtn = findViewById<Button>(R.id.hard_button)

        val front = findViewById<TextView>(R.id.flashcard_front)
        val back = findViewById<TextView>(R.id.flashcard_back)

        front.text = card.front
        back.text = card.back

        front.visibility = View.VISIBLE
        changeVisibility(false, back, easyBtn, mediumBtn, hardBtn)

        front.setOnClickListener {
            changeVisibility(true, back, easyBtn, mediumBtn, hardBtn)
        }

        easyBtn.setOnClickListener {
            deckToLearn.saveProgress(Progress.EASY)
            if(deckToLearn.isFinished()) {
                moveToFinishedActivity()
            } else {
                card = deckToLearn.getNextCard()
                changeVisibility(false, back, easyBtn, mediumBtn, hardBtn)
                front.text = card.front
                back.text = card.back
            }
        }

        mediumBtn.setOnClickListener {
            deckToLearn.saveProgress(Progress.MEDIUM)
            if(deckToLearn.isFinished()) {
                moveToFinishedActivity()
            } else {
                card = deckToLearn.getNextCard()
                changeVisibility(false, back, easyBtn, mediumBtn, hardBtn)
                front.text = card.front
                back.text = card.back
            }
        }

        hardBtn.setOnClickListener {
            deckToLearn.saveProgress(Progress.HARD)
            if(deckToLearn.isFinished()) {
                moveToFinishedActivity()
            } else {
                card = deckToLearn.getNextCard()
                changeVisibility(false, back, easyBtn, mediumBtn, hardBtn)
                front.text = card.front
                back.text = card.back
            }
        }
    }

    private fun changeVisibility(visible: Boolean, back: TextView, easy: Button, medium: Button, hard: Button) {
        if(visible) {
            back.visibility = View.VISIBLE
            easy.visibility = View.VISIBLE
            medium.visibility = View.VISIBLE
            hard.visibility = View.VISIBLE
        }
        else {
            back.visibility = View.INVISIBLE
            easy.visibility = View.INVISIBLE
            medium.visibility = View.INVISIBLE
            hard.visibility = View.INVISIBLE
        }
    }
}

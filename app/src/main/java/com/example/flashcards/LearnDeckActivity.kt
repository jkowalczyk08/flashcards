package com.example.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.flashcards.data.DecksDatasource
import com.example.flashcards.model.Card
import com.example.flashcards.model.Deck
import com.example.flashcards.model.Learnable
import com.example.flashcards.model.Progress
import org.w3c.dom.Text

const val EXTRA_SELECTED_DECK = "com.example.flashcards.DECK"

class LearnDeckActivity() : AppCompatActivity() {

    private var learningList: Learnable? = null
    private var easyBtn: Button? = null
    private var mediumBtn: Button? = null
    private var hardBtn: Button? = null
    private var front: TextView? = null
    private var back: TextView? = null
    private var waitingForTap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_deck)

        easyBtn = findViewById<Button>(R.id.easy_button)
        mediumBtn = findViewById<Button>(R.id.medium_button)
        hardBtn = findViewById<Button>(R.id.hard_button)
        front = findViewById<TextView>(R.id.flashcard_front)
        back = findViewById<TextView>(R.id.flashcard_back)

        learningList = DecksDatasource.getLearningList(intent.getStringExtra(EXTRA_SELECTED_DECK))
        waitingForTap = false
        showQuestion()
    }

    private fun showQuestion() {

        changeVisibility(false)
        if (learningList?.hasNext() != true) {
            goFinishedActivity()
            return
        }
        val card = learningList?.next()
        front?.text = card?.front
        back?.text = card?.back
        waitingForTap = true
    }

    fun showAnswer(view: View) {
        if (!waitingForTap) {
            return
        }
        changeVisibility(true)
        waitingForTap = false
    }

    private fun goFinishedActivity() {
        val intent = Intent(this, FinishedLearningActivity::class.java)
        startActivity(intent)
    }

    fun reactEasyAnswer(view: View) {
        learningList?.saveAnswer(Progress.EASY)
        showQuestion()
    }

    fun reactMediumAnswer(view: View) {
        learningList?.saveAnswer(Progress.MEDIUM)
        showQuestion()
    }

    fun reactHardAnswer(view: View) {
        learningList?.saveAnswer(Progress.HARD)
        showQuestion()
    }

    private fun changeVisibility(isAnswer: Boolean) {
        if (isAnswer) {
            back?.visibility = View.VISIBLE
            easyBtn?.visibility = View.VISIBLE
            mediumBtn?.visibility = View.VISIBLE
            hardBtn?.visibility = View.VISIBLE
        } else {
            back?.visibility = View.INVISIBLE
            easyBtn?.visibility = View.INVISIBLE
            mediumBtn?.visibility = View.INVISIBLE
            hardBtn?.visibility = View.INVISIBLE
        }
    }
}
package com.example.flashcards.model

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

class ScoreCalculator {

    @RequiresApi(Build.VERSION_CODES.O)
    fun evaluateAnswer(progress: Progress, score: Int) : Evaluation {
        val calendar = Calendar.getInstance()
        when(score) {
            0 -> {
                if(progress == Progress.EASY) {
                    calendar.add(Calendar.DATE, 1)
                    return Evaluation(1, calendar.time, true)
                }
                else {
                    return Evaluation(0, calendar.time, false)
                }
            }
            1 -> {
                if(progress == Progress.EASY) {
                    calendar.add(Calendar.DATE, 1)
                    return Evaluation(2, calendar.time, true)
                }
                else if(progress == Progress.HARD) {
                    return Evaluation(0, calendar.time, false)
                }
                else {
                    return Evaluation(1, calendar.time, false)
                }
            }
            else -> {
                if(progress == Progress.EASY) {
                    calendar.add(Calendar.DATE, 2*score)
                    return Evaluation(2*score, calendar.time, true)
                }
                else if(progress == Progress.HARD) {
                    return Evaluation(score/2, calendar.time, false)
                }
                else {
                    return Evaluation(score, calendar.time, false)
                }
            }
        }
    }
}
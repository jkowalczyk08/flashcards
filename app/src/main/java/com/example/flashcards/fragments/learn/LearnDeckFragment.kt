package com.example.flashcards.fragments.learn

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flashcards.R
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.databinding.FragmentLearnDeckBinding
import com.example.flashcards.model.LearningList
import com.example.flashcards.model.Progress
import com.example.flashcards.model.ScoreCalculator
import com.example.flashcards.repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnDeckFragment : Fragment() {

    private val TAG = "LearnDeckFragment"

    private val args by navArgs<LearnDeckFragmentArgs>()
    private lateinit var repository: CardRepository

    private var _binding: FragmentLearnDeckBinding? = null
    private val binding get() = _binding!!

    private var waitingForTap = false
    private lateinit var learningList: LearningList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_learnDeckFragment_to_decksToLearnFragment)
        }
        callback.isEnabled = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLearnDeckBinding.inflate(inflater, container, false)
        val view = binding.root

        repository = CardRepository(AppDatabase.getDatabase(requireContext()).cardDao())
        Log.i(TAG, "getting all active cards from deck ${args.deckToLearn}")

        learningList = LearningList(repository.getActiveFromDeck(args.deckToLearn.id), ScoreCalculator())
        Log.i(TAG, "got active cards: ${repository.getActiveFromDeck(args.deckToLearn.id)}")


        binding.learnDeckConstraintLayout.setOnClickListener {
            if(waitingForTap) showAnswer()
        }
        binding.easyButton.setOnClickListener {
            if(!waitingForTap) reactToAnswer(Progress.EASY)
        }
        binding.mediumButton.setOnClickListener {
            if(!waitingForTap) reactToAnswer(Progress.MEDIUM)
        }
        binding.hardButton.setOnClickListener {
            if(!waitingForTap) reactToAnswer(Progress.HARD)
        }


        showQuestion()

        return view
    }

    private fun showQuestion() {
        changeVisibility(false)
        if(!learningList.hasNext()) {
            lifecycleScope.launch(Dispatchers.IO) {
                repository.updateCards(learningList.getCards())
            }
            changeVisibility(false)
            binding.learnFront.text = getString(R.string.congrats_message)

            return
        }
        val learningCard = learningList.next()
        binding.learnFront.text = learningCard.card.front
        binding.learnBack.text = learningCard.card.back
        waitingForTap = true
    }

    private fun showAnswer() {
        changeVisibility(true)
        waitingForTap = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun reactToAnswer(progress: Progress) {
        learningList.logAnswer(progress)
        showQuestion()
    }

    private fun changeVisibility(isAnswer: Boolean) {
        if(isAnswer) {
            binding.learnBack.visibility = View.VISIBLE
            binding.easyButton.visibility = View.VISIBLE
            binding.mediumButton.visibility = View.VISIBLE
            binding.hardButton.visibility = View.VISIBLE
        }
        else {
            binding.learnBack.visibility = View.INVISIBLE
            binding.easyButton.visibility = View.INVISIBLE
            binding.mediumButton.visibility = View.INVISIBLE
            binding.hardButton.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch(Dispatchers.IO) {
            repository.updateCards(learningList.getCards())
        }
    }
}
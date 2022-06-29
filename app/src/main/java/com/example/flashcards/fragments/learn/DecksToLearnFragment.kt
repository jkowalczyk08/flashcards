package com.example.flashcards.fragments.learn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.R
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.databinding.FragmentDecksToLearnBinding
import com.example.flashcards.repository.CardRepository
import com.example.flashcards.viewmodels.CardViewModel
import com.example.flashcards.viewmodels.DeckViewModel
import java.util.*


class DecksToLearnFragment : Fragment() {
    private val TAG = "DecksToLearnFragment"

    private var _binding: FragmentDecksToLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var deckViewModel: DeckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_decksToLearnFragment_to_homeFragment)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDecksToLearnBinding.inflate(inflater, container, false)
        val view = binding.root

        displayStatistics(view)

        //RecyclerView of decks
        val adapter = LearningDecksListAdapter()
        val recyclerView = binding.learningDecksRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // DeckViewModel
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        deckViewModel.getAllActive.observe(viewLifecycleOwner, Observer { deck ->
            adapter.setData(deck)
        })

        val cardRepository = CardRepository(AppDatabase.getDatabase(requireContext()).cardDao())

        if(cardRepository.getDeckIdsForRevision(Calendar.getInstance().time).isEmpty()) {
            binding.noMoreDecksMessage.visibility = View.VISIBLE
        }
        else {
            binding.noMoreDecksMessage.visibility = View.INVISIBLE
        }

        return view
    }

    private fun displayStatistics(view: View) {
        val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        val pendingCount = cardViewModel.getPendingCount()
        val finishedCount = cardViewModel.getFinishedCount()
        Log.i(TAG, "pending count = $pendingCount, finishedCount = $finishedCount")
        binding.finishedCountTextView.text = finishedCount.toString()
        binding.pendingCountTextView.text = pendingCount.toString()
    }
}
package com.example.flashcards.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentHomeBinding
import com.example.flashcards.viewmodels.CardViewModel
import com.example.flashcards.viewmodels.DeckViewModel

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var pendingCount = 0
    private var finishedCount = 0
    private lateinit var cardViewModel: CardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the binding and layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        pendingCount = cardViewModel.getPendingCount()
        finishedCount = cardViewModel.getFinishedCount()
        Log.i(TAG, "pending count = $pendingCount, finishedCount = $finishedCount")

        // TODO show pending and finished count

        binding.decksButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_decksFragment)
        }

        binding.learnButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_decksToLearnFragment)
        }

        binding.bluetoothButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bluetoothFragment)
        }

        return view
    }
}
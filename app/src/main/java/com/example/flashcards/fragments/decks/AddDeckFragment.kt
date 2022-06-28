package com.example.flashcards.fragments.decks

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.model.Deck
import com.example.flashcards.databinding.FragmentAddDeckBinding
import com.example.flashcards.viewmodels.DeckViewModel

class AddDeckFragment : Fragment() {

    private lateinit var deckViewModel: DeckViewModel

    private var _binding: FragmentAddDeckBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_addDeckFragment_to_decksFragment)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the binding and layout for this fragment
        _binding = FragmentAddDeckBinding.inflate(inflater, container, false)
        val view = binding.root

        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        binding.addDeckButton.setOnClickListener {
            insertDeckToDatabase()
        }

        return view
    }

    private fun insertDeckToDatabase() {
        val deckName = binding.deckNameEditText.text.toString()

        if(newDeckInputCheck(deckName)) {
            // Create new deck object
            val deck = Deck(0, deckName, 1)
            deckViewModel.addDeck(deck)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            // Navigate Back
            findNavController().navigate(R.id.action_addDeckFragment_to_decksFragment)

        } else {
            Toast.makeText(requireContext(), "Name is incorrect. Please choose different name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun newDeckInputCheck(deckName: String): Boolean {
        return deckViewModel.correctName(deckName)
    }

}
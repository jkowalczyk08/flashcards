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
import androidx.navigation.fragment.navArgs
import com.example.flashcards.databinding.FragmentAddCardBinding
import com.example.flashcards.model.Card
import com.example.flashcards.viewmodels.CardViewModel
import java.util.*

class AddCardFragment : Fragment() {

    private val args by navArgs<AddCardFragmentArgs>()
    private lateinit var cardViewModel: CardViewModel

    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = AddCardFragmentDirections.actionAddCardFragmentToEditDeckFragment(args.deckForNewCard)
            findNavController().navigate(action)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the binding and layout for this fragment
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        val view = binding.root

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        binding.addNewCardButton.setOnClickListener {
            insertCardToDatabase()
        }

        return view
    }

    private fun insertCardToDatabase() {
        val deckId = args.deckForNewCard.id
        val front = binding.editTextFront.text.toString()
        val back = binding.editTextBack.text.toString()

        if(newCardInputCheck(front, back)) {
            val card = Card(0, deckId, front, back, 0, Calendar.getInstance().time)
            cardViewModel.addCard(card)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out front and back.", Toast.LENGTH_SHORT).show()
        }
        binding.editTextBack.text.clear()
        binding.editTextFront.text.clear()
    }

    private fun newCardInputCheck(front: String, back: String): Boolean {
        return !(TextUtils.isEmpty(front)) && !(TextUtils.isEmpty(back))
    }
}
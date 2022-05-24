package com.example.flashcards.fragments.decks

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
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
import com.example.flashcards.databinding.FragmentEditCardBinding
import com.example.flashcards.viewmodels.CardViewModel


class EditCardFragment : Fragment() {

    private val args by navArgs<EditCardFragmentArgs>()
    private lateinit var cardViewModel: CardViewModel

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = EditCardFragmentDirections.actionEditCardFragmentToEditDeckFragment(args.deck)
            findNavController().navigate(action)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCardBinding.inflate(inflater, container, false)
        val view = binding.root

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        binding.updateFront.text = Editable.Factory.getInstance().newEditable(args.card.front)
        binding.updateBack.text = Editable.Factory.getInstance().newEditable(args.card.back)
        binding.updateCardButton.setOnClickListener {
            updateCardToDatabase()
        }
        binding.deleteCardButton.setOnClickListener {
            deleteCardFromDatabase()
        }

        return view
    }

    private fun deleteCardFromDatabase() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            cardViewModel.deleteCard(args.card)
            Toast.makeText(requireContext(), "Flashcard removed", Toast.LENGTH_SHORT).show()
            val action = EditCardFragmentDirections.actionEditCardFragmentToEditDeckFragment(args.deck)
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No") {_, _ ->

        }
        builder.setTitle("Delete this flashcard?")
            .setMessage("Are you sure you want to delete this flashcard?")
            .create()
            .show()
    }

    private fun updateCardToDatabase() {
        val front = binding.updateFront.text.toString()
        val back = binding.updateBack.text.toString()

        if(newCardInputCheck(front, back)) {
            args.card.front = front
            args.card.back = back
            cardViewModel.updateCard(args.card)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            val action = EditCardFragmentDirections.actionEditCardFragmentToEditDeckFragment(args.deck)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill out front and back.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun newCardInputCheck(front: String, back: String): Boolean {
        return !(TextUtils.isEmpty(front)) && !(TextUtils.isEmpty(back))
    }
}
package com.example.flashcards.fragments.decks

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentEditDeckBinding
import com.example.flashcards.model.Deck
import com.example.flashcards.viewmodels.CardViewModel
import com.example.flashcards.viewmodels.DeckViewModel

class EditDeckFragment : Fragment() {

    private val args by navArgs<EditDeckFragmentArgs>()

    private var _binding: FragmentEditDeckBinding? = null
    private val binding get() = _binding!!

    private lateinit var deckViewModel: DeckViewModel
    private lateinit var cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_editDeckFragment_to_decksFragment)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the binding and layout for this fragment
        _binding = FragmentEditDeckBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.editedDeckName.text = args.deckToEdit.deckName

        // deckViewModel
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        binding.deleteDeckButton.setOnClickListener {
            deleteDeck(args.deckToEdit, view)
        }

        // CardsRecyclerView
        val adapter = CardsListAdapter(args.deckToEdit)
        val recyclerView = binding.cardsRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // cardViewModel
        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        cardViewModel.getAllFromDeck(args.deckToEdit.id).observe(viewLifecycleOwner, Observer { cards ->
            adapter.setData(cards)
        })


        // add new card
        binding.addNewCardFAB.setOnClickListener {
            val action = EditDeckFragmentDirections.actionEditDeckFragmentToAddCardFragment(args.deckToEdit)
            view.findNavController().navigate(action)
        }


        return view
    }

    private fun deleteDeck(deck: Deck, view: View){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            deckViewModel.deleteDeck(deck)
            Toast.makeText(requireContext(), "Successfully removed ${args.deckToEdit.deckName}", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_editDeckFragment_to_decksFragment)
        }
        builder.setNegativeButton("No") {_, _ ->

        }
        builder.setTitle("Delete ${args.deckToEdit.deckName}?")
        .setMessage("Are you sure you want to delete ${args.deckToEdit.deckName}?")
        .create()
        .show()
    }
}
package com.example.flashcards.fragments.bluetooth

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentDecksBinding
import com.example.flashcards.databinding.FragmentDecksToShareBinding
import com.example.flashcards.model.Deck
import com.example.flashcards.model.DeckParser
import com.example.flashcards.viewmodels.CardViewModel
import com.example.flashcards.viewmodels.DeckViewModel


class DecksToShareFragment : Fragment() {

    private val TAG = "DecksToShareFragment"

    private val args by navArgs<DecksToShareFragmentArgs>()

    private var _binding: FragmentDecksToShareBinding? = null
    private val binding get() = _binding!!

    private lateinit var deckViewModel: DeckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            args.bluetoothService.stop()
            findNavController().navigate(R.id.action_decksToShareFragment_to_homeFragment)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDecksToShareBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = ShareDeckListAdapter(this)
        val recyclerView = binding.shareDecksRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // DeckViewModel
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        deckViewModel.getAll.observe(viewLifecycleOwner, Observer { deck ->
            adapter.setData(deck)
        })

        return view
    }

    fun shareDeck(deck: Deck) {
        val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        val cards = cardViewModel.getAllFromDeckNotLive(deck.id)
        val cardsString = DeckParser().deckToShareableString(deck.deckName, cards)
        args.bluetoothService.write(cardsString.toByteArray())
    }
}
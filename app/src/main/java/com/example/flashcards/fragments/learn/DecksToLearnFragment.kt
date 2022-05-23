package com.example.flashcards.fragments.learn

import android.os.Bundle
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
import com.example.flashcards.databinding.FragmentDecksToLearnBinding
import com.example.flashcards.viewmodels.DeckViewModel


class DecksToLearnFragment : Fragment() {

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
    ): View? {

        _binding = FragmentDecksToLearnBinding.inflate(inflater, container, false)
        val view = binding.root

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

        return view
    }
}
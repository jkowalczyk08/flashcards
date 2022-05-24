package com.example.flashcards.fragments.decks

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentBluetoothDevicesBinding

class BluetoothDevicesFragment : Fragment() {

    private val args by navArgs<BluetoothDevicesFragmentArgs>()

    private var _binding: FragmentBluetoothDevicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = BluetoothDevicesFragmentDirections.actionBluetoothDevicesFragmentToEditDeckFragment(args.deckToShare)
            findNavController().navigate(action)
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBluetoothDevicesBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }
}
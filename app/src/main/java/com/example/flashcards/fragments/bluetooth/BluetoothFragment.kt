package com.example.flashcards.fragments.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentBluetoothBinding

class BluetoothFragment : Fragment() {

    private val TAG = "BluetoothFragment"

    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
        }
        callback.isEnabled = true

        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

        if(bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
            return
        }

        // If BT is not on, request that it be enabled.
        if(bluetoothAdapter.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                // TODO: do nothing?
            }
            else {
                Log.i(TAG, "BT not enabled")
                findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
            }
        }
    }
}
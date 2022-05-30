package com.example.flashcards.fragments.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentBluetoothBinding


class BluetoothFragment : Fragment() {

    private val TAG = "BluetoothFragment"

    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_DISCOVERABLE = 1
    private val REQUEST_ENABLE_GPS = 2
    private val REQUEST_ENABLE_BT = 3

    private var bluetoothAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // back navigation
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
        }
        callback.isEnabled = true


        // bluetooth
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        if(bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
            return
        }

        // If BT is not on, request that it be enabled. also ensure the device is discoverable
        if(bluetoothAdapter!!.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            ensureDiscoverable()
        }

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context?.registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        context?.unregisterReceiver(receiver)
    }

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        // TODO: deal with permissions
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if(device != null) {
                        val deviceName = device.name
                        val deviceHardwareAddress = device.address // MAC address
                        Log.i(TAG, "found device with name: $deviceName and MAC: $deviceHardwareAddress")
                    }
                }
            }
        }

    }

    private fun ensureDiscoverable() {
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        }
        startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE)
    }

    private fun ensureGpsEnabled() {
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager?
        val isGpsEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            startActivityForResult(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                REQUEST_ENABLE_GPS
            )
        }
    }

    @SuppressLint("MissingPermission")
    // TODO: deal with permissions
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_ENABLE_BT -> {
                if(resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "BT enabled")
                    ensureGpsEnabled()
                }
                else {
                    Log.i(TAG, "BT not enabled")
                    findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
                }
            }

            REQUEST_ENABLE_GPS -> {
                if(resultCode == Activity.RESULT_CANCELED) {
                    Log.i(TAG, "GPS not enabled")
                    findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
                }
                else {
                    Log.i(TAG, "GPS enabled")
                    ensureDiscoverable()
                }
            }

            REQUEST_DISCOVERABLE -> {
                if(resultCode == Activity.RESULT_CANCELED) {
                    Log.i(TAG, "Device not discoverable")
                    findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
                }
                else {
                    Log.i(TAG, "Device discoverable")
                    if(bluetoothAdapter?.startDiscovery() == true) {
                        Log.i(TAG, "discovery started")
                    }
                    else {
                        Log.i(TAG, "discovery failed")
                    }
                }
            }
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


}
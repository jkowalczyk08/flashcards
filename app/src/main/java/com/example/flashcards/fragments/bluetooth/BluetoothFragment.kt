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
import android.view.Window
import android.widget.*
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    private lateinit var newDevicesArrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // back navigation
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_bluetoothFragment_to_homeFragment)
        }
        callback.isEnabled = true

        newDevicesArrayAdapter = ArrayAdapter(requireContext(), R.layout.device_text_view)

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
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        context?.registerReceiver(receiver, filter)
    }


    private val receiver = object : BroadcastReceiver() {

        private val devicesSet: MutableSet<BluetoothDevice> = mutableSetOf()

        @SuppressLint("MissingPermission")
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
                        if(!devicesSet.contains(device)) {
                            newDevicesArrayAdapter.add(device.name + "\n" + device.address)
                            devicesSet.add(device)
                        }
                    }
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.i(TAG, "discovery finished")
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
                    if(bluetoothAdapter?.isDiscovering() == true) bluetoothAdapter?.cancelDiscovery()

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

        var newDevicesListView = binding.devicesListView
        newDevicesListView.adapter = newDevicesArrayAdapter
        newDevicesListView.setOnItemClickListener(deviceClickListener)

        return view
    }

    @SuppressLint("MissingPermission")
    val deviceClickListener = AdapterView.OnItemClickListener { av, v, p2, p3 ->
        bluetoothAdapter?.cancelDiscovery()

        if(v is TextView) {
            Log.i(TAG, "selected device ${v.text}")

        }

    }



    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()

        bluetoothAdapter?.cancelDiscovery()

        context?.unregisterReceiver(receiver)
    }
}



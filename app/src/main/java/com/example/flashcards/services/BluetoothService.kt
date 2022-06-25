package com.example.flashcards.services

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.IOException

@Parcelize
class BluetoothService(
    private val context: @RawValue Context,
    private val handler: @RawValue Handler,
    private val bluetoothAdapter: @RawValue BluetoothAdapter,
    private var connectedThread: @RawValue ConnectedThread?
    ) : Parcelable {

    private val TAG = "BluetoothService"
    private val NAME = "Flashcards"
    private val UUID = java.util.UUID.fromString("3cf9b353-7064-470e-b843-f84c8a8f73fc")

    private var acceptThread: AcceptThread? = null
    private var connectThread: ConnectThread? = null
    //private var connectedThread: ConnectedThread? = null

    private val MESSAGE_WRITE = 1;
    private val MESSAGE_READ = 2;

    /**
     * Starts the bluetooth service.
     * starts AcceptThread to begin a session in listening mode.
     */
    fun start() {
        if(acceptThread != null) {
            acceptThread!!.cancel()
            acceptThread = null
        }
        if(acceptThread == null) {
            acceptThread = AcceptThread()
            acceptThread!!.start()
        }
        Log.i(TAG, "started bluetooth service")
    }

    /**
     * Start the ConnectThread
     */
    fun connect(device: BluetoothDevice) {
        Log.i(TAG, "connecting to $device")

        if(connectThread != null) {
            connectThread!!.cancel()
            connectThread = null
        }

        if(connectThread == null) {
            connectThread = ConnectThread(device)
            connectThread!!.start()
        }
    }

    /**
     * Start the ConnectedThread to begin managing a bluetooth connection
     */
     fun connected(socket: BluetoothSocket, device: BluetoothDevice) {

        // Cancel the thread that completed the connection
        if (connectThread != null) {
            connectThread!!.cancel()
            connectThread = null
        }

        // Cancel any thread currently running a connection
        if (connectedThread != null) {
            connectedThread!!.cancel()
            connectedThread = null
        }

        // Cancel the accept thread because we only want to connect to one device
        if (acceptThread != null) {
            acceptThread!!.cancel()
            acceptThread = null
        }

        // Start the thread to manage the connection and perform the transmission
        connectedThread = ConnectedThread(socket)
        connectedThread!!.start()

        Log.i(TAG, "connected to $device")
    }
    fun stop() {
        Log.i(TAG, "stopping")

        if (connectThread != null) {
            connectThread!!.cancel()
            connectThread = null
        }

        if (connectedThread != null) {
            connectedThread!!.cancel()
            connectedThread = null
        }

        if (acceptThread != null) {
            acceptThread!!.cancel()
            acceptThread = null
        }
    }

    fun write(out: ByteArray) {
        if(connectedThread != null) {
            connectedThread!!.write(out)
        }
    }


    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private inner class AcceptThread : Thread() {
        @SuppressLint("MissingPermission")
        private val serverSocket: BluetoothServerSocket =
            bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, UUID)

        override fun run() {
            Log.i(TAG, "accept thread started running")

            var socket: BluetoothSocket? = null
            while(socket == null) {
                try {
                    socket = serverSocket.accept()
                } catch (e: IOException) {
                    Log.e(TAG, "accept thread failed on serversocket.accept()", e)
                    break
                }
            }

            if(socket != null) {
                connected(socket, socket.remoteDevice)
            }
            Log.i(TAG, "end of accept thread")
        }

        fun cancel() {
            serverSocket.close()
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private inner class ConnectThread(
        private val bluetoothDevice: BluetoothDevice
    ) : Thread() {

        @SuppressLint("MissingPermission")
        private val socket: BluetoothSocket =
            bluetoothDevice.createRfcommSocketToServiceRecord(UUID)

        @SuppressLint("MissingPermission")
        override fun run() {
            Log.i(TAG, "connect thread started running")

            // Always cancel discovery because it will slow down a connection
            bluetoothAdapter.cancelDiscovery()

            try {
                socket.connect()
            } catch (e: IOException) {
                Log.e(TAG, "connect thread failed on socket.connect()", e)
            }
            // Reset the ConnectThread because we're done
            connectThread = null

            // Start the connected thread
            if(socket.isConnected) {
                connected(socket, bluetoothDevice)
            }

            Log.i(TAG, "end of connect thread")
        }

        fun cancel() {
            socket.close()
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    inner class ConnectedThread(
        private val socket: BluetoothSocket
    ) : Thread() {

        private val inputStream = socket.inputStream
        private val outputStream = socket.outputStream

        override fun run() {
            Log.i(TAG, "connected thread started running, connected to ${socket.remoteDevice}")
            val buffer = ByteArray(1024)
            var bytes = 0

            while(socket.isConnected) {
                try {
                    bytes = inputStream.read(buffer)
                    Log.i(TAG, "connected thread received $bytes bytes")

                    handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget()
                } catch(e: IOException) {
                    Log.e(TAG, "disconnected", e)
                }

            }
        }

        fun write(buffer: ByteArray) {
            try {
                outputStream.write(buffer)

                Log.i(TAG, "connected thread wrote to outputStream")

                // Share the sent message back to the UI Activity
                handler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer).sendToTarget()


            } catch(e: IOException) {
                Log.e(TAG, "Exception during write", e)
            }
        }

        fun cancel() {
            socket.close()
        }
    }
}

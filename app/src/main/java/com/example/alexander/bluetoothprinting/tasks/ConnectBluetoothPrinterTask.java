package com.example.alexander.bluetoothprinting.tasks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.alexander.bluetoothprinting.models.Barcode;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by alexander on 7/18/15.
 */
public class ConnectBluetoothPrinterTask extends Thread {

    private final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
    private final BluetoothAdapter mBluetoothAdapter;
    private final Barcode mBarcode;

    public ConnectBluetoothPrinterTask(BluetoothDevice device,
                                       BluetoothAdapter bluetoothAdapter,
                                       Barcode barcode) {

            BluetoothSocket temporarySocket = null;
            mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
            mBluetoothAdapter = bluetoothAdapter;
            mBarcode = barcode;

            try {
                mBluetoothAdapter.cancelDiscovery();
                temporarySocket = mDevice.createInsecureRfcommSocketToServiceRecord(SPP_UUID);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket = temporarySocket;
        }

    @Override
    public void run() {
            try {
                mSocket.connect();

            } catch (IOException connectException) {
                try {
                    mSocket.close();

                } catch (IOException closeException) {

                    closeException.printStackTrace();
                }
            }
            manageBluetoothConnection(mSocket, mBarcode);
        }


    public void cancel() {
            try {
                mSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void manageBluetoothConnection(BluetoothSocket socket, Barcode barcode){

        final PrintBarcodeTask printDataTask = new PrintBarcodeTask(socket, barcode);
        printDataTask.start();
    }

}

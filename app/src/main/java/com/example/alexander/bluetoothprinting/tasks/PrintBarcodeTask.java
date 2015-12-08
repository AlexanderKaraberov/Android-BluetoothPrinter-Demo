package com.example.alexander.bluetoothprinting.tasks;

import android.bluetooth.BluetoothSocket;

import com.example.alexander.bluetoothprinting.models.Barcode;
import com.example.alexander.bluetoothprinting.utils.ByteHelper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by alexander on 12/8/15.
 */
public class PrintBarcodeTask extends Thread {

    private final BluetoothSocket mSocket;
    private final OutputStream mOutputStream;
    private final Barcode mBarcode;

    public PrintBarcodeTask(BluetoothSocket socket, Barcode barcode) {

        mSocket = socket;

        OutputStream temporaryOutput = null;
        try {
            temporaryOutput = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mOutputStream = temporaryOutput;
        mBarcode = barcode;
    }

    private void sendCommand(final int command){

        try {
            mOutputStream.write(ByteHelper.commandByteRepresentation(command));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGSCommand(){

        final int gsCommand = 29;

        try {
            mOutputStream.write(ByteHelper.commandByteRepresentation(gsCommand));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendPrintBarcodeCommand(final int barcodeValue) {

        final String barcode = Integer.toString(barcodeValue);
        final int barcodeLength = barcode.length();

        try {
            mOutputStream.write(ByteHelper.commandByteRepresentation(mBarcode.getPrintCodeCommand()));
            mOutputStream.write(ByteHelper.commandByteRepresentation(mBarcode.getBarcodeSystem().getValue()));
            mOutputStream.write(ByteHelper.commandByteRepresentation(barcodeLength));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < barcode.length(); i++) {

            try {
                mOutputStream.write((barcode.charAt(i) + "").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
         //Setting tab don't need a GS command
        sendCommand(mBarcode.getHorizontalTab());

        sendGSCommand();
        sendCommand(mBarcode.getHeightCommandCode());
        sendCommand(mBarcode.getHeight());

        sendGSCommand();
        sendCommand(mBarcode.getWidthCommandCode());
        sendCommand(mBarcode.getWidth());

        sendGSCommand();
        sendPrintBarcodeCommand(mBarcode.getValue());
    }

    public void cancel() {

        try {

            mOutputStream.close();
            mSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.alexander.bluetoothprinting.activities;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexander.bluetoothprinting.R;
import com.example.alexander.bluetoothprinting.models.Barcode;
import com.example.alexander.bluetoothprinting.models.BarcodeSystem;
import com.example.alexander.bluetoothprinting.tasks.ConnectBluetoothPrinterTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //Print button's onClick event
    public void printBarcode(View v) {

        final EditText barcodeTextEdit = (EditText)findViewById(R.id.editText);

        if (barcodeTextEdit.getText().length() > 0) {

            final String barcodeTextRepresentation = barcodeTextEdit.getText().toString();
            final int barcodeValue = Integer.parseInt(barcodeTextRepresentation);

            //Setup barcode
            final Barcode barcode = Barcode
                    .builder()
                    .setValue(barcodeValue)
                    .setCodeSystem(BarcodeSystem.CODE39)
                    .setHeight(112)
                    .setWidth(112)
                    .setHorizontalTab(30)
                    .build();

            final List<BluetoothDevice> devicesList = new ArrayList<>();
            final Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();

            if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF ||
                    mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {

                Toast.makeText(MainActivity.this, R.string.enable_bluetooth, Toast.LENGTH_SHORT).show();

            } else {

                final List<String> deviceNames = new ArrayList<String>();

                if (bondedDevices.size() > 0) {

                    for (final BluetoothDevice device : bondedDevices) {

                        deviceNames.add(device.getName());
                        devicesList.add(device);
                    }
                    showAlert(devicesList, deviceNames, barcode);
                }
            }
        } else Toast.makeText(MainActivity.this, R.string.enter_barcode, Toast.LENGTH_SHORT).show();
    }

    private void showAlert(final List<BluetoothDevice> devicesList,
                           final List<String> deviceNames,
                           final Barcode barcode) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.paired_devices)
                .setSingleChoiceItems(deviceNames.toArray(new CharSequence[deviceNames.size()]),
                        -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                final BluetoothDevice selectedDevice = devicesList.get(which);

                                final ConnectBluetoothPrinterTask printerTask = new ConnectBluetoothPrinterTask(
                                        selectedDevice,
                                        mBluetoothAdapter,
                                        barcode);

                                printerTask.start();
                            }
                        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

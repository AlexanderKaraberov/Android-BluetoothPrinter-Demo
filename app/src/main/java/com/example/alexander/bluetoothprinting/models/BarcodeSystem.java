package com.example.alexander.bluetoothprinting.models;

/**
 * Created by alexander on 7/18/15.
 */
public enum BarcodeSystem {

    JAN13(67),
    JAN8(68),
    CODE39(69),
    CODE128(73);

    private int value;

    private BarcodeSystem(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

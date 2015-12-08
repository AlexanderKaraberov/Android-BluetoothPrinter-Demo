package com.example.alexander.bluetoothprinting.utils;

import java.nio.ByteBuffer;

/**
 * Created by alexander on 7/11/15.
 */
public class ByteHelper {

    //Prepare data for printer representation
    public static byte commandByteRepresentation(final int command) {

        final byte[] bytes = ByteBuffer
                .allocate(4)
                .putInt(command)
                .array();

        return bytes[3];
    }
}

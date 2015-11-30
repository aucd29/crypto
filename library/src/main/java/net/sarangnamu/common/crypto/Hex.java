/*
 * Hex.java
 * Copyright 2014 Burke Choi All right reserverd.
 *             http://www.sarangnamu.net
 */
package net.sarangnamu.common.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hex {
    private static final Logger mLog = LoggerFactory.getLogger(Hex.class);

    public static byte[] toBytes(String values) {
        if (values == null || values.length() == 0) {
            mLog.error("values == null or values.length == 0");
            return null;
        }

        byte[] value = new byte[values.length() / 2];
        for (int i = 0; i < value.length; ++i) {
            value[i] = (byte) Integer.parseInt(values.substring(2 * i, 2 * i + 2), 16);
        }

        return value;
    }
}

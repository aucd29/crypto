/*
 * TripleDES.java
 * Copyright 2014 Burke Choi All right reserverd.
 *             http://www.sarangnamu.net
 */
package net.sarangnamu.common.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripleDES extends CipherBase {
    private static final Logger mLog = LoggerFactory.getLogger(TripleDES.class);

    private static final String TYPE = "DESede";

    public static String encrypt(final String option, final String iv, final String data, final String key) {
        if (!checkKeyLength(key)) {
            return null;
        }

        return doEncrypt(option, iv, TYPE, data, key);
    }

    public static String decrypt(final String option, final String iv, final String data, final String key) {
        if (!checkKeyLength(key)) {
            return null;
        }

        return doDecrypt(option, iv, TYPE, data, key);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // PRIVATE
    //
    ////////////////////////////////////////////////////////////////////////////////////

    private static boolean checkKeyLength(final String key) {
        if (key.length() > 8) {
            mLog.error("DES key too long - should be 8 bytes");
            return false;
        }

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // INTERFACE
    //
    ////////////////////////////////////////////////////////////////////////////////////

    public static interface ECB {
        public static final String PKCS5 = "DESede/ECB/PKCS5Padding";
        public static final String NOPAD = "DESede/ECB/NoPadding";
    }

    public static interface CBC {
        public static final String PKCS5 = "DESede/CBC/PKCS5Padding";
        public static final String NOPAD = "DESede/CBC/NoPadding";
    }
}

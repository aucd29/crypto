/*
 * CipherBase.java
 * Copyright 2014 Burke Choi All right reserverd.
 *             http://www.sarangnamu.net
 */
package net.sarangnamu.common.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class CipherBase {
    private static final Logger mLog = LoggerFactory.getLogger(CipherBase.class);

    protected static String doEncrypt(final String type, final String data, final String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), type);
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Bytes.toHex(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (NullPointerException e) {
            mLog.error(e.getMessage());
        } catch (Exception e) {
            mLog.error(e.getMessage());
        }

        return null;
    }

    protected static String doEncrypt(final String cipherOption, final String iv, final String type, final String data, final String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), type);
            Cipher cipher = Cipher.getInstance(cipherOption);

            if (iv == null) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
            }

            return Bytes.toHex(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (NullPointerException e) {
            mLog.error(e.getMessage());
        } catch (Exception e) {
            mLog.error(e.getMessage());
        }

        return null;
    }

    protected static String doDecrypt(final String type, final String data, final String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), type);
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Hex.toBytes(data));

            return new String(decrypted, "UTF-8");
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }

        return null;
    }

    protected static String doDecrypt(final String cipherOption, final String iv, final String type, final String data, final String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), type);
            Cipher cipher = Cipher.getInstance(cipherOption);

            if (iv == null) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
            }

            byte[] decrypted = cipher.doFinal(Hex.toBytes(data));

            return new String(decrypted, "UTF-8");
        } catch (NullPointerException e) {
            mLog.error(e.getMessage());
        } catch (Exception e) {
            mLog.error(e.getMessage());
        }

        return null;
    }
}

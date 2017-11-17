///*
// * DigestBase.java
// * Copyright 2014 Burke Choi All right reserverd.
// *             http://www.sarangnamu.net
// */
//package net.sarangnamu.common.crypto;
//
//import java.security.MessageDigest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public abstract class DigestBase {
//    private static final Logger mLog = LoggerFactory.getLogger(DigestBase.class);
//
//    protected static String getDigest(final String type, final String data) {
//        try {
//            return MessageDigest.getInstance(type).digest(data.getBytes("UTF-8")).toString();
//        } catch (NullPointerException e) {
//            mLog.error(e.getMessage());
//        } catch (Exception e) {
//            mLog.error(e.getMessage());
//        }
//
//        return null;
//    }
//}

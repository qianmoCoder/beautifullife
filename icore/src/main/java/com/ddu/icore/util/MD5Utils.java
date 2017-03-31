package com.ddu.icore.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {

    public static String compute(String content) throws Exception {
        if (content.isEmpty())
            return null;
        MessageDigest digestInstance = MessageDigest.getInstance("MD5");
        digestInstance.update(content.getBytes("utf-8"));
        byte[] md = digestInstance.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = (md[i]) & 0xff;
            if (val < 16)
                sb.append("0");
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toUpperCase();
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}

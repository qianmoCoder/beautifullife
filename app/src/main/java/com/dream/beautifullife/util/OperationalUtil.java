package com.dream.beautifullife.util;

/**
 * Created by admin on 2015/9/25.
 */
public class OperationalUtil {
    private static String mHexDigits = "0123456789ABCDEF";

    public static String bytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder("");
        if(bytes == null || bytes.length<=0){
            return null;
        }
        for(int i =0;i < bytes.length;i++){
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if(hv.length() < 2){
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString){
        if(hexString == null || hexString.equals("")){
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) mHexDigits.indexOf(c);
    }

}

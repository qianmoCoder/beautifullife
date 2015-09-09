package com.dream.beautifullife.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/9/9.
 */
public class URLUtil {

    public static HashMap<String,String> encodeAll(HashMap<String,String> parmas, final String charsetName) throws UnsupportedEncodingException {
        return encode(parmas, charsetName, new Callback() {
            @Override
            public void encode(String key, String value) throws UnsupportedEncodingException{
                URLEncoder.encode(key,charsetName);
                URLEncoder.encode(value,charsetName);
            }
        });
    }

    public static HashMap<String,String> encodeValue(HashMap<String,String> parmas, final String charsetName) throws UnsupportedEncodingException {
        return encode(parmas, charsetName, new Callback() {
            @Override
            public void encode(String key, String value) throws UnsupportedEncodingException{
                URLEncoder.encode(value,charsetName);
            }
        });
    }

    public static HashMap<String,String> encodeValueUTF8(HashMap<String,String> parmas) throws UnsupportedEncodingException {
       return encodeValue(parmas, "UTF-8");
    }

    private static HashMap<String,String> encode(HashMap<String,String> parmas,String charsetName,Callback callback) throws UnsupportedEncodingException {

        HashMap<String,String> tempParams = (HashMap<String,String>)parmas.clone();
        tempParams.clear();

        for(Map.Entry<String,String> entry : parmas.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            callback.encode(key,value);
            tempParams.put(key,value);
        }
        return  tempParams;
    }

    public interface Callback{
         void encode(String key,String value) throws UnsupportedEncodingException;
    }
}


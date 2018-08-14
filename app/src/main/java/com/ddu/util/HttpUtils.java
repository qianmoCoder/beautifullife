package com.ddu.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by yzbzz on 2017/10/24.
 */

public class HttpUtils {

    public static String post(String url, Map<String, Object> form) {
        HttpURLConnection conn = null;
        PrintWriter pw = null;
        BufferedReader rd = null;
        StringBuilder out = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        String line = null;
        String response = null;
        for (String key : form.keySet()) {
            if (out.length() != 0) {
                out.append("&");
            }
            out.append(key).append("=").append(form.get(key));
        }
        Log.v("lhz", "out: " + out);
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setUseCaches(false);
            conn.connect();
            pw = new PrintWriter(conn.getOutputStream());
            pw.print(out.toString());
            pw.flush();
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            while ((line = rd.readLine()) != null) {
//                sb.append(line);
//            }
//            response = sb.toString();
            saveAd(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (rd != null) {
                    rd.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static void saveAd(InputStream inputStream) {
        File file = new File(Environment.getExternalStorageDirectory() + "/icore/1.jpg");
        Log.v("lhz", "file: " + file.getAbsolutePath());
        FileOutputStream out = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file, false);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

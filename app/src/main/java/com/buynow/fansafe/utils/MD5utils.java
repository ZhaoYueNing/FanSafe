package com.buynow.fansafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by buynow on 16-6-7.
 * MD5加密
 */
public class MD5utils {
    public static String encode(String string)  {
        StringBuilder res = new StringBuilder();
        String s = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(string.getBytes());
        for (byte b :
                digest) {
            int d = b & 0xff;
            s = Integer.toHexString(d);
            if (s.length() == 1) {
                s = "0"+s;
            }
            res.append(s);
        }
        return res.toString();
    }
}

package com.appsflyer;

import java.security.MessageDigest;
import java.util.Formatter;

final class t {
    t() {
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    public static String m221(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.reset();
            instance.update(str.getBytes("UTF-8"));
            return m218(instance.digest());
        } catch (Exception e) {
            AFLogger.afErrorLog(new StringBuilder("Error turning ").append(str.substring(0, 6)).append(".. to SHA1").toString(), e);
            return null;
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public static String m220(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(str.getBytes("UTF-8"));
            return m218(instance.digest());
        } catch (Exception e) {
            AFLogger.afErrorLog(new StringBuilder("Error turning ").append(str.substring(0, 6)).append(".. to MD5").toString(), e);
            return null;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public static String m219(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            AFLogger.afErrorLog(new StringBuilder("Error turning ").append(str.substring(0, 6)).append(".. to SHA-256").toString(), e);
            return null;
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static String m218(byte[] bArr) {
        Formatter formatter = new Formatter();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            formatter.format("%02x", new Object[]{Byte.valueOf(bArr[i])});
        }
        String obj = formatter.toString();
        formatter.close();
        return obj;
    }
}

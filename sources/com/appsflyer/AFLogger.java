package com.appsflyer;

import android.support.annotation.NonNull;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AFLogger {

    /* renamed from: ˋ  reason: contains not printable characters */
    private static long f12 = System.currentTimeMillis();

    public enum LogLevel {
        NONE(0),
        ERROR(1),
        WARNING(2),
        INFO(3),
        DEBUG(4),
        VERBOSE(5);
        

        /* renamed from: ˋ  reason: contains not printable characters */
        private int f14;

        private LogLevel(int i) {
            this.f14 = i;
        }

        public final int getLevel() {
            return this.f14;
        }
    }

    public static void afInfoLog(String str, boolean z) {
        boolean z2;
        if (LogLevel.INFO.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            Log.i(AppsFlyerLib.LOG_TAG, m12(str, false));
        }
        if (z) {
            C0001r.m185().m200("I", m12(str, true));
        }
    }

    public static void resetDeltaTime() {
        f12 = System.currentTimeMillis();
    }

    @NonNull
    /* renamed from: ˎ  reason: contains not printable characters */
    private static String m12(String str, boolean z) {
        if (z || LogLevel.VERBOSE.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            return new StringBuilder("(").append(m13(System.currentTimeMillis() - f12)).append(") [").append(Thread.currentThread().getName()).append("] ").append(str).toString();
        }
        return str;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static void m15(String str, Throwable th, boolean z) {
        boolean z2;
        if (LogLevel.ERROR.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 && z) {
            Log.e(AppsFlyerLib.LOG_TAG, m12(str, false), th);
        }
        C0001r.m185().m194(th);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    static void m11(String str) {
        boolean z;
        if (LogLevel.WARNING.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Log.w(AppsFlyerLib.LOG_TAG, m12(str, false));
        }
        C0001r.m185().m200("W", m12(str, true));
    }

    public static void afRDLog(String str) {
        boolean z;
        if (LogLevel.VERBOSE.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Log.v(AppsFlyerLib.LOG_TAG, m12(str, false));
        }
        C0001r.m185().m200("V", m12(str, true));
    }

    public static void afInfoLog(String str) {
        afInfoLog(str, true);
    }

    public static void afErrorLog(String str, Throwable th) {
        m15(str, th, false);
    }

    public static void afErrorLog(String str, Throwable th, boolean z) {
        m15(str, th, z);
    }

    public static void afWarnLog(String str) {
        m11(str);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static String m13(long j) {
        long hours = TimeUnit.MILLISECONDS.toHours(j);
        long millis = j - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long millis2 = millis - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis2);
        long millis3 = TimeUnit.MILLISECONDS.toMillis(millis2 - TimeUnit.SECONDS.toMillis(seconds));
        return String.format(Locale.getDefault(), "%02d:%02d:%02d:%03d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds), Long.valueOf(millis3)});
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    static void m14(String str) {
        if (!AppsFlyerProperties.getInstance().isLogsDisabledCompletely()) {
            Log.d(AppsFlyerLib.LOG_TAG, m12(str, false));
        }
        C0001r.m185().m200("F", str);
    }

    public static void afDebugLog(String str) {
        boolean z;
        if (LogLevel.DEBUG.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Log.d(AppsFlyerLib.LOG_TAG, m12(str, false));
        }
        C0001r.m185().m200("D", m12(str, true));
    }
}

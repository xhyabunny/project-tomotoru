package com.appsflyer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class j {

    /* renamed from: ʻ  reason: contains not printable characters */
    private static final BitSet f149 = new BitSet(6);

    /* renamed from: ʼ  reason: contains not printable characters */
    private static volatile j f150;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static final Handler f151 = new Handler(Looper.getMainLooper());

    /* renamed from: ʽ  reason: contains not printable characters */
    final Runnable f152 = new Runnable() {
        public final void run() {
            synchronized (j.this.f155) {
                if (j.this.f158) {
                    j.this.f157.removeCallbacks(j.this.f160);
                    j.this.f157.removeCallbacks(j.this.f153);
                    j.this.m149();
                    j.this.f158 = false;
                }
            }
        }
    };

    /* renamed from: ˊ  reason: contains not printable characters */
    final Runnable f153 = new AnonymousClass1();

    /* renamed from: ˊॱ  reason: contains not printable characters */
    private final Map<f, Map<String, Object>> f154 = new HashMap(f149.size());

    /* renamed from: ˋ  reason: contains not printable characters */
    final Object f155 = new Object();

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private boolean f156;

    /* renamed from: ˎ  reason: contains not printable characters */
    final Handler f157;

    /* renamed from: ˏ  reason: contains not printable characters */
    boolean f158;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private final SensorManager f159;

    /* renamed from: ॱ  reason: contains not printable characters */
    final Runnable f160 = new AnonymousClass2();

    /* renamed from: ᐝ  reason: contains not printable characters */
    private final Map<f, f> f161 = new HashMap(f149.size());

    static {
        f149.set(1);
        f149.set(2);
        f149.set(4);
    }

    /* renamed from: com.appsflyer.j$1  reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        public final void run() {
            synchronized (j.this.f155) {
                j.this.m149();
                j.this.f157.postDelayed(j.this.f160, 1800000);
            }
        }

        AnonymousClass1() {
        }

        /* renamed from: ˋ  reason: contains not printable characters */
        static boolean m151(Context context, String str) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, str);
            AFLogger.afRDLog(new StringBuilder("is Permission Available: ").append(str).append("; res: ").append(checkSelfPermission).toString());
            return checkSelfPermission == 0;
        }
    }

    /* renamed from: com.appsflyer.j$2  reason: invalid class name */
    class AnonymousClass2 implements Runnable {

        /* renamed from: ˋ  reason: contains not printable characters */
        private static String f163;

        /* renamed from: ˏ  reason: contains not printable characters */
        private static String f164;

        AnonymousClass2() {
        }

        public final void run() {
            synchronized (j.this.f155) {
                j.this.m150();
                j.this.f157.postDelayed(j.this.f153, 500);
                j.this.f158 = true;
            }
        }

        AnonymousClass2() {
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        static void m153(String str) {
            f163 = str;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append("*");
                }
            }
            f164 = sb.toString();
        }

        /* renamed from: ˊ  reason: contains not printable characters */
        static void m152(String str) {
            if (f163 == null) {
                m153(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY));
            }
            if (f163 != null && str.contains(f163)) {
                AFLogger.afInfoLog(str.replace(f163, f164));
            }
        }
    }

    private j(@NonNull SensorManager sensorManager, Handler handler) {
        this.f159 = sensorManager;
        this.f157 = handler;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static j m147(Context context) {
        return m146((SensorManager) context.getApplicationContext().getSystemService("sensor"), f151);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static j m146(SensorManager sensorManager, Handler handler) {
        if (f150 == null) {
            synchronized (j.class) {
                if (f150 == null) {
                    f150 = new j(sensorManager, handler);
                }
            }
        }
        return f150;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m150() {
        boolean z;
        try {
            for (Sensor next : this.f159.getSensorList(-1)) {
                int type = next.getType();
                if (type < 0 || !f149.get(type)) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    f r1 = f.m125(next);
                    if (!this.f161.containsKey(r1)) {
                        this.f161.put(r1, r1);
                    }
                    this.f159.registerListener(this.f161.get(r1), next, 0);
                }
            }
        } catch (Throwable th) {
        }
        this.f156 = true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m149() {
        try {
            if (!this.f161.isEmpty()) {
                for (f next : this.f161.values()) {
                    this.f159.unregisterListener(next);
                    next.m129(this.f154);
                }
            }
        } catch (Throwable th) {
        }
        this.f156 = false;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    /* renamed from: ˊ  reason: contains not printable characters */
    public final List<Map<String, Object>> m148() {
        List<Map<String, Object>> arrayList;
        synchronized (this.f155) {
            if (!this.f161.isEmpty() && this.f156) {
                for (f r0 : this.f161.values()) {
                    r0.m128(this.f154);
                }
            }
            if (this.f154.isEmpty()) {
                arrayList = Collections.emptyList();
            } else {
                arrayList = new ArrayList<>(this.f154.values());
            }
        }
        return arrayList;
    }
}

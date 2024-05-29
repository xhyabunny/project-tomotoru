package com.appsflyer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class f implements SensorEventListener {

    /* renamed from: ʻ  reason: contains not printable characters */
    private double f119;

    /* renamed from: ʼ  reason: contains not printable characters */
    private final int f120;
    @NonNull

    /* renamed from: ˊ  reason: contains not printable characters */
    private final String f121;

    /* renamed from: ˋ  reason: contains not printable characters */
    private final long[] f122 = new long[2];
    @NonNull

    /* renamed from: ˎ  reason: contains not printable characters */
    private final String f123;

    /* renamed from: ˏ  reason: contains not printable characters */
    private final float[][] f124 = new float[2][];

    /* renamed from: ॱ  reason: contains not printable characters */
    private final int f125;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private long f126;

    private f(int i, @Nullable String str, @Nullable String str2) {
        this.f125 = i;
        this.f123 = str == null ? "" : str;
        this.f121 = str2 == null ? "" : str2;
        this.f120 = ((this.f123.hashCode() + ((i + 31) * 31)) * 31) + this.f121.hashCode();
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static f m125(Sensor sensor) {
        return new f(sensor.getType(), sensor.getName(), sensor.getVendor());
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static double m121(@NonNull float[] fArr, @NonNull float[] fArr2) {
        int min = Math.min(fArr.length, fArr2.length);
        double d = 0.0d;
        for (int i = 0; i < min; i++) {
            d += StrictMath.pow((double) (fArr[i] - fArr2[i]), 2.0d);
        }
        return Math.sqrt(d);
    }

    @NonNull
    /* renamed from: ॱ  reason: contains not printable characters */
    private static List<Float> m126(@NonNull float[] fArr) {
        ArrayList arrayList = new ArrayList(fArr.length);
        for (float valueOf : fArr) {
            arrayList.add(Float.valueOf(valueOf));
        }
        return arrayList;
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        boolean z;
        if (sensorEvent != null && sensorEvent.values != null) {
            Sensor sensor = sensorEvent.sensor;
            if (sensor == null || sensor.getName() == null || sensor.getVendor() == null) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                int type = sensorEvent.sensor.getType();
                String name = sensorEvent.sensor.getName();
                String vendor = sensorEvent.sensor.getVendor();
                long j = sensorEvent.timestamp;
                float[] fArr = sensorEvent.values;
                if (m124(type, name, vendor)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    float[] fArr2 = this.f124[0];
                    if (fArr2 == null) {
                        this.f124[0] = Arrays.copyOf(fArr, fArr.length);
                        this.f122[0] = currentTimeMillis;
                        return;
                    }
                    float[] fArr3 = this.f124[1];
                    if (fArr3 == null) {
                        float[] copyOf = Arrays.copyOf(fArr, fArr.length);
                        this.f124[1] = copyOf;
                        this.f122[1] = currentTimeMillis;
                        this.f119 = m121(fArr2, copyOf);
                    } else if (50000000 <= j - this.f126) {
                        this.f126 = j;
                        if (Arrays.equals(fArr3, fArr)) {
                            this.f122[1] = currentTimeMillis;
                            return;
                        }
                        double r2 = m121(fArr2, fArr);
                        if (r2 > this.f119) {
                            this.f124[1] = Arrays.copyOf(fArr, fArr.length);
                            this.f122[1] = currentTimeMillis;
                            this.f119 = r2;
                        }
                    }
                }
            }
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m129(@NonNull Map<f, Map<String, Object>> map) {
        m123(map, true);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m128(Map<f, Map<String, Object>> map) {
        m123(map, false);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private void m123(@NonNull Map<f, Map<String, Object>> map, boolean z) {
        boolean z2 = false;
        if (this.f124[0] != null) {
            z2 = true;
        }
        if (z2) {
            map.put(this, m122());
            if (z) {
                m127();
            }
        } else if (!map.containsKey(this)) {
            map.put(this, m122());
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private boolean m124(int i, @NonNull String str, @NonNull String str2) {
        return this.f125 == i && this.f123.equals(str) && this.f121.equals(str2);
    }

    @NonNull
    /* renamed from: ˊ  reason: contains not printable characters */
    private Map<String, Object> m122() {
        HashMap hashMap = new HashMap(7);
        hashMap.put("sT", Integer.valueOf(this.f125));
        hashMap.put("sN", this.f123);
        hashMap.put("sV", this.f121);
        float[] fArr = this.f124[0];
        if (fArr != null) {
            hashMap.put("sVS", m126(fArr));
        }
        float[] fArr2 = this.f124[1];
        if (fArr2 != null) {
            hashMap.put("sVE", m126(fArr2));
        }
        return hashMap;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private void m127() {
        for (int i = 0; i < 2; i++) {
            this.f124[i] = null;
        }
        for (int i2 = 0; i2 < 2; i2++) {
            this.f122[i2] = 0;
        }
        this.f119 = 0.0d;
        this.f126 = 0;
    }

    public final int hashCode() {
        return this.f120;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return m124(fVar.f125, fVar.f123, fVar.f121);
    }
}

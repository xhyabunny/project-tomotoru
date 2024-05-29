package com.sony.smartar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import java.util.List;

final class SensorDevice {
    private final Context a;
    /* access modifiers changed from: private */
    public final long b;
    private final SensorManager c;
    /* access modifiers changed from: private */
    public boolean d = false;
    /* access modifiers changed from: private */
    public long e = -1;
    private final SensorEventListener f = new SensorEventListener() {
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (SensorDevice.this.e == -1) {
                SensorDevice.this.a(sensorEvent);
            }
            if (SensorDevice.this.d) {
                SensorDevice.onSensor(SensorDevice.this.b, 0, sensorEvent.values, sensorEvent.timestamp - SensorDevice.this.e);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    private final SensorEventListener g = new SensorEventListener() {
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (SensorDevice.this.e == -1) {
                SensorDevice.this.a(sensorEvent);
            }
            if (SensorDevice.this.d) {
                SensorDevice.onSensor(SensorDevice.this.b, 1, sensorEvent.values, sensorEvent.timestamp - SensorDevice.this.e);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    /* access modifiers changed from: private */
    public static native void onSensor(long j, int i, float[] fArr, long j2);

    public SensorDevice(Context context, long j) {
        this.a = context;
        this.b = j;
        this.c = (SensorManager) context.getSystemService("sensor");
    }

    public void release() {
        stop();
    }

    public int getRotation() {
        return 0;
    }

    public boolean isGyroAvailable() {
        return this.c.getSensorList(4).size() > 0;
    }

    public boolean isAccelerometerAvailable() {
        if (this.c.getSensorList(1).size() > 0) {
            return true;
        }
        return false;
    }

    public SensorManager getNativeDevice() {
        return this.c;
    }

    public int start() {
        a(1, this.f);
        a(4, this.g);
        this.d = true;
        return 0;
    }

    private void a(int i, SensorEventListener sensorEventListener) {
        List<Sensor> sensorList = this.c.getSensorList(i);
        if (sensorList.size() > 0) {
            this.c.registerListener(sensorEventListener, sensorList.get(0), 0);
        }
    }

    public int stop() {
        this.d = false;
        this.c.unregisterListener(this.f);
        this.c.unregisterListener(this.g);
        return 0;
    }

    /* access modifiers changed from: private */
    public void a(SensorEvent sensorEvent) {
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long uptimeMillis = SystemClock.uptimeMillis();
        long abs = Math.abs((1000000 * currentTimeMillis) - sensorEvent.timestamp);
        long abs2 = Math.abs((1000000 * elapsedRealtime) - sensorEvent.timestamp);
        long abs3 = Math.abs((1000000 * uptimeMillis) - sensorEvent.timestamp);
        if (abs < abs2 && abs < abs3) {
            this.e = (currentTimeMillis - uptimeMillis) * 1000000;
        } else if (abs2 >= abs || abs2 >= abs3) {
            this.e = 0;
        } else {
            this.e = (elapsedRealtime - uptimeMillis) * 1000000;
        }
    }
}

package com.cyberstep.sanriocharacters;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorListener implements SensorEventListener {
    Sensor grav;
    float[] grav_val = new float[3];
    Sensor mag;
    float[] mag_val = new float[3];
    SensorManager sensorman;

    public void wakeupSensorManager(Context context) {
        this.sensorman = (SensorManager) context.getSystemService("sensor");
    }

    private int startMagneticField() {
        if (this.mag == null) {
            this.mag = this.sensorman.getDefaultSensor(2);
        }
        this.sensorman.registerListener(this, this.mag, 2);
        return 0;
    }

    private int stopMagneticField() {
        if (this.mag == null) {
            return 0;
        }
        this.sensorman.unregisterListener(this, this.mag);
        return 0;
    }

    private int startGravitySensor() {
        if (this.grav == null) {
            this.grav = this.sensorman.getDefaultSensor(9);
        }
        this.sensorman.registerListener(this, this.grav, 2);
        return 0;
    }

    private int stopGravitySensor() {
        if (this.grav == null) {
            return 0;
        }
        this.sensorman.unregisterListener(this, this.grav);
        return 0;
    }

    public void onSensorChanged(SensorEvent e) {
        switch (e.sensor.getType()) {
            case 2:
                this.mag_val = (float[]) e.values.clone();
                return;
            case 9:
                this.grav_val = (float[]) e.values.clone();
                return;
            default:
                return;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public float[] calcCurrentRotationMA(float[] accel) {
        float[] rotmat = new float[16];
        float[] adjust = new float[3];
        SensorManager.getRotationMatrix(rotmat, new float[16], accel, this.mag_val);
        SensorManager.getOrientation(rotmat, adjust);
        return adjust;
    }

    public float[] getMagneticValue() {
        return this.mag_val;
    }

    public float[] getGravityValue() {
        return this.grav_val;
    }
}

package com.sony.smartar;

import android.content.Context;

final class ScreenDevice {
    ScreenDevice() {
    }

    public static int getRotation(Context context) {
        int b = d.b(context);
        switch (b) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                throw new RuntimeException("unexpected value: " + b);
        }
    }
}

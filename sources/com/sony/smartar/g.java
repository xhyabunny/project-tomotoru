package com.sony.smartar;

import android.graphics.PixelFormat;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: GuPixelUtil */
final class g {
    private static AtomicReference<PixelFormat> a = new AtomicReference<>();

    public static int a(int i, int i2, int i3) {
        return ((i * i2) * a(i3)) / 8;
    }

    private static int a(int i) {
        int i2;
        PixelFormat andSet = a.getAndSet((Object) null);
        if (andSet == null) {
            andSet = new PixelFormat();
        }
        PixelFormat.getPixelFormatInfo(i, andSet);
        if (i == 2) {
            i2 = andSet.bytesPerPixel * 8;
        } else {
            i2 = andSet.bitsPerPixel;
        }
        a.set(andSet);
        return i2;
    }
}

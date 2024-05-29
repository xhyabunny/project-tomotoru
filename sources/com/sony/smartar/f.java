package com.sony.smartar;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: GuLevel8Methods */
final class f {
    private static final Method A;
    private static final Method B = i.a((Class<?>) SurfaceView.class, "setWindowType", (Class<?>[]) new Class[]{Integer.TYPE});
    private static final Field C = i.a((Class<?>) SurfaceView.class, "mLayout");
    private static final Method a;
    private static final Method b;
    private static final Method c;
    private static final Constructor<?> d;
    private static final Field e;
    private static final Field f;
    private static final Method g;
    private static final Method h;
    private static final Method i;
    private static final Method j;
    private static final Method k;
    private static final Method l;
    private static final Method m;
    private static final Method n;
    private static final Method o;
    private static final Method p;
    private static final Method q;
    private static final Method r;
    private static final Method s;
    private static final Method t;
    private static final Method u;
    private static final Method v;
    private static final Constructor<?> w;
    private static final Field x;
    private static final Field y;
    private static final Constructor<?> z;

    static {
        if (Build.VERSION.SDK_INT >= 9) {
            Class<?> a2 = i.a("android.hardware.Camera$CameraInfo");
            a = i.a((Class<?>) Camera.class, "open", (Class<?>[]) new Class[]{Integer.TYPE});
            b = i.a((Class<?>) Camera.class, "getNumberOfCameras", (Class<?>[]) new Class[0]);
            c = i.a((Class<?>) Camera.class, "getCameraInfo", (Class<?>[]) new Class[]{Integer.TYPE, a2});
            d = i.a(a2, (Class<?>[]) new Class[0]);
            e = i.a(a2, "facing");
            f = i.a(a2, "orientation");
            g = i.a((Class<?>) Camera.Parameters.class, "getSupportedPreviewFpsRange", (Class<?>[]) new Class[0]);
            h = i.a((Class<?>) Camera.Parameters.class, "setPreviewFpsRange", (Class<?>[]) new Class[]{Integer.TYPE, Integer.TYPE});
        } else {
            a = null;
            b = null;
            c = null;
            d = null;
            e = null;
            f = null;
            g = null;
            h = null;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            i = i.a((Class<?>) Camera.class, "setPreviewTexture", (Class<?>[]) new Class[]{i.a("android.graphics.SurfaceTexture")});
        } else {
            i = null;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            j = i.a((Class<?>) Camera.Parameters.class, "setRecordingHint", (Class<?>[]) new Class[]{Boolean.TYPE});
            k = i.a((Class<?>) Camera.Parameters.class, "getMaxNumFocusAreas", (Class<?>[]) new Class[0]);
            l = i.a((Class<?>) Camera.Parameters.class, "getFocusAreas", (Class<?>[]) new Class[0]);
            m = i.a((Class<?>) Camera.Parameters.class, "setFocusAreas", (Class<?>[]) new Class[]{List.class});
            n = i.a((Class<?>) Camera.Parameters.class, "getMaxNumMeteringAreas", (Class<?>[]) new Class[0]);
            o = i.a((Class<?>) Camera.Parameters.class, "getMeteringAreas", (Class<?>[]) new Class[0]);
            p = i.a((Class<?>) Camera.Parameters.class, "setMeteringAreas", (Class<?>[]) new Class[]{List.class});
            q = i.a((Class<?>) Camera.Parameters.class, "setAutoWhiteBalanceLock", (Class<?>[]) new Class[]{Boolean.TYPE});
            r = i.a((Class<?>) Camera.Parameters.class, "setAutoExposureLock", (Class<?>[]) new Class[]{Boolean.TYPE});
            s = i.a((Class<?>) Camera.Parameters.class, "getAutoWhiteBalanceLock", (Class<?>[]) new Class[0]);
            t = i.a((Class<?>) Camera.Parameters.class, "getAutoExposureLock", (Class<?>[]) new Class[0]);
            u = i.a((Class<?>) Camera.Parameters.class, "isAutoExposureLockSupported", (Class<?>[]) new Class[0]);
            v = i.a((Class<?>) Camera.Parameters.class, "isAutoWhiteBalanceLockSupported", (Class<?>[]) new Class[0]);
            Class<?> a3 = i.a("android.hardware.Camera$Area");
            w = i.a(a3, (Class<?>[]) new Class[]{Rect.class, Integer.TYPE});
            x = i.a(a3, "rect");
            y = i.a(a3, "weight");
        } else {
            j = null;
            k = null;
            l = null;
            m = null;
            n = null;
            o = null;
            p = null;
            q = null;
            r = null;
            s = null;
            t = null;
            u = null;
            v = null;
            w = null;
            x = null;
            y = null;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            z = i.a(i.a("android.graphics.SurfaceTexture"), (Class<?>[]) new Class[]{Integer.TYPE});
        } else {
            z = null;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            A = i.a(i.a("android.graphics.SurfaceTexture"), "release", (Class<?>[]) new Class[0]);
        } else {
            A = null;
        }
    }

    public static Camera a(int i2) {
        return (Camera) i.a(a, Integer.valueOf(i2));
    }

    public static int a() {
        return ((Integer) i.a(b, new Object[0])).intValue();
    }

    public static void a(int i2, Object obj) {
        i.a(c, Integer.valueOf(i2), obj);
    }

    public static Object b() {
        return i.a(d, new Object[0]);
    }

    public static int a(Object obj) {
        return ((Integer) i.a(obj, e)).intValue();
    }

    public static int b(Object obj) {
        return ((Integer) i.a(obj, f)).intValue();
    }

    public static List<int[]> a(Camera.Parameters parameters) {
        return (List) i.a((Object) parameters, g, new Object[0]);
    }

    public static void a(Camera.Parameters parameters, int i2, int i3) {
        i.a((Object) parameters, h, Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void a(Camera camera, Object obj) {
        i.a((Object) camera, i, obj);
    }

    public static void a(Camera.Parameters parameters, boolean z2) {
        i.a((Object) parameters, j, Boolean.valueOf(z2));
    }

    public static int b(Camera.Parameters parameters) {
        return ((Integer) i.a((Object) parameters, k, new Object[0])).intValue();
    }

    public static List<Object> c(Camera.Parameters parameters) {
        return (List) i.a((Object) parameters, l, new Object[0]);
    }

    public static void a(Camera.Parameters parameters, List<Object> list) {
        i.a((Object) parameters, m, list);
    }

    public static int d(Camera.Parameters parameters) {
        return ((Integer) i.a((Object) parameters, n, new Object[0])).intValue();
    }

    public static void b(Camera.Parameters parameters, List<Object> list) {
        i.a((Object) parameters, p, list);
    }

    public static void b(Camera.Parameters parameters, boolean z2) {
        i.a((Object) parameters, q, Boolean.valueOf(z2));
    }

    public static void c(Camera.Parameters parameters, boolean z2) {
        i.a((Object) parameters, r, Boolean.valueOf(z2));
    }

    public static boolean e(Camera.Parameters parameters) {
        return ((Boolean) i.a((Object) parameters, s, new Object[0])).booleanValue();
    }

    public static boolean f(Camera.Parameters parameters) {
        return ((Boolean) i.a((Object) parameters, t, new Object[0])).booleanValue();
    }

    public static boolean g(Camera.Parameters parameters) {
        return ((Boolean) i.a((Object) parameters, u, new Object[0])).booleanValue();
    }

    public static boolean h(Camera.Parameters parameters) {
        return ((Boolean) i.a((Object) parameters, v, new Object[0])).booleanValue();
    }

    public static Object a(Rect rect, int i2) {
        return i.a(w, rect, Integer.valueOf(i2));
    }

    public static void a(Object obj, Rect rect) {
        i.a(obj, x, (Object) rect);
    }

    public static void a(Object obj, int i2) {
        i.a(obj, y, (Object) Integer.valueOf(i2));
    }

    public static SurfaceTexture b(int i2) {
        return (SurfaceTexture) i.a(z, Integer.valueOf(i2));
    }

    public static void c(Object obj) {
        i.a(obj, A, new Object[0]);
    }
}

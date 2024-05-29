package com.sony.smartar;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: GuUtils */
final class j {
    public static final boolean a;
    public static final boolean b;
    public static final boolean c = Build.MODEL.equals("P-01D");
    public static final boolean d = Build.MODEL.equals("SC-01C");

    static {
        boolean z;
        Class<?> cls;
        boolean z2 = true;
        if (Build.MANUFACTURER.indexOf("Sony") != -1) {
            z = true;
        } else {
            z = false;
        }
        a = z;
        try {
            cls = Class.forName("com.unity3d.player.UnityPlayer", false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            cls = null;
        }
        if (cls == null) {
            z2 = false;
        }
        b = z2;
    }

    public static <E> E a(View view, Class<E> cls) {
        if (cls.isInstance(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                E a2 = a(viewGroup.getChildAt(i), cls);
                if (a2 != null) {
                    return a2;
                }
            }
        }
        return null;
    }
}

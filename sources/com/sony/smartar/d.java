package com.sony.smartar;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

/* compiled from: GuExtra */
final class d {
    public static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static int b(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
    }

    public static void a(String str) {
        Log.d("SmartAR", str);
    }

    public static void b(String str) {
        Log.i("SmartAR", str);
    }
}

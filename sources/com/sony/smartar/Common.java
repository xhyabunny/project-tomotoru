package com.sony.smartar;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

final class Common {
    Common() {
    }

    public static byte[] loadAssets(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            return e.a(open, open.available());
        } catch (IOException e) {
            return null;
        }
    }
}

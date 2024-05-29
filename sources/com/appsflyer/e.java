package com.appsflyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

final class e {

    /* renamed from: ˎ  reason: contains not printable characters */
    private IntentFilter f115 = new IntentFilter("android.intent.action.BATTERY_CHANGED");

    static final class b {

        /* renamed from: ˊ  reason: contains not printable characters */
        static final e f116 = new e();
    }

    e() {
    }

    /* access modifiers changed from: package-private */
    @NonNull
    /* renamed from: ˋ  reason: contains not printable characters */
    public final d m118(Context context) {
        String str = null;
        float f = 0.0f;
        try {
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, this.f115);
            if (registerReceiver != null) {
                if (2 == registerReceiver.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1)) {
                    switch (registerReceiver.getIntExtra("plugged", -1)) {
                        case 1:
                            str = "ac";
                            break;
                        case 2:
                            str = "usb";
                            break;
                        case 4:
                            str = "wireless";
                            break;
                        default:
                            str = "other";
                            break;
                    }
                } else {
                    str = "no";
                }
                int intExtra = registerReceiver.getIntExtra("level", -1);
                int intExtra2 = registerReceiver.getIntExtra("scale", -1);
                if (!(-1 == intExtra || -1 == intExtra2)) {
                    f = (100.0f * ((float) intExtra)) / ((float) intExtra2);
                }
            }
        } catch (Throwable th) {
        }
        return new d(f, str);
    }

    static final class d {

        /* renamed from: ˋ  reason: contains not printable characters */
        private final String f117;

        /* renamed from: ॱ  reason: contains not printable characters */
        private final float f118;

        d(float f, String str) {
            this.f118 = f;
            this.f117 = str;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ˎ  reason: contains not printable characters */
        public final float m120() {
            return this.f118;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ˋ  reason: contains not printable characters */
        public final String m119() {
            return this.f117;
        }

        d() {
        }
    }
}

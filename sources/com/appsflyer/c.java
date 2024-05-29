package com.appsflyer;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.appsflyer.j;

final class c {

    static final class a {

        /* renamed from: ॱ  reason: contains not printable characters */
        static final c f107 = new c();
    }

    c() {
    }

    @Nullable
    /* renamed from: ॱ  reason: contains not printable characters */
    static Location m115(@NonNull Context context) {
        Location location;
        Location location2;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (m114(context, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"})) {
                location = locationManager.getLastKnownLocation("network");
            } else {
                location = null;
            }
            if (m114(context, new String[]{"android.permission.ACCESS_FINE_LOCATION"})) {
                location2 = locationManager.getLastKnownLocation("gps");
            } else {
                location2 = null;
            }
            if (location2 == null && location == null) {
                location2 = null;
            } else if (location2 == null && location != null) {
                location2 = location;
            } else if ((location != null || location2 == null) && 60000 < location.getTime() - location2.getTime()) {
                location2 = location;
            }
            if (location2 != null) {
                return location2;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static boolean m114(@NonNull Context context, @NonNull String[] strArr) {
        for (String r3 : strArr) {
            if (j.AnonymousClass1.m151(context, r3)) {
                return true;
            }
        }
        return false;
    }
}

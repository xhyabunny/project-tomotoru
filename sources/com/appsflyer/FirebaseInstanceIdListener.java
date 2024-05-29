package com.appsflyer;

import com.appsflyer.b;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIdListener extends FirebaseInstanceIdService {
    /* JADX WARNING: type inference failed for: r5v0, types: [android.content.Context, com.google.firebase.iid.FirebaseInstanceIdService, com.appsflyer.FirebaseInstanceIdListener] */
    public void onTokenRefresh() {
        String str;
        FirebaseInstanceIdListener.super.onTokenRefresh();
        long currentTimeMillis = System.currentTimeMillis();
        try {
            str = FirebaseInstanceId.getInstance().getToken();
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error registering for uninstall tracking", th);
            str = null;
        }
        if (str != null) {
            AFLogger.afInfoLog("Firebase Refreshed Token = ".concat(String.valueOf(str)));
            b.e.C0000b r1 = b.e.C0000b.m111(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
            b.e.C0000b bVar = new b.e.C0000b(currentTimeMillis, str);
            if (r1.m113(bVar)) {
                y.m227(getApplicationContext(), bVar);
            }
        }
    }
}

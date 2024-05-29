package com.appsflyer;

import android.os.Bundle;
import com.appsflyer.b;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmInstanceIdListener extends InstanceIDListenerService {
    /* JADX WARNING: type inference failed for: r7v0, types: [android.content.Context, com.appsflyer.GcmInstanceIdListener, com.google.android.gms.iid.InstanceIDListenerService] */
    public void onTokenRefresh() {
        String str;
        GcmInstanceIdListener.super.onTokenRefresh();
        String string = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            str = InstanceID.getInstance(getApplicationContext()).getToken(string, "GCM", (Bundle) null);
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error registering for uninstall tracking", th);
            str = null;
        }
        if (str != null) {
            AFLogger.afInfoLog("GCM Refreshed Token = ".concat(String.valueOf(str)));
            b.e.C0000b r1 = b.e.C0000b.m111(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
            b.e.C0000b bVar = new b.e.C0000b(currentTimeMillis, str);
            if (r1.m113(bVar)) {
                y.m227(getApplicationContext(), bVar);
            }
        }
    }
}

package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.Map;

public class AFDeepLinkManager {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static AFDeepLinkManager f0 = null;

    /* renamed from: ˋ  reason: contains not printable characters */
    private static Uri f1 = null;

    /* renamed from: ˎ  reason: contains not printable characters */
    private static Uri f2 = null;
    protected int currentActivityHash = -1;

    private AFDeepLinkManager() {
    }

    public static AFDeepLinkManager getInstance() {
        if (f0 == null) {
            f0 = new AFDeepLinkManager();
        }
        return f0;
    }

    /* access modifiers changed from: protected */
    public void processIntentForDeepLink(Intent intent, Context context, Map<String, Object> map) {
        Uri uri;
        if (intent == null || !"android.intent.action.VIEW".equals(intent.getAction())) {
            uri = null;
        } else {
            uri = intent.getData();
        }
        if (uri != null) {
            boolean z = AppsFlyerProperties.getInstance().getBoolean("consumeAfDeepLink", false);
            boolean z2 = (intent.getFlags() & 4194304) == 0;
            if (intent.hasExtra("appsflyer_click_ts") && !z) {
                long longExtra = intent.getLongExtra("appsflyer_click_ts", 0);
                long j = AppsFlyerProperties.getInstance().getLong("appsflyer_click_consumed_ts", 0);
                if (longExtra == 0 || longExtra == j) {
                    AFLogger.afInfoLog(new StringBuilder("skipping re-use of previously consumed deep link: ").append(uri.toString()).append(" w/Ex: ").append(String.valueOf(longExtra)).toString());
                    return;
                }
                AppsFlyerLib.getInstance().handleDeepLinkCallback(context, map, uri);
                AppsFlyerProperties.getInstance().set("appsflyer_click_consumed_ts", longExtra);
            } else if (z || z2) {
                Boolean valueOf = Boolean.valueOf(z2);
                if (f1 == null || !uri.equals(f1)) {
                    AppsFlyerLib.getInstance().handleDeepLinkCallback(context, map, uri);
                    f1 = uri;
                    return;
                }
                AFLogger.afInfoLog(new StringBuilder("skipping re-use of previously consumed deep link: ").append(uri.toString()).append(valueOf.booleanValue() ? " w/sT" : " w/cAPI").toString());
            } else {
                if (this.currentActivityHash != AppsFlyerProperties.getInstance().getInt("lastActivityHash", 0)) {
                    AppsFlyerLib.getInstance().handleDeepLinkCallback(context, map, uri);
                    AppsFlyerProperties.getInstance().set("lastActivityHash", this.currentActivityHash);
                    return;
                }
                AFLogger.afInfoLog(new StringBuilder("skipping re-use of previously consumed deep link: ").append(uri.toString()).append(" w/hC: ").append(String.valueOf(this.currentActivityHash)).toString());
            }
        } else if (f2 != null) {
            AppsFlyerLib.getInstance().handleDeepLinkCallback(context, map, f2);
            AFLogger.afInfoLog(new StringBuilder("using trampoline Intent fallback with URI : ").append(f2.toString()).toString());
            f2 = null;
        } else if (AppsFlyerLib.getInstance().latestDeepLink != null) {
            AppsFlyerLib.getInstance().handleDeepLinkCallback(context, map, AppsFlyerLib.getInstance().latestDeepLink);
        }
    }

    /* access modifiers changed from: protected */
    public void collectIntentsFromActivities(Intent intent) {
        Uri uri = null;
        if (intent != null && "android.intent.action.VIEW".equals(intent.getAction())) {
            uri = intent.getData();
        }
        if (uri != null && intent.getData() != f2) {
            f2 = intent.getData();
        }
    }
}

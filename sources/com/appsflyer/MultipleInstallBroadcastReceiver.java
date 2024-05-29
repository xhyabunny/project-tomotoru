package com.appsflyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class MultipleInstallBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String str;
        if (intent != null) {
            try {
                str = intent.getStringExtra("referrer");
            } catch (Throwable th) {
                AFLogger.afErrorLog("error in BroadcastReceiver ", th);
                str = null;
            }
            if (str != null) {
                if ("AppsFlyer_Test".equals(str) && intent.getStringExtra("TestIntegrationMode") != null) {
                    AppsFlyerLib.getInstance().m74(context, intent);
                    return;
                } else if (context.getSharedPreferences("appsflyer-data", 0).getString("referrer", (String) null) != null) {
                    AppsFlyerLib.getInstance();
                    AppsFlyerLib.m35(context, str);
                    return;
                }
            }
            AFLogger.afInfoLog("MultipleInstallBroadcastReceiver called");
            AppsFlyerLib.getInstance().m74(context, intent);
            for (ResolveInfo next : context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.vending.INSTALL_REFERRER"), 0)) {
                String action = intent.getAction();
                if (next.activityInfo.packageName.equals(context.getPackageName()) && "com.android.vending.INSTALL_REFERRER".equals(action) && !getClass().getName().equals(next.activityInfo.name)) {
                    AFLogger.afInfoLog(new StringBuilder("trigger onReceive: class: ").append(next.activityInfo.name).toString());
                    try {
                        ((BroadcastReceiver) Class.forName(next.activityInfo.name).newInstance()).onReceive(context, intent);
                    } catch (Throwable th2) {
                        AFLogger.afErrorLog(new StringBuilder("error in BroadcastReceiver ").append(next.activityInfo.name).toString(), th2);
                    }
                }
            }
        }
    }
}

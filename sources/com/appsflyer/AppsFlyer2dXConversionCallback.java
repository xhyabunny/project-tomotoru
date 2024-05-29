package com.appsflyer;

import android.support.v4.app.NotificationCompat;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyer2dXConversionCallback implements AppsFlyerConversionListener {
    public native void onAppOpenAttributionNative(Object obj);

    public native void onAttributionFailureNative(Object obj);

    public native void onInstallConversionDataLoadedNative(Object obj);

    public native void onInstallConversionFailureNative(Object obj);

    public void onInstallConversionDataLoaded(Map<String, String> map) {
        onInstallConversionDataLoadedNative(map);
    }

    public void onInstallConversionFailure(String str) {
        m16("onAttributionFailure", str);
    }

    public void onAppOpenAttribution(Map<String, String> map) {
        onAppOpenAttributionNative(map);
    }

    public void onAttributionFailure(String str) {
        m16("onInstallConversionFailure", str);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private void m16(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NotificationCompat.CATEGORY_STATUS, "failure");
            jSONObject.put("data", str2);
            char c = 65535;
            switch (str.hashCode()) {
                case -1390007222:
                    if (str.equals("onAttributionFailure")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1050716216:
                    if (str.equals("onInstallConversionFailure")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    onInstallConversionFailureNative(jSONObject);
                    return;
                case 1:
                    onAttributionFailureNative(jSONObject);
                    return;
                default:
                    return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        e.printStackTrace();
    }
}

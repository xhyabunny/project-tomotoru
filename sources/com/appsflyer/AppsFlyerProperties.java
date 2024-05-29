package com.appsflyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyerProperties {
    public static final String ADDITIONAL_CUSTOM_DATA = "additionalCustomData";
    public static final String AF_KEY = "AppsFlyerKey";
    public static final String AF_WAITFOR_CUSTOMERID = "waitForCustomerId";
    public static final String APP_ID = "appid";
    public static final String APP_USER_ID = "AppUserId";
    public static final String CHANNEL = "channel";
    public static final String COLLECT_ANDROID_ID = "collectAndroidId";
    public static final String COLLECT_ANDROID_ID_FORCE_BY_USER = "collectAndroidIdForceByUser";
    public static final String COLLECT_FACEBOOK_ATTR_ID = "collectFacebookAttrId";
    public static final String COLLECT_FINGER_PRINT = "collectFingerPrint";
    public static final String COLLECT_IMEI = "collectIMEI";
    public static final String COLLECT_IMEI_FORCE_BY_USER = "collectIMEIForceByUser";
    public static final String COLLECT_MAC = "collectMAC";
    public static final String CURRENCY_CODE = "currencyCode";
    public static final String DEVICE_TRACKING_DISABLED = "deviceTrackingDisabled";
    public static final String DISABLE_KEYSTORE = "keyPropDisableAFKeystore";
    public static final String DISABLE_LOGS_COMPLETELY = "disableLogs";
    public static final String DISABLE_OTHER_SDK = "disableOtherSdk";
    public static final String EMAIL_CRYPT_TYPE = "userEmailsCryptType";
    public static final String ENABLE_GPS_FALLBACK = "enableGpsFallback";
    public static final String EXTENSION = "sdkExtension";
    public static final String IS_MONITOR = "shouldMonitor";
    public static final String IS_UPDATE = "IS_UPDATE";
    public static final String LAUNCH_PROTECT_ENABLED = "launchProtectEnabled";
    public static final String ONELINK_DOMAIN = "onelinkDomain";
    public static final String ONELINK_ID = "oneLinkSlug";
    public static final String ONELINK_SCHEME = "onelinkScheme";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_EMAILS = "userEmails";
    public static final String USE_HTTP_FALLBACK = "useHttpFallback";

    /* renamed from: ˊ  reason: contains not printable characters */
    private static AppsFlyerProperties f81 = new AppsFlyerProperties();

    /* renamed from: ʽ  reason: contains not printable characters */
    private boolean f82 = false;

    /* renamed from: ˋ  reason: contains not printable characters */
    private Map<String, Object> f83 = new HashMap();

    /* renamed from: ˎ  reason: contains not printable characters */
    private boolean f84;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f85;

    /* renamed from: ॱ  reason: contains not printable characters */
    private boolean f86;

    public void remove(String str) {
        this.f83.remove(str);
    }

    public enum EmailsCryptType {
        NONE(0),
        SHA1(1),
        MD5(2),
        SHA256(3);
        

        /* renamed from: ˋ  reason: contains not printable characters */
        private final int f88;

        private EmailsCryptType(int i) {
            this.f88 = i;
        }

        public final int getValue() {
            return this.f88;
        }
    }

    private AppsFlyerProperties() {
    }

    public static AppsFlyerProperties getInstance() {
        return f81;
    }

    public void set(String str, String str2) {
        this.f83.put(str, str2);
    }

    public void set(String str, String[] strArr) {
        this.f83.put(str, strArr);
    }

    public void set(String str, int i) {
        this.f83.put(str, Integer.toString(i));
    }

    public void set(String str, long j) {
        this.f83.put(str, Long.toString(j));
    }

    public void set(String str, boolean z) {
        this.f83.put(str, Boolean.toString(z));
    }

    public void setCustomData(String str) {
        this.f83.put(ADDITIONAL_CUSTOM_DATA, str);
    }

    public void setUserEmails(String str) {
        this.f83.put(USER_EMAILS, str);
    }

    public String getString(String str) {
        return (String) this.f83.get(str);
    }

    public boolean getBoolean(String str, boolean z) {
        String string = getString(str);
        return string == null ? z : Boolean.valueOf(string).booleanValue();
    }

    public int getInt(String str, int i) {
        String string = getString(str);
        return string == null ? i : Integer.valueOf(string).intValue();
    }

    public long getLong(String str, long j) {
        String string = getString(str);
        return string == null ? j : Long.valueOf(string).longValue();
    }

    public Object getObject(String str) {
        return this.f83.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean isOnReceiveCalled() {
        return this.f84;
    }

    /* access modifiers changed from: protected */
    public void setOnReceiveCalled() {
        this.f84 = true;
    }

    /* access modifiers changed from: protected */
    public boolean isFirstLaunchCalled() {
        return this.f86;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled(boolean z) {
        this.f86 = z;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled() {
        this.f86 = true;
    }

    /* access modifiers changed from: protected */
    public void setReferrer(String str) {
        set("AF_REFERRER", str);
        this.f85 = str;
    }

    public String getReferrer(Context context) {
        if (this.f85 != null) {
            return this.f85;
        }
        if (getString("AF_REFERRER") != null) {
            return getString("AF_REFERRER");
        }
        if (context != null) {
            return context.getSharedPreferences("appsflyer-data", 0).getString("referrer", (String) null);
        }
        return null;
    }

    public boolean isEnableLog() {
        return getBoolean("shouldLog", true);
    }

    public boolean isLogsDisabledCompletely() {
        return getBoolean(DISABLE_LOGS_COMPLETELY, false);
    }

    public boolean isOtherSdkStringDisabled() {
        return getBoolean(DISABLE_OTHER_SDK, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void saveProperties(SharedPreferences sharedPreferences) {
        String jSONObject = new JSONObject(this.f83).toString();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("savedProperties", jSONObject);
        if (Build.VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    public void loadProperties(Context context) {
        String string;
        if (!this.f82 && (string = context.getSharedPreferences("appsflyer-data", 0).getString("savedProperties", (String) null)) != null) {
            AFLogger.afDebugLog("Loading properties..");
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (this.f83.get(next) == null) {
                        this.f83.put(next, jSONObject.getString(next));
                    }
                }
                this.f82 = true;
            } catch (JSONException e) {
                AFLogger.afErrorLog("Failed loading properties", e);
            }
            AFLogger.afDebugLog(new StringBuilder("Done loading properties: ").append(this.f82).toString());
        }
    }
}

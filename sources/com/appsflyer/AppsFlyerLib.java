package com.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.OneLinkHttpTask;
import com.appsflyer.b;
import com.appsflyer.cache.CacheManager;
import com.appsflyer.cache.RequestCacheData;
import com.appsflyer.i;
import com.appsflyer.j;
import com.appsflyer.o;
import com.appsflyer.s;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.GoogleApiAvailability;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyerLib implements a {
    public static final String AF_PRE_INSTALL_PATH = "AF_PRE_INSTALL_PATH";
    public static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    public static final String ATTRIBUTION_ID_CONTENT_URI = "content://com.facebook.katana.provider.AttributionIdProvider";
    public static final String IS_STOP_TRACKING_USED = "is_stop_tracking_used";
    public static final String LOG_TAG = "AppsFlyer_4.8.18";
    public static final String PRE_INSTALL_SYSTEM_DEFAULT = "/data/local/tmp/pre_install.appsflyer";
    public static final String PRE_INSTALL_SYSTEM_DEFAULT_ETC = "/etc/pre_install.appsflyer";
    public static final String PRE_INSTALL_SYSTEM_RO_PROP = "ro.appsflyer.preinstall.path";

    /* renamed from: ʼ  reason: contains not printable characters */
    private static final String f15 = new StringBuilder().append(f19).append("/androidevent?buildnumber=4.8.18&app_id=").toString();

    /* renamed from: ʼॱ  reason: contains not printable characters */
    private static AppsFlyerLib f16 = new AppsFlyerLib();

    /* renamed from: ʽ  reason: contains not printable characters */
    private static String f17 = new StringBuilder("https://events.%s/api/v").append(f15).toString();

    /* renamed from: ˊ  reason: contains not printable characters */
    static AppsFlyerInAppPurchaseValidatorListener f18 = null;

    /* renamed from: ˋ  reason: contains not printable characters */
    static final String f19 = BuildConfig.VERSION_NAME.substring(0, BuildConfig.VERSION_NAME.indexOf("."));

    /* renamed from: ˎ  reason: contains not printable characters */
    static final String f20 = new StringBuilder("https://register.%s/api/v").append(f15).toString();
    /* access modifiers changed from: private */

    /* renamed from: ˏॱ  reason: contains not printable characters */
    public static AppsFlyerConversionListener f21 = null;
    /* access modifiers changed from: private */

    /* renamed from: ͺ  reason: contains not printable characters */
    public static final List<String> f22 = Arrays.asList(new String[]{"googleplay", "playstore", "googleplaystore"});

    /* renamed from: ॱˊ  reason: contains not printable characters */
    private static final List<String> f23 = Arrays.asList(new String[]{"is_cache"});

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static String f24 = new StringBuilder("https://t.%s/api/v").append(f15).toString();

    /* renamed from: ᐝ  reason: contains not printable characters */
    private static String f25 = new StringBuilder("https://attr.%s/api/v").append(f15).toString();

    /* renamed from: ᐝॱ  reason: contains not printable characters */
    private static AppsFlyerTrackingRequestListener f26 = null;
    protected Uri latestDeepLink = null;

    /* renamed from: ʻ  reason: contains not printable characters */
    private long f27 = -1;
    /* access modifiers changed from: private */

    /* renamed from: ʻॱ  reason: contains not printable characters */
    public boolean f28 = false;

    /* renamed from: ʽॱ  reason: contains not printable characters */
    private long f29;

    /* renamed from: ʾ  reason: contains not printable characters */
    private long f30;
    /* access modifiers changed from: private */

    /* renamed from: ʿ  reason: contains not printable characters */
    public ScheduledExecutorService f31 = null;

    /* renamed from: ˈ  reason: contains not printable characters */
    private o.d f32;

    /* renamed from: ˉ  reason: contains not printable characters */
    private boolean f33 = false;

    /* renamed from: ˊˊ  reason: contains not printable characters */
    private Map<Long, String> f34;

    /* renamed from: ˊˋ  reason: contains not printable characters */
    private String f35;

    /* renamed from: ˊॱ  reason: contains not printable characters */
    private long f36 = -1;

    /* renamed from: ˊᐝ  reason: contains not printable characters */
    private long f37;

    /* renamed from: ˋˊ  reason: contains not printable characters */
    private boolean f38 = false;

    /* renamed from: ˋˋ  reason: contains not printable characters */
    private q f39 = new q();

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private long f40 = TimeUnit.SECONDS.toMillis(5);

    /* renamed from: ˋᐝ  reason: contains not printable characters */
    private boolean f41;

    /* renamed from: ˌ  reason: contains not printable characters */
    private boolean f42;

    /* renamed from: ˍ  reason: contains not printable characters */
    private boolean f43 = false;

    /* renamed from: ˎˎ  reason: contains not printable characters */
    private boolean f44 = false;

    /* renamed from: ˏ  reason: contains not printable characters */
    String f45;

    /* renamed from: ॱ  reason: contains not printable characters */
    String f46;
    /* access modifiers changed from: private */

    /* renamed from: ॱˋ  reason: contains not printable characters */
    public Map<String, String> f47;

    /* renamed from: ॱˎ  reason: contains not printable characters */
    private d f48 = null;
    /* access modifiers changed from: private */

    /* renamed from: ॱᐝ  reason: contains not printable characters */
    public long f49;

    /* renamed from: ˏ  reason: contains not printable characters */
    static /* synthetic */ void m54(AppsFlyerLib appsFlyerLib, String str, String str2, String str3, WeakReference weakReference, String str4, boolean z) throws IOException {
        URL url = new URL(str);
        AFLogger.afInfoLog(new StringBuilder("url: ").append(url.toString()).toString());
        j.AnonymousClass2.m152("data: ".concat(String.valueOf(str2)));
        m64((Context) weakReference.get(), LOG_TAG, "EVENT_DATA", str2);
        try {
            appsFlyerLib.m26(url, str2, str3, weakReference, str4, z);
        } catch (IOException e2) {
            AFLogger.afErrorLog("Exception in sendRequestToServer. ", e2);
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.USE_HTTP_FALLBACK, false)) {
                appsFlyerLib.m26(new URL(str.replace("https:", "http:")), str2, str3, weakReference, str4, z);
                return;
            }
            AFLogger.afInfoLog(new StringBuilder("failed to send requeset to server. ").append(e2.getLocalizedMessage()).toString());
            m64((Context) weakReference.get(), LOG_TAG, "ERROR", e2.getLocalizedMessage());
            throw e2;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    static /* synthetic */ boolean m38(AppsFlyerLib appsFlyerLib) {
        return appsFlyerLib.f47 != null && appsFlyerLib.f47.size() > 0;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static /* synthetic */ void m45(AppsFlyerLib appsFlyerLib, Context context, String str, String str2, String str3, String str4, boolean z, boolean z2, Intent intent) {
        if (context == null) {
            AFLogger.afDebugLog("sendTrackingWithEvent - got null context. skipping event/launch.");
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("appsflyer-data", 0);
        AppsFlyerProperties.getInstance().saveProperties(sharedPreferences);
        if (!appsFlyerLib.isTrackingStopped()) {
            AFLogger.afInfoLog(new StringBuilder("sendTrackingWithEvent from activity: ").append(context.getClass().getName()).toString());
        }
        boolean z3 = str2 == null;
        Map<String, Object> r6 = appsFlyerLib.m72(context, str, str2, str3, str4, z, sharedPreferences, z3, intent);
        String str5 = (String) r6.get("appsflyerKey");
        if (str5 == null || str5.length() == 0) {
            AFLogger.afDebugLog("Not sending data yet, waiting for dev key");
            return;
        }
        if (!appsFlyerLib.isTrackingStopped()) {
            AFLogger.afInfoLog("AppsFlyerLib.sendTrackingWithEvent");
        }
        String obj = new StringBuilder().append(z3 ? z2 ? ServerConfigHandler.getUrl(f25) : ServerConfigHandler.getUrl(f24) : ServerConfigHandler.getUrl(f17)).append(context.getPackageName()).toString();
        int r9 = m48(sharedPreferences, "appsFlyerCount", false);
        if (!(AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.COLLECT_ANDROID_ID_FORCE_BY_USER, false) || AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.COLLECT_IMEI_FORCE_BY_USER, false)) && r6.get(ServerParameters.ADVERTISING_ID_PARAM) != null) {
            try {
                if (TextUtils.isEmpty(appsFlyerLib.f46) && r6.remove("android_id") != null) {
                    AFLogger.afInfoLog("validateGaidAndIMEI :: removing: android_id");
                }
                if (TextUtils.isEmpty(appsFlyerLib.f45) && r6.remove("imei") != null) {
                    AFLogger.afInfoLog("validateGaidAndIMEI :: removing: imei");
                }
            } catch (Exception e2) {
                AFLogger.afErrorLog("failed to remove IMEI or AndroidID key from params; ", e2);
            }
        }
        e eVar = new e(appsFlyerLib, obj, r6, context.getApplicationContext(), z3, r9, (byte) 0);
        if (z3 && m56(context)) {
            if (!(appsFlyerLib.f47 != null && appsFlyerLib.f47.size() > 0)) {
                AFLogger.afDebugLog("Failed to get new referrer, wait ...");
                m66((ScheduledExecutorService) AFExecutor.getInstance().m2(), (Runnable) eVar, 500, TimeUnit.MILLISECONDS);
                return;
            }
        }
        eVar.run();
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static /* synthetic */ void m65(Map map) {
        if (f21 != null) {
            try {
                f21.onAppOpenAttribution(map);
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getLocalizedMessage(), th);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m73() {
        this.f30 = System.currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m70() {
        this.f29 = System.currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m74(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(AppsFlyerProperties.IS_MONITOR);
        if (stringExtra != null) {
            AFLogger.afInfoLog("Turning on monitoring.");
            AppsFlyerProperties.getInstance().set(AppsFlyerProperties.IS_MONITOR, stringExtra.equals("true"));
            m64(context, (String) null, "START_TRACKING", context.getPackageName());
            return;
        }
        AFLogger.afInfoLog("****** onReceive called *******");
        AppsFlyerProperties.getInstance().setOnReceiveCalled();
        String stringExtra2 = intent.getStringExtra("referrer");
        AFLogger.afInfoLog("Play store referrer: ".concat(String.valueOf(stringExtra2)));
        if (stringExtra2 != null) {
            if ("AppsFlyer_Test".equals(intent.getStringExtra("TestIntegrationMode"))) {
                SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
                edit.clear();
                if (Build.VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
                AppsFlyerProperties.getInstance().setFirstLaunchCalled(false);
                AFLogger.afInfoLog("Test mode started..");
                this.f37 = System.currentTimeMillis();
            }
            SharedPreferences.Editor edit2 = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit2.putString("referrer", stringExtra2);
            if (Build.VERSION.SDK_INT >= 9) {
                edit2.apply();
            } else {
                edit2.commit();
            }
            AppsFlyerProperties.getInstance().setReferrer(stringExtra2);
            if (AppsFlyerProperties.getInstance().isFirstLaunchCalled()) {
                AFLogger.afInfoLog("onReceive: isLaunchCalled");
                if (stringExtra2 != null && stringExtra2.length() > 5) {
                    ScheduledThreadPoolExecutor r7 = AFExecutor.getInstance().m2();
                    m66((ScheduledExecutorService) r7, (Runnable) new c(this, new WeakReference(context.getApplicationContext()), (String) null, (String) null, (String) null, stringExtra2, r7, true, intent, (byte) 0), 5, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private static void m46(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                JSONArray jSONArray = new JSONArray((String) jSONObject.get(keys.next()));
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(Long.valueOf(jSONArray.getLong(i)));
                }
            } catch (JSONException e2) {
            }
        }
        Collections.sort(arrayList);
        Iterator<String> keys2 = jSONObject.keys();
        String str = null;
        while (keys2.hasNext() && str == null) {
            String next = keys2.next();
            try {
                JSONArray jSONArray2 = new JSONArray((String) jSONObject.get(next));
                int i2 = 0;
                while (true) {
                    if (i2 >= jSONArray2.length()) {
                        break;
                    } else if (jSONArray2.getLong(i2) == ((Long) arrayList.get(0)).longValue() || jSONArray2.getLong(i2) == ((Long) arrayList.get(1)).longValue() || jSONArray2.getLong(i2) == ((Long) arrayList.get(arrayList.size() - 1)).longValue()) {
                        str = null;
                    } else {
                        i2++;
                        str = next;
                    }
                }
            } catch (JSONException e3) {
                str = str;
            }
        }
        if (str != null) {
            jSONObject.remove(str);
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    static void m35(Context context, String str) {
        JSONArray jSONArray;
        JSONObject jSONObject;
        AFLogger.afDebugLog("received a new (extra) referrer: ".concat(String.valueOf(str)));
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String string = context.getSharedPreferences("appsflyer-data", 0).getString("extraReferrers", (String) null);
            if (string == null) {
                jSONObject = new JSONObject();
                jSONArray = new JSONArray();
            } else {
                JSONObject jSONObject2 = new JSONObject(string);
                if (jSONObject2.has(str)) {
                    jSONArray = new JSONArray((String) jSONObject2.get(str));
                    jSONObject = jSONObject2;
                } else {
                    jSONArray = new JSONArray();
                    jSONObject = jSONObject2;
                }
            }
            if (((long) jSONArray.length()) < 5) {
                jSONArray.put(currentTimeMillis);
            }
            if (((long) jSONObject.length()) >= 4) {
                m46(jSONObject);
            }
            jSONObject.put(str, jSONArray.toString());
            String jSONObject3 = jSONObject.toString();
            SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit.putString("extraReferrers", jSONObject3);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (JSONException e2) {
        } catch (Throwable th) {
            AFLogger.afErrorLog(new StringBuilder("Couldn't save referrer - ").append(str).append(": ").toString(), th);
        }
    }

    private AppsFlyerLib() {
        AFVersionDeclaration.init();
    }

    public static AppsFlyerLib getInstance() {
        return f16;
    }

    public void stopTracking(boolean z, Context context) {
        this.f44 = z;
        CacheManager.getInstance().clearCache(context);
        if (this.f44) {
            SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
            edit.putBoolean(IS_STOP_TRACKING_USED, true);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public String getSdkVersion() {
        C0001r.m185().m193("getSdkVersion", new String[0]);
        return "version: 4.8.18 (build 413)";
    }

    public void onPause(Context context) {
        i.b.m142(context);
        j r0 = j.m147(context);
        r0.f157.post(r0.f152);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private void m23(Application application) {
        AppsFlyerProperties.getInstance().loadProperties(application.getApplicationContext());
        if (Build.VERSION.SDK_INT < 14) {
            AFLogger.afInfoLog("SDK<14 call trackEvent manually");
            AFLogger.afInfoLog("onBecameForeground");
            getInstance().f30 = System.currentTimeMillis();
            getInstance().m76((Context) application, (String) null, (Map<String, Object>) null);
            AFLogger.resetDeltaTime();
        } else if (Build.VERSION.SDK_INT >= 14 && this.f32 == null) {
            o.m163();
            this.f32 = new o.d() {
                /* renamed from: ˊ  reason: contains not printable characters */
                public final void m80(Activity activity) {
                    if (2 > AppsFlyerLib.m29(AppsFlyerLib.m57((Context) activity))) {
                        j r0 = j.m147(activity);
                        r0.f157.post(r0.f152);
                        r0.f157.post(r0.f160);
                    }
                    AFLogger.afInfoLog("onBecameForeground");
                    AppsFlyerLib.getInstance().m73();
                    AppsFlyerLib.getInstance().m76((Context) activity, (String) null, (Map<String, Object>) null);
                    AFLogger.resetDeltaTime();
                }

                /* renamed from: ˊ  reason: contains not printable characters */
                public final void m81(WeakReference<Context> weakReference) {
                    i.b.m142(weakReference.get());
                    j r0 = j.m147(weakReference.get());
                    r0.f157.post(r0.f152);
                }
            };
            o.m166().m169(application, this.f32);
        }
    }

    @Deprecated
    public void setGCMProjectID(String str) {
        C0001r.m185().m193("setGCMProjectID", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please follow the documentation.");
        enableUninstallTracking(str);
    }

    @Deprecated
    public void setGCMProjectNumber(String str) {
        C0001r.m185().m193("setGCMProjectNumber", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please follow the documentation.");
        enableUninstallTracking(str);
    }

    @Deprecated
    public void setGCMProjectNumber(Context context, String str) {
        C0001r.m185().m193("setGCMProjectNumber", str);
        AFLogger.afWarnLog("Method 'setGCMProjectNumber' is deprecated. Please use 'enableUninstallTracking'.");
        enableUninstallTracking(str);
    }

    public void enableUninstallTracking(String str) {
        C0001r.m185().m193("enableUninstallTracking", str);
        AppsFlyerProperties.getInstance().set("gcmProjectNumber", str);
    }

    public void updateServerUninstallToken(Context context, String str) {
        if (str != null) {
            y.m227(context, new b.e.C0000b(str));
        }
    }

    public void setDebugLog(boolean z) {
        AFLogger.LogLevel logLevel;
        C0001r.m185().m193("setDebugLog", String.valueOf(z));
        AppsFlyerProperties.getInstance().set("shouldLog", z);
        AppsFlyerProperties instance = AppsFlyerProperties.getInstance();
        if (z) {
            logLevel = AFLogger.LogLevel.DEBUG;
        } else {
            logLevel = AFLogger.LogLevel.NONE;
        }
        instance.set("logLevel", logLevel.getLevel());
    }

    public void setImeiData(String str) {
        C0001r.m185().m193("setImeiData", str);
        this.f45 = str;
    }

    public void setAndroidIdData(String str) {
        C0001r.m185().m193("setAndroidIdData", str);
        this.f46 = str;
    }

    public AppsFlyerLib enableLocationCollection(boolean z) {
        this.f33 = z;
        return this;
    }

    @Deprecated
    public void setAppUserId(String str) {
        C0001r.m185().m193("setAppUserId", str);
        setCustomerUserId(str);
    }

    public void setCustomerUserId(String str) {
        C0001r.m185().m193("setCustomerUserId", str);
        AFLogger.afInfoLog("setCustomerUserId = ".concat(String.valueOf(str)));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.APP_USER_ID, str);
    }

    public void waitForCustomerUserId(boolean z) {
        AFLogger.afInfoLog("initAfterCustomerUserID: ".concat(String.valueOf(z)), true);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, z);
    }

    public void setCustomerIdAndTrack(String str, @NonNull Context context) {
        boolean z = false;
        if (context != null) {
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false) && AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID) == null) {
                z = true;
            }
            if (z) {
                setCustomerUserId(str);
                AFLogger.afInfoLog(new StringBuilder("CustomerUserId set: ").append(str).append(" - Initializing AppsFlyer Tacking").toString(), true);
                String referrer = AppsFlyerProperties.getInstance().getReferrer(context);
                String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
                if (referrer == null) {
                    referrer = "";
                }
                m37(context, string, (String) null, (String) null, referrer, context instanceof Activity ? ((Activity) context).getIntent() : null);
                if (AppsFlyerProperties.getInstance().getString("afUninstallToken") != null) {
                    m75(context, AppsFlyerProperties.getInstance().getString("afUninstallToken"));
                    return;
                }
                return;
            }
            setCustomerUserId(str);
            AFLogger.afInfoLog("waitForCustomerUserId is false; setting CustomerUserID: ".concat(String.valueOf(str)), true);
        }
    }

    public String getOutOfStore(Context context) {
        String string = AppsFlyerProperties.getInstance().getString("api_store_value");
        if (string != null) {
            return string;
        }
        String r0 = m61((WeakReference<Context>) new WeakReference(context), "AF_STORE");
        if (r0 != null) {
            return r0;
        }
        AFLogger.afInfoLog("No out-of-store value set");
        return null;
    }

    public void setOutOfStore(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase();
            AppsFlyerProperties.getInstance().set("api_store_value", lowerCase);
            AFLogger.afInfoLog("Store API set with value: ".concat(String.valueOf(lowerCase)), true);
            return;
        }
        AFLogger.m11("Cannot set setOutOfStore with null");
    }

    public void setAppInviteOneLink(String str) {
        C0001r.m185().m193("setAppInviteOneLink", str);
        AFLogger.afInfoLog("setAppInviteOneLink = ".concat(String.valueOf(str)));
        if (str == null || !str.equals(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID))) {
            AppsFlyerProperties.getInstance().remove(AppsFlyerProperties.ONELINK_DOMAIN);
            AppsFlyerProperties.getInstance().remove("onelinkVersion");
            AppsFlyerProperties.getInstance().remove(AppsFlyerProperties.ONELINK_SCHEME);
        }
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.ONELINK_ID, str);
    }

    public void setAdditionalData(HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            C0001r.m185().m193("setAdditionalData", hashMap.toString());
            AppsFlyerProperties.getInstance().setCustomData(new JSONObject(hashMap).toString());
        }
    }

    public void sendDeepLinkData(Activity activity) {
        if (activity != null && activity.getIntent() != null) {
            C0001r.m185().m193("sendDeepLinkData", activity.getLocalClassName(), new StringBuilder("activity_intent_").append(activity.getIntent().toString()).toString());
        } else if (activity != null) {
            C0001r.m185().m193("sendDeepLinkData", activity.getLocalClassName(), "activity_intent_null");
        } else {
            C0001r.m185().m193("sendDeepLinkData", "activity_null");
        }
        try {
            m23(activity.getApplication());
            AFLogger.afInfoLog(new StringBuilder("getDeepLinkData with activity ").append(activity.getIntent().getDataString()).toString());
        } catch (Exception e2) {
            AFLogger.afInfoLog("getDeepLinkData Exception: ".concat(String.valueOf(e2)));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendPushNotificationData(android.app.Activity r14) {
        /*
            r13 = this;
            r1 = 0
            r12 = 2
            r6 = 1
            r5 = 0
            if (r14 == 0) goto L_0x00cf
            android.content.Intent r0 = r14.getIntent()
            if (r0 == 0) goto L_0x00cf
            com.appsflyer.r r0 = com.appsflyer.C0001r.m185()
            java.lang.String r2 = "sendPushNotificationData"
            java.lang.String[] r3 = new java.lang.String[r12]
            java.lang.String r4 = r14.getLocalClassName()
            r3[r5] = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "activity_intent_"
            r4.<init>(r5)
            android.content.Intent r5 = r14.getIntent()
            java.lang.String r5 = r5.toString()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3[r6] = r4
            r0.m193((java.lang.String) r2, (java.lang.String[]) r3)
        L_0x0036:
            boolean r0 = r14 instanceof android.app.Activity
            if (r0 == 0) goto L_0x01aa
            r0 = r14
            android.app.Activity r0 = (android.app.Activity) r0
            android.content.Intent r2 = r0.getIntent()
            if (r2 == 0) goto L_0x01aa
            android.os.Bundle r3 = r2.getExtras()
            if (r3 == 0) goto L_0x01aa
            java.lang.String r0 = "af"
            java.lang.String r1 = r3.getString(r0)
            if (r1 == 0) goto L_0x006d
            java.lang.String r0 = "Push Notification received af payload = "
            java.lang.String r4 = java.lang.String.valueOf(r1)
            java.lang.String r0 = r0.concat(r4)
            com.appsflyer.AFLogger.afInfoLog(r0)
            java.lang.String r0 = "af"
            r3.remove(r0)
            r0 = r14
            android.app.Activity r0 = (android.app.Activity) r0
            android.content.Intent r2 = r2.putExtras(r3)
            r0.setIntent(r2)
        L_0x006d:
            r0 = r1
        L_0x006e:
            r13.f35 = r0
            java.lang.String r0 = r13.f35
            if (r0 == 0) goto L_0x00ce
            long r4 = java.lang.System.currentTimeMillis()
            java.util.Map<java.lang.Long, java.lang.String> r0 = r13.f34
            if (r0 != 0) goto L_0x00f9
            java.lang.String r0 = "pushes: initializing pushes history.."
            com.appsflyer.AFLogger.afInfoLog(r0)
            java.util.concurrent.ConcurrentHashMap r0 = new java.util.concurrent.ConcurrentHashMap
            r0.<init>()
            r13.f34 = r0
            r2 = r4
        L_0x0089:
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r1 = "pushPayloadHistorySize"
            int r0 = r0.getInt(r1, r12)
            java.util.Map<java.lang.Long, java.lang.String> r1 = r13.f34
            int r1 = r1.size()
            if (r1 != r0) goto L_0x00bc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "pushes: removing oldest overflowing push (oldest push:"
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r1 = ")"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.appsflyer.AFLogger.afInfoLog(r0)
            java.util.Map<java.lang.Long, java.lang.String> r0 = r13.f34
            java.lang.Long r1 = java.lang.Long.valueOf(r2)
            r0.remove(r1)
        L_0x00bc:
            java.util.Map<java.lang.Long, java.lang.String> r0 = r13.f34
            java.lang.Long r1 = java.lang.Long.valueOf(r4)
            java.lang.String r2 = r13.f35
            r0.put(r1, r2)
            android.app.Application r0 = r14.getApplication()
            r13.m23((android.app.Application) r0)
        L_0x00ce:
            return
        L_0x00cf:
            if (r14 == 0) goto L_0x00e8
            com.appsflyer.r r0 = com.appsflyer.C0001r.m185()
            java.lang.String r2 = "sendPushNotificationData"
            java.lang.String[] r3 = new java.lang.String[r12]
            java.lang.String r4 = r14.getLocalClassName()
            r3[r5] = r4
            java.lang.String r4 = "activity_intent_null"
            r3[r6] = r4
            r0.m193((java.lang.String) r2, (java.lang.String[]) r3)
            goto L_0x0036
        L_0x00e8:
            com.appsflyer.r r0 = com.appsflyer.C0001r.m185()
            java.lang.String r2 = "sendPushNotificationData"
            java.lang.String[] r3 = new java.lang.String[r6]
            java.lang.String r4 = "activity_null"
            r3[r5] = r4
            r0.m193((java.lang.String) r2, (java.lang.String[]) r3)
            goto L_0x0036
        L_0x00f9:
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x01a5 }
            java.lang.String r1 = "pushPayloadMaxAging"
            r2 = 1800000(0x1b7740, double:8.89318E-318)
            long r6 = r0.getLong(r1, r2)     // Catch:{ Throwable -> 0x01a5 }
            java.util.Map<java.lang.Long, java.lang.String> r0 = r13.f34     // Catch:{ Throwable -> 0x01a5 }
            java.util.Set r0 = r0.keySet()     // Catch:{ Throwable -> 0x01a5 }
            java.util.Iterator r8 = r0.iterator()     // Catch:{ Throwable -> 0x01a5 }
            r2 = r4
        L_0x0111:
            boolean r0 = r8.hasNext()     // Catch:{ Throwable -> 0x016a }
            if (r0 == 0) goto L_0x0089
            java.lang.Object r0 = r8.next()     // Catch:{ Throwable -> 0x016a }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ Throwable -> 0x016a }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = r13.f35     // Catch:{ Throwable -> 0x016a }
            r9.<init>(r1)     // Catch:{ Throwable -> 0x016a }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Throwable -> 0x016a }
            java.util.Map<java.lang.Long, java.lang.String> r1 = r13.f34     // Catch:{ Throwable -> 0x016a }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x016a }
            r10.<init>(r1)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = "pid"
            java.lang.Object r1 = r9.get(r1)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r11 = "pid"
            java.lang.Object r11 = r10.get(r11)     // Catch:{ Throwable -> 0x016a }
            boolean r1 = r1.equals(r11)     // Catch:{ Throwable -> 0x016a }
            if (r1 == 0) goto L_0x0187
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = "PushNotificationMeasurement: A previous payload with same PID was already acknowledged! (old: "
            r0.<init>(r1)     // Catch:{ Throwable -> 0x016a }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = ", new: "
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x016a }
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r1 = ")"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x016a }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x016a }
            com.appsflyer.AFLogger.afInfoLog(r0)     // Catch:{ Throwable -> 0x016a }
            r0 = 0
            r13.f35 = r0     // Catch:{ Throwable -> 0x016a }
            goto L_0x00ce
        L_0x016a:
            r0 = move-exception
        L_0x016b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r6 = "Error while handling push notification measurement: "
            r1.<init>(r6)
            java.lang.Class r6 = r0.getClass()
            java.lang.String r6 = r6.getSimpleName()
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.appsflyer.AFLogger.afErrorLog(r1, r0)
            goto L_0x0089
        L_0x0187:
            long r10 = r0.longValue()     // Catch:{ Throwable -> 0x016a }
            long r10 = r4 - r10
            int r1 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x0196
            java.util.Map<java.lang.Long, java.lang.String> r1 = r13.f34     // Catch:{ Throwable -> 0x016a }
            r1.remove(r0)     // Catch:{ Throwable -> 0x016a }
        L_0x0196:
            long r10 = r0.longValue()     // Catch:{ Throwable -> 0x016a }
            int r1 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x01a8
            long r0 = r0.longValue()     // Catch:{ Throwable -> 0x016a }
        L_0x01a2:
            r2 = r0
            goto L_0x0111
        L_0x01a5:
            r0 = move-exception
            r2 = r4
            goto L_0x016b
        L_0x01a8:
            r0 = r2
            goto L_0x01a2
        L_0x01aa:
            r0 = r1
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.sendPushNotificationData(android.app.Activity):void");
    }

    @Deprecated
    public void setUserEmail(String str) {
        C0001r.m185().m193("setUserEmail", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.USER_EMAIL, str);
    }

    public void setUserEmails(String... strArr) {
        C0001r.m185().m193("setUserEmails", strArr);
        setUserEmails(AppsFlyerProperties.EmailsCryptType.NONE, strArr);
    }

    public void setUserEmails(AppsFlyerProperties.EmailsCryptType emailsCryptType, String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length + 1);
        arrayList.add(emailsCryptType.toString());
        arrayList.addAll(Arrays.asList(strArr));
        C0001r.m185().m193("setUserEmails", (String[]) arrayList.toArray(new String[(strArr.length + 1)]));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.EMAIL_CRYPT_TYPE, emailsCryptType.getValue());
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        Object obj = null;
        for (String str : strArr) {
            switch (AnonymousClass4.f54[emailsCryptType.ordinal()]) {
                case 2:
                    obj = "md5_el_arr";
                    arrayList2.add(t.m220(str));
                    break;
                case 3:
                    obj = "sha256_el_arr";
                    arrayList2.add(t.m219(str));
                    break;
                case 4:
                    obj = "plain_el_arr";
                    arrayList2.add(str);
                    break;
                default:
                    obj = "sha1_el_arr";
                    arrayList2.add(t.m221(str));
                    break;
            }
        }
        hashMap.put(obj, arrayList2);
        AppsFlyerProperties.getInstance().setUserEmails(new JSONObject(hashMap).toString());
    }

    /* renamed from: com.appsflyer.AppsFlyerLib$4  reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {

        /* renamed from: ˎ  reason: contains not printable characters */
        static final /* synthetic */ int[] f54 = new int[AppsFlyerProperties.EmailsCryptType.values().length];

        static {
            try {
                f54[AppsFlyerProperties.EmailsCryptType.SHA1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f54[AppsFlyerProperties.EmailsCryptType.MD5.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f54[AppsFlyerProperties.EmailsCryptType.SHA256.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f54[AppsFlyerProperties.EmailsCryptType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void setCollectAndroidID(boolean z) {
        C0001r.m185().m193("setCollectAndroidID", String.valueOf(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_ANDROID_ID, Boolean.toString(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_ANDROID_ID_FORCE_BY_USER, Boolean.toString(z));
    }

    public void setCollectIMEI(boolean z) {
        C0001r.m185().m193("setCollectIMEI", String.valueOf(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_IMEI, Boolean.toString(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_IMEI_FORCE_BY_USER, Boolean.toString(z));
    }

    @Deprecated
    public void setCollectFingerPrint(boolean z) {
        C0001r.m185().m193("setCollectFingerPrint", String.valueOf(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.COLLECT_FINGER_PRINT, Boolean.toString(z));
    }

    public AppsFlyerLib init(String str, AppsFlyerConversionListener appsFlyerConversionListener) {
        C0001r r1 = C0001r.m185();
        String[] strArr = new String[2];
        strArr[0] = str;
        strArr[1] = appsFlyerConversionListener == null ? "null" : "conversionDataListener";
        r1.m193("init", strArr);
        AFLogger.m14(String.format("Initializing AppsFlyer SDK: (v%s.%s)", new Object[]{BuildConfig.VERSION_NAME, "413"}));
        this.f41 = true;
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_KEY, str);
        j.AnonymousClass2.m153(str);
        f21 = appsFlyerConversionListener;
        return this;
    }

    public AppsFlyerLib init(String str, AppsFlyerConversionListener appsFlyerConversionListener, Context context) {
        if (context != null && m56(context)) {
            if (this.f48 == null) {
                this.f48 = new d();
                this.f48.m117(context, this);
            } else {
                AFLogger.afWarnLog("AFInstallReferrer instance already created");
            }
        }
        return init(str, appsFlyerConversionListener);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static boolean m56(@NonNull Context context) {
        if (m48(context.getSharedPreferences("appsflyer-data", 0), "appsFlyerCount", false) > 2) {
            AFLogger.afRDLog("Install referrer will not load, the counter > 2, ");
            return false;
        }
        try {
            Class.forName("com.android.installreferrer.api.InstallReferrerClient");
            if (j.AnonymousClass1.m151(context, "com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE")) {
                AFLogger.afDebugLog("Install referrer is allowed");
                return true;
            }
            AFLogger.afDebugLog("Install referrer is not allowed");
            return false;
        } catch (ClassNotFoundException e2) {
            AFLogger.afRDLog("Class com.android.installreferrer.api.InstallReferrerClient not found");
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest : com.android.installreferrer.api.InstallReferrerClient", th);
            return false;
        }
    }

    public void startTracking(Application application) {
        if (!this.f41) {
            AFLogger.afWarnLog("ERROR: AppsFlyer SDK is not initialized! The API call 'startTracking(Application)' must be called after the 'init(String, AppsFlyerConversionListener)' API method, which should be called on the Application's onCreate.");
        } else {
            startTracking(application, (String) null);
        }
    }

    public void startTracking(Application application, String str) {
        startTracking(application, str, (AppsFlyerTrackingRequestListener) null);
    }

    public void startTracking(Application application, String str, AppsFlyerTrackingRequestListener appsFlyerTrackingRequestListener) {
        C0001r.m185().m193("startTracking", str);
        AFLogger.afInfoLog(String.format("Starting AppsFlyer Tracking: (v%s.%s)", new Object[]{BuildConfig.VERSION_NAME, "413"}));
        AFLogger.afInfoLog("Build Number: 413");
        AppsFlyerProperties.getInstance().loadProperties(application.getApplicationContext());
        if (!TextUtils.isEmpty(str)) {
            AppsFlyerProperties.getInstance().set(AppsFlyerProperties.AF_KEY, str);
            j.AnonymousClass2.m153(str);
        } else if (TextUtils.isEmpty(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY))) {
            AFLogger.afWarnLog("ERROR: AppsFlyer SDK is not initialized! You must provide AppsFlyer Dev-Key either in the 'init' API method (should be called on Application's onCreate),or in the startTracking API method (should be called on Activity's onCreate).");
            return;
        }
        if (appsFlyerTrackingRequestListener != null) {
            f26 = appsFlyerTrackingRequestListener;
        }
        m23(application);
    }

    public void setAppId(String str) {
        C0001r.m185().m193("setAppId", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.APP_ID, str);
    }

    public void setExtension(String str) {
        C0001r.m185().m193("setExtension", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.EXTENSION, str);
    }

    public void setIsUpdate(boolean z) {
        C0001r.m185().m193("setIsUpdate", String.valueOf(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.IS_UPDATE, z);
    }

    public void setCurrencyCode(String str) {
        C0001r.m185().m193("setCurrencyCode", str);
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.CURRENCY_CODE, str);
    }

    public void trackLocation(Context context, double d2, double d3) {
        C0001r.m185().m193("trackLocation", String.valueOf(d2), String.valueOf(d3));
        HashMap hashMap = new HashMap();
        hashMap.put(AFInAppEventParameterName.LONGTITUDE, Double.toString(d3));
        hashMap.put(AFInAppEventParameterName.LATITUDE, Double.toString(d2));
        m76(context, AFInAppEventType.LOCATION_COORDINATES, (Map<String, Object>) hashMap);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m71(WeakReference<Context> weakReference) {
        if (weakReference.get() != null) {
            AFLogger.afInfoLog("app went to background");
            SharedPreferences sharedPreferences = weakReference.get().getSharedPreferences("appsflyer-data", 0);
            AppsFlyerProperties.getInstance().saveProperties(sharedPreferences);
            long j = this.f29 - this.f30;
            HashMap hashMap = new HashMap();
            String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
            if (string == null) {
                AFLogger.afWarnLog("[callStats] AppsFlyer's SDK cannot send any event without providing DevKey.");
                return;
            }
            String string2 = AppsFlyerProperties.getInstance().getString("KSAppsFlyerId");
            if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, false)) {
                hashMap.put(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, "true");
            }
            k r0 = n.m162(weakReference.get().getContentResolver());
            if (r0 != null) {
                hashMap.put("amazon_aid", r0.m155());
                hashMap.put("amazon_aid_limit", String.valueOf(r0.m154()));
            }
            String string3 = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
            if (string3 != null) {
                hashMap.put(ServerParameters.ADVERTISING_ID_PARAM, string3);
            }
            hashMap.put("app_id", weakReference.get().getPackageName());
            hashMap.put("devkey", string);
            hashMap.put("uid", p.m174(weakReference));
            hashMap.put("time_in_app", String.valueOf(j / 1000));
            hashMap.put("statType", "user_closed_app");
            hashMap.put("platform", "Android");
            hashMap.put("launch_counter", Integer.toString(m48(sharedPreferences, "appsFlyerCount", false)));
            hashMap.put("gcd_conversion_data_timing", Long.toString(sharedPreferences.getLong("appsflyerGetConversionDataTiming", 0)));
            hashMap.put(AppsFlyerProperties.CHANNEL, m40(weakReference));
            hashMap.put("originalAppsflyerId", string2 != null ? string2 : "");
            if (this.f43) {
                try {
                    m mVar = new m((Context) null, isTrackingStopped());
                    mVar.f180 = hashMap;
                    if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                        AFLogger.afDebugLog("Main thread detected. Running callStats task in a new thread.");
                        mVar.execute(new String[]{ServerConfigHandler.getUrl("https://stats.%s/stats")});
                        return;
                    }
                    AFLogger.afDebugLog(new StringBuilder("Running callStats task (on current thread: ").append(Thread.currentThread().toString()).append(" )").toString());
                    mVar.onPreExecute();
                    mVar.onPostExecute(mVar.doInBackground(ServerConfigHandler.getUrl("https://stats.%s/stats")));
                } catch (Throwable th) {
                    AFLogger.afErrorLog("Could not send callStats request", th);
                }
            } else {
                AFLogger.afDebugLog("Stats call is disabled, ignore ...");
            }
        }
    }

    public void trackAppLaunch(Context context, String str) {
        if (m56(context)) {
            if (this.f48 == null) {
                this.f48 = new d();
                this.f48.m117(context, this);
            } else {
                AFLogger.afWarnLog("AFInstallReferrer instance already created");
            }
        }
        m37(context, str, (String) null, (String) null, "", (Intent) null);
    }

    /* access modifiers changed from: protected */
    public void setDeepLinkData(Intent intent) {
        if (intent != null) {
            try {
                if ("android.intent.action.VIEW".equals(intent.getAction())) {
                    this.latestDeepLink = intent.getData();
                    AFLogger.afDebugLog(new StringBuilder("Unity setDeepLinkData = ").append(this.latestDeepLink).toString());
                }
            } catch (Throwable th) {
                AFLogger.afErrorLog("Exception while setting deeplink data (unity). ", th);
            }
        }
    }

    public void reportTrackSession(Context context) {
        C0001r.m185().m193("reportTrackSession", new String[0]);
        C0001r.m185().m190();
        m76(context, (String) null, (Map<String, Object>) null);
    }

    public void trackEvent(Context context, String str, Map<String, Object> map) {
        Map<String, Object> map2;
        if (map == null) {
            map2 = new HashMap<>();
        } else {
            map2 = map;
        }
        JSONObject jSONObject = new JSONObject(map2);
        C0001r.m185().m193("trackEvent", str, jSONObject.toString());
        m76(context, str, map);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m76(Context context, String str, Map<String, Object> map) {
        Intent intent;
        if (context instanceof Activity) {
            intent = ((Activity) context).getIntent();
            AFDeepLinkManager.getInstance().currentActivityHash = System.identityHashCode((Activity) context);
        } else {
            intent = null;
        }
        if (AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY) == null) {
            AFLogger.afWarnLog("[TrackEvent/Launch] AppsFlyer's SDK cannot send any event without providing DevKey.");
            return;
        }
        if (map == null) {
            map = new HashMap<>();
        }
        JSONObject jSONObject = new JSONObject(map);
        String referrer = AppsFlyerProperties.getInstance().getReferrer(context);
        String jSONObject2 = jSONObject.toString();
        if (referrer == null) {
            referrer = "";
        }
        m37(context, (String) null, str, jSONObject2, referrer, intent);
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static void m64(Context context, String str, String str2, String str3) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.IS_MONITOR, false)) {
            Intent intent = new Intent("com.appsflyer.MonitorBroadcast");
            intent.setPackage("com.appsflyer.nightvision");
            intent.putExtra("message", str2);
            intent.putExtra("value", str3);
            intent.putExtra("packageName", "true");
            intent.putExtra(Constants.URL_MEDIA_SOURCE, new Integer(Process.myPid()));
            intent.putExtra("eventIdentifier", str);
            intent.putExtra("sdk", BuildConfig.VERSION_NAME);
            context.sendBroadcast(intent);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m75(Context context, String str) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false) && AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID) == null) {
            AFLogger.afInfoLog("CustomerUserId not set, Tracking is disabled", true);
            return;
        }
        HashMap hashMap = new HashMap();
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
        if (string == null) {
            AFLogger.afWarnLog("[registerUninstall] AppsFlyer's SDK cannot send any event without providing DevKey.");
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            hashMap.put("app_version_code", Integer.toString(packageInfo.versionCode));
            hashMap.put("app_version_name", packageInfo.versionName);
            hashMap.put("app_name", packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
            long j = packageInfo.firstInstallTime;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssZ", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            hashMap.put("installDate", simpleDateFormat.format(new Date(j)));
        } catch (Throwable th) {
            AFLogger.afErrorLog("Exception while collecting application version info.", th);
        }
        m25(context, (Map<String, ? super String>) hashMap);
        String string2 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID);
        if (string2 != null) {
            hashMap.put("appUserId", string2);
        }
        try {
            hashMap.put("model", Build.MODEL);
            hashMap.put("brand", Build.BRAND);
        } catch (Throwable th2) {
            AFLogger.afErrorLog("Exception while collecting device brand and model.", th2);
        }
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, false)) {
            hashMap.put(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, "true");
        }
        k r0 = n.m162(context.getContentResolver());
        if (r0 != null) {
            hashMap.put("amazon_aid", r0.m155());
            hashMap.put("amazon_aid_limit", String.valueOf(r0.m154()));
        }
        String string3 = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
        if (string3 != null) {
            hashMap.put(ServerParameters.ADVERTISING_ID_PARAM, string3);
        }
        hashMap.put("devkey", string);
        hashMap.put("uid", p.m174((WeakReference<Context>) new WeakReference(context)));
        hashMap.put("af_gcm_token", str);
        hashMap.put("launch_counter", Integer.toString(m48(context.getSharedPreferences("appsflyer-data", 0), "appsFlyerCount", false)));
        hashMap.put("sdk", Integer.toString(Build.VERSION.SDK_INT));
        String r02 = m40((WeakReference<Context>) new WeakReference(context));
        if (r02 != null) {
            hashMap.put(AppsFlyerProperties.CHANNEL, r02);
        }
        try {
            m mVar = new m(context, isTrackingStopped());
            mVar.f180 = hashMap;
            mVar.execute(new String[]{new StringBuilder().append(ServerConfigHandler.getUrl(f20)).append(packageName).toString()});
        } catch (Throwable th3) {
            AFLogger.afErrorLog(th3.getMessage(), th3);
        }
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static void m51(Context context, String str) {
        Intent intent = new Intent("com.appsflyer.testIntgrationBroadcast");
        intent.putExtra("params", str);
        if (Build.VERSION.SDK_INT < 26) {
            context.sendBroadcast(intent);
        } else if (context.getPackageManager().queryBroadcastReceivers(intent, 0).toString().contains("com.appsflyer.referrerSender")) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(new ComponentName("com.appsflyer.referrerSender", "com.appsflyer.referrerSender.Receiver"));
            context.sendBroadcast(intent2);
        }
    }

    public void setDeviceTrackingDisabled(boolean z) {
        C0001r.m185().m193("setDeviceTrackingDisabled", String.valueOf(z));
        AppsFlyerProperties.getInstance().set(AppsFlyerProperties.DEVICE_TRACKING_DISABLED, z);
    }

    /* access modifiers changed from: private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public static Map<String, String> m33(Context context) throws l {
        String string = context.getSharedPreferences("appsflyer-data", 0).getString("attributionId", (String) null);
        if (string != null && string.length() > 0) {
            return m34(string);
        }
        throw new l();
    }

    public void registerConversionListener(Context context, AppsFlyerConversionListener appsFlyerConversionListener) {
        C0001r.m185().m193("registerConversionListener", new String[0]);
        if (appsFlyerConversionListener != null) {
            f21 = appsFlyerConversionListener;
        }
    }

    public void unregisterConversionListener() {
        C0001r.m185().m193("unregisterConversionListener", new String[0]);
        f21 = null;
    }

    public void registerValidatorListener(Context context, AppsFlyerInAppPurchaseValidatorListener appsFlyerInAppPurchaseValidatorListener) {
        C0001r.m185().m193("registerValidatorListener", new String[0]);
        AFLogger.afDebugLog("registerValidatorListener called");
        if (appsFlyerInAppPurchaseValidatorListener == null) {
            AFLogger.afDebugLog("registerValidatorListener null listener");
        } else {
            f18 = appsFlyerInAppPurchaseValidatorListener;
        }
    }

    /* access modifiers changed from: protected */
    public void getConversionData(Context context, final ConversionDataListener conversionDataListener) {
        f21 = new AppsFlyerConversionListener() {
            public final void onInstallConversionDataLoaded(Map<String, String> map) {
                conversionDataListener.onConversionDataLoaded(map);
            }

            public final void onInstallConversionFailure(String str) {
                conversionDataListener.onConversionFailure(str);
            }

            public final void onAppOpenAttribution(Map<String, String> map) {
            }

            public final void onAttributionFailure(String str) {
            }
        };
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private static Map<String, String> m42(Context context, String str) {
        String str2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String[] split = str.split("&");
        int length = split.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            String str3 = split[i];
            int indexOf = str3.indexOf("=");
            if (indexOf > 0) {
                str2 = str3.substring(0, indexOf);
            } else {
                str2 = str3;
            }
            if (!linkedHashMap.containsKey(str2)) {
                if (str2.equals(Constants.URL_CAMPAIGN)) {
                    str2 = "campaign";
                } else if (str2.equals(Constants.URL_MEDIA_SOURCE)) {
                    str2 = "media_source";
                } else if (str2.equals("af_prt")) {
                    z = true;
                    str2 = "agency";
                }
                linkedHashMap.put(str2, "");
            }
            boolean z2 = z;
            linkedHashMap.put(str2, (indexOf <= 0 || str3.length() <= indexOf + 1) ? null : str3.substring(indexOf + 1));
            i++;
            z = z2;
        }
        try {
            if (!linkedHashMap.containsKey("install_time")) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                long j = packageInfo.firstInstallTime;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                linkedHashMap.put("install_time", simpleDateFormat.format(new Date(j)));
            }
        } catch (Exception e2) {
            AFLogger.afErrorLog("Could not fetch install time. ", e2);
        }
        if (!linkedHashMap.containsKey("af_status")) {
            linkedHashMap.put("af_status", "Non-organic");
        }
        if (z) {
            linkedHashMap.remove("media_source");
        }
        return linkedHashMap;
    }

    /* access modifiers changed from: private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public static Map<String, String> m34(String str) {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!f23.contains(next)) {
                    String string = jSONObject.getString(next);
                    if (!TextUtils.isEmpty(string) && !"null".equals(string)) {
                        hashMap.put(next, string);
                    }
                }
            }
            return hashMap;
        } catch (JSONException e2) {
            AFLogger.afErrorLog(e2.getMessage(), e2);
            return null;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private void m37(Context context, String str, String str2, String str3, String str4, Intent intent) {
        Context applicationContext = context.getApplicationContext();
        boolean z = str2 == null;
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false) && AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID) == null) {
            AFLogger.afInfoLog("CustomerUserId not set, Tracking is disabled", true);
            return;
        }
        if (z) {
            if (!AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.LAUNCH_PROTECT_ENABLED, true)) {
                AFLogger.afInfoLog("Allowing multiple launches within a 5 second time window.");
            } else if (m55()) {
                return;
            }
            this.f27 = System.currentTimeMillis();
        }
        ScheduledThreadPoolExecutor r7 = AFExecutor.getInstance().m2();
        m66((ScheduledExecutorService) r7, (Runnable) new c(this, new WeakReference(applicationContext), str, str2, str3, str4, r7, false, intent, (byte) 0), 150, TimeUnit.MILLISECONDS);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private boolean m55() {
        if (this.f27 > 0) {
            long currentTimeMillis = System.currentTimeMillis() - this.f27;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS Z", Locale.US);
            long j = this.f27;
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String format = simpleDateFormat.format(new Date(j));
            long j2 = this.f36;
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String format2 = simpleDateFormat.format(new Date(j2));
            if (currentTimeMillis < this.f40 && !isTrackingStopped()) {
                AFLogger.afInfoLog(String.format(Locale.US, "Last Launch attempt: %s;\nLast successful Launch event: %s;\nThis launch is blocked: %s ms < %s ms", new Object[]{format, format2, Long.valueOf(currentTimeMillis), Long.valueOf(this.f40)}));
                return true;
            } else if (!isTrackingStopped()) {
                AFLogger.afInfoLog(String.format(Locale.US, "Last Launch attempt: %s;\nLast successful Launch event: %s;\nSending launch (+%s ms)", new Object[]{format, format2, Long.valueOf(currentTimeMillis)}));
            }
        } else if (!isTrackingStopped()) {
            AFLogger.afInfoLog("Sending first launch for this session!");
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* renamed from: ˎ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.String, java.lang.Object> m72(android.content.Context r13, java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, boolean r18, android.content.SharedPreferences r19, boolean r20, android.content.Intent r21) {
        /*
            r12 = this;
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            com.appsflyer.n.m161(r13, r5)
            java.util.Date r2 = new java.util.Date
            r2.<init>()
            long r2 = r2.getTime()
            java.lang.String r4 = "af_timestamp"
            java.lang.String r6 = java.lang.Long.toString(r2)
            r5.put(r4, r6)
            java.lang.String r2 = com.appsflyer.b.m101(r13, r2)
            if (r2 == 0) goto L_0x0025
            java.lang.String r3 = "cksm_v1"
            r5.put(r3, r2)
        L_0x0025:
            boolean r2 = r12.isTrackingStopped()     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x085f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "******* sendTrackingWithEvent: "
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r20 == 0) goto L_0x085c
            java.lang.String r2 = "Launch"
        L_0x0036:
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0041:
            java.lang.String r3 = "AppsFlyer_4.8.18"
            java.lang.String r4 = "EVENT_CREATED_WITH_NAME"
            if (r20 == 0) goto L_0x086f
            java.lang.String r2 = "Launch"
        L_0x0049:
            m64((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r4, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.cache.CacheManager r2 = com.appsflyer.cache.CacheManager.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r2.init(r13)     // Catch:{ Throwable -> 0x0866 }
            android.content.pm.PackageManager r2 = r13.getPackageManager()     // Catch:{ Exception -> 0x0872 }
            java.lang.String r3 = r13.getPackageName()     // Catch:{ Exception -> 0x0872 }
            r4 = 4096(0x1000, float:5.74E-42)
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r3, r4)     // Catch:{ Exception -> 0x0872 }
            java.lang.String[] r2 = r2.requestedPermissions     // Catch:{ Exception -> 0x0872 }
            java.util.List r2 = java.util.Arrays.asList(r2)     // Catch:{ Exception -> 0x0872 }
            java.lang.String r3 = "android.permission.INTERNET"
            boolean r3 = r2.contains(r3)     // Catch:{ Exception -> 0x0872 }
            if (r3 != 0) goto L_0x007b
            java.lang.String r3 = "Permission android.permission.INTERNET is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Exception -> 0x0872 }
            r3 = 0
            java.lang.String r4 = "PERMISSION_INTERNET_MISSING"
            r6 = 0
            m64((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r4, (java.lang.String) r6)     // Catch:{ Exception -> 0x0872 }
        L_0x007b:
            java.lang.String r3 = "android.permission.ACCESS_NETWORK_STATE"
            boolean r3 = r2.contains(r3)     // Catch:{ Exception -> 0x0872 }
            if (r3 != 0) goto L_0x0088
            java.lang.String r3 = "Permission android.permission.ACCESS_NETWORK_STATE is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Exception -> 0x0872 }
        L_0x0088:
            java.lang.String r3 = "android.permission.ACCESS_WIFI_STATE"
            boolean r2 = r2.contains(r3)     // Catch:{ Exception -> 0x0872 }
            if (r2 != 0) goto L_0x0095
            java.lang.String r2 = "Permission android.permission.ACCESS_WIFI_STATE is missing in the AndroidManifest.xml"
            com.appsflyer.AFLogger.afWarnLog(r2)     // Catch:{ Exception -> 0x0872 }
        L_0x0095:
            if (r18 == 0) goto L_0x009e
            java.lang.String r2 = "af_events_api"
            java.lang.String r3 = "1"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x009e:
            java.lang.String r2 = "brand"
            java.lang.String r3 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "device"
            java.lang.String r3 = android.os.Build.DEVICE     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "product"
            java.lang.String r3 = android.os.Build.PRODUCT     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "sdk"
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "model"
            java.lang.String r3 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "deviceType"
            java.lang.String r3 = android.os.Build.TYPE     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "deviceRm"
            java.lang.String r3 = android.os.Build.DISPLAY     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            if (r20 == 0) goto L_0x08a5
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "appsFlyerCount"
            boolean r2 = r2.contains(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x087a
            r2 = 1
        L_0x00e5:
            if (r2 == 0) goto L_0x0174
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            boolean r2 = r2.isOtherSdkStringDisabled()     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x00fe
            float r2 = m17(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "batteryLevel"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x00fe:
            r2 = 18
            java.lang.String r3 = "OPPO"
            java.lang.String r4 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0866 }
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x0866 }
            if (r3 == 0) goto L_0x087d
            r3 = 1
        L_0x010b:
            if (r3 == 0) goto L_0x0114
            r2 = 23
            java.lang.String r3 = "OPPO device found"
            com.appsflyer.AFLogger.afRDLog(r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0114:
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0866 }
            if (r3 < r2) goto L_0x0885
            java.lang.String r2 = "keyPropDisableAFKeystore"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r4 = 0
            boolean r2 = r3.getBoolean(r2, r4)     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x0885
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "OS SDK is="
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0866 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "; use KeyStore"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afRDLog(r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFKeystoreWrapper r2 = new com.appsflyer.AFKeystoreWrapper     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r13)     // Catch:{ Throwable -> 0x0866 }
            boolean r3 = r2.m9()     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x0880
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0866 }
            r3.<init>(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = com.appsflyer.p.m174((java.lang.ref.WeakReference<android.content.Context>) r3)     // Catch:{ Throwable -> 0x0866 }
            r2.m7(r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0156:
            java.lang.String r3 = "KSAppsFlyerId"
            java.lang.String r4 = r2.m6()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r6.set((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "KSAppsFlyerRICounter"
            int r2 = r2.m8()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r4.set((java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0174:
            java.lang.String r4 = "timepassedsincelastlaunch"
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "AppsFlyerTimePassedSincePrevLaunch"
            r6 = 0
            long r2 = r2.getLong(r3, r6)     // Catch:{ Throwable -> 0x0866 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r8 = "AppsFlyerTimePassedSincePrevLaunch"
            m36(r13, r8, r6)     // Catch:{ Throwable -> 0x0866 }
            r8 = 0
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x08a1
            long r2 = r6 - r2
            r6 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r6
        L_0x0199:
            java.lang.String r2 = java.lang.Long.toString(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r4, r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "oneLinkSlug"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x01c0
            java.lang.String r3 = "onelink_id"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "ol_ver"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "onelinkVersion"
            java.lang.String r3 = r3.getString(r4)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x01c0:
            java.lang.String r2 = "KSAppsFlyerId"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "KSAppsFlyerRICounter"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r4.getString(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x01ec
            if (r3 == 0) goto L_0x01ec
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0866 }
            int r4 = r4.intValue()     // Catch:{ Throwable -> 0x0866 }
            if (r4 <= 0) goto L_0x01ec
            java.lang.String r4 = "reinstallCounter"
            r5.put(r4, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "originalAppsflyerId"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x01ec:
            java.lang.String r2 = "additionalCustomData"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x01fd
            java.lang.String r3 = "customData"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x01fd:
            android.content.pm.PackageManager r2 = r13.getPackageManager()     // Catch:{ Exception -> 0x091f }
            java.lang.String r3 = r13.getPackageName()     // Catch:{ Exception -> 0x091f }
            java.lang.String r2 = r2.getInstallerPackageName(r3)     // Catch:{ Exception -> 0x091f }
            if (r2 == 0) goto L_0x0210
            java.lang.String r3 = "installer_package"
            r5.put(r3, r2)     // Catch:{ Exception -> 0x091f }
        L_0x0210:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "sdkExtension"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0227
            int r3 = r2.length()     // Catch:{ Throwable -> 0x0866 }
            if (r3 <= 0) goto L_0x0227
            java.lang.String r3 = "sdkExtension"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0227:
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = m40((java.lang.ref.WeakReference<android.content.Context>) r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = m67(r13, r2)     // Catch:{ Throwable -> 0x0866 }
            if (r3 == 0) goto L_0x023b
            java.lang.String r4 = "channel"
            r5.put(r4, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x023b:
            if (r3 == 0) goto L_0x0243
            boolean r4 = r3.equals(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r4 == 0) goto L_0x0247
        L_0x0243:
            if (r3 != 0) goto L_0x024c
            if (r2 == 0) goto L_0x024c
        L_0x0247:
            java.lang.String r3 = "af_latestchannel"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x024c:
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "INSTALL_STORE"
            boolean r3 = r2.contains(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r3 == 0) goto L_0x0927
            java.lang.String r3 = "INSTALL_STORE"
            r4 = 0
            java.lang.String r2 = r2.getString(r3, r4)     // Catch:{ Throwable -> 0x0866 }
        L_0x0262:
            if (r2 == 0) goto L_0x026d
            java.lang.String r3 = "af_installstore"
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x026d:
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r3 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "preInstallName"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r4.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x029a
            java.lang.String r4 = "preInstallName"
            boolean r4 = r3.contains(r4)     // Catch:{ Throwable -> 0x0866 }
            if (r4 == 0) goto L_0x0948
            java.lang.String r2 = "preInstallName"
            r4 = 0
            java.lang.String r2 = r3.getString(r2, r4)     // Catch:{ Throwable -> 0x0866 }
        L_0x028f:
            if (r2 == 0) goto L_0x029a
            java.lang.String r3 = "preInstallName"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r4.set((java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x029a:
            if (r2 == 0) goto L_0x02a5
            java.lang.String r3 = "af_preinstall_name"
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x02a5:
            java.lang.String r2 = m18(r13)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x02b4
            java.lang.String r3 = "af_currentstore"
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x02b4:
            if (r14 == 0) goto L_0x09dc
            int r2 = r14.length()     // Catch:{ Throwable -> 0x0866 }
            if (r2 < 0) goto L_0x09dc
            java.lang.String r2 = "appsflyerKey"
            r5.put(r2, r14)     // Catch:{ Throwable -> 0x0866 }
        L_0x02c1:
            java.lang.String r2 = "AppUserId"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x02d2
            java.lang.String r3 = "appUserId"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x02d2:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "userEmails"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0a0a
            java.lang.String r3 = "user_emails"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x02e3:
            if (r15 == 0) goto L_0x02f3
            java.lang.String r2 = "eventName"
            r5.put(r2, r15)     // Catch:{ Throwable -> 0x0866 }
            if (r16 == 0) goto L_0x02f3
            java.lang.String r2 = "eventValue"
            r0 = r16
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x0866 }
        L_0x02f3:
            java.lang.String r2 = "appid"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x030e
            java.lang.String r2 = "appid"
            java.lang.String r3 = "appid"
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r4.getString(r3)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x030e:
            java.lang.String r2 = "currencyCode"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x033e
            int r3 = r2.length()     // Catch:{ Throwable -> 0x0866 }
            r4 = 3
            if (r3 == r4) goto L_0x0339
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "WARNING: currency code should be 3 characters!!! '"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "' is not a legal value."
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0339:
            java.lang.String r3 = "currency"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x033e:
            java.lang.String r2 = "IS_UPDATE"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x034f
            java.lang.String r3 = "isUpdate"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x034f:
            boolean r2 = r12.isPreInstalledApp(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_preinstalled"
            java.lang.String r2 = java.lang.Boolean.toString(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "collectFacebookAttrId"
            r4 = 1
            boolean r2 = r2.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0382
            android.content.pm.PackageManager r2 = r13.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0a21, Throwable -> 0x0a2a }
            java.lang.String r3 = "com.facebook.katana"
            r4 = 0
            r2.getApplicationInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x0a21, Throwable -> 0x0a2a }
            android.content.ContentResolver r2 = r13.getContentResolver()     // Catch:{ NameNotFoundException -> 0x0a21, Throwable -> 0x0a2a }
            java.lang.String r2 = r12.getAttributionId(r2)     // Catch:{ NameNotFoundException -> 0x0a21, Throwable -> 0x0a2a }
        L_0x037b:
            if (r2 == 0) goto L_0x0382
            java.lang.String r3 = "fb"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0382:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "deviceTrackingDisabled"
            r4 = 0
            boolean r2 = r2.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0a34
            java.lang.String r2 = "deviceTrackingDisabled"
            java.lang.String r3 = "true"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0396:
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ Exception -> 0x0b52 }
            r2.<init>(r13)     // Catch:{ Exception -> 0x0b52 }
            java.lang.String r2 = com.appsflyer.p.m174((java.lang.ref.WeakReference<android.content.Context>) r2)     // Catch:{ Exception -> 0x0b52 }
            if (r2 == 0) goto L_0x03a6
            java.lang.String r3 = "uid"
            r5.put(r3, r2)     // Catch:{ Exception -> 0x0b52 }
        L_0x03a6:
            java.lang.String r2 = "lang"
            java.util.Locale r3 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0b6b }
            java.lang.String r3 = r3.getDisplayLanguage()     // Catch:{ Exception -> 0x0b6b }
            r5.put(r2, r3)     // Catch:{ Exception -> 0x0b6b }
        L_0x03b3:
            java.lang.String r2 = "lang_code"
            java.util.Locale r3 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0b73 }
            java.lang.String r3 = r3.getLanguage()     // Catch:{ Exception -> 0x0b73 }
            r5.put(r2, r3)     // Catch:{ Exception -> 0x0b73 }
        L_0x03c0:
            java.lang.String r2 = "country"
            java.util.Locale r3 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x0b7b }
            java.lang.String r3 = r3.getCountry()     // Catch:{ Exception -> 0x0b7b }
            r5.put(r2, r3)     // Catch:{ Exception -> 0x0b7b }
        L_0x03cd:
            java.lang.String r2 = "platformextension"
            com.appsflyer.q r3 = r12.f39     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r3.m177()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            m25((android.content.Context) r13, (java.util.Map<java.lang.String, ? super java.lang.String>) r5)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "yyyy-MM-dd_HHmmssZ"
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x0866 }
            java.util.Locale r4 = java.util.Locale.US     // Catch:{ Throwable -> 0x0866 }
            r3.<init>(r2, r4)     // Catch:{ Throwable -> 0x0866 }
            android.content.pm.PackageManager r2 = r13.getPackageManager()     // Catch:{ Exception -> 0x0b83 }
            java.lang.String r4 = r13.getPackageName()     // Catch:{ Exception -> 0x0b83 }
            r6 = 0
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r4, r6)     // Catch:{ Exception -> 0x0b83 }
            long r6 = r2.firstInstallTime     // Catch:{ Exception -> 0x0b83 }
            java.lang.String r2 = "installDate"
            java.lang.String r4 = "UTC"
            java.util.TimeZone r4 = java.util.TimeZone.getTimeZone(r4)     // Catch:{ Exception -> 0x0b83 }
            r3.setTimeZone(r4)     // Catch:{ Exception -> 0x0b83 }
            java.util.Date r4 = new java.util.Date     // Catch:{ Exception -> 0x0b83 }
            r4.<init>(r6)     // Catch:{ Exception -> 0x0b83 }
            java.lang.String r4 = r3.format(r4)     // Catch:{ Exception -> 0x0b83 }
            r5.put(r2, r4)     // Catch:{ Exception -> 0x0b83 }
        L_0x040a:
            android.content.pm.PackageManager r2 = r13.getPackageManager()     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = r13.getPackageName()     // Catch:{ Throwable -> 0x0b92 }
            r6 = 0
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r4, r6)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = "versionCode"
            r6 = 0
            r0 = r19
            int r4 = r0.getInt(r4, r6)     // Catch:{ Throwable -> 0x0b92 }
            int r6 = r2.versionCode     // Catch:{ Throwable -> 0x0b92 }
            if (r6 <= r4) goto L_0x0431
            java.lang.String r4 = "appsflyerConversionDataRequestRetries"
            r6 = 0
            m52((android.content.Context) r13, (java.lang.String) r4, (int) r6)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = "versionCode"
            int r6 = r2.versionCode     // Catch:{ Throwable -> 0x0b92 }
            m52((android.content.Context) r13, (java.lang.String) r4, (int) r6)     // Catch:{ Throwable -> 0x0b92 }
        L_0x0431:
            java.lang.String r4 = "app_version_code"
            int r6 = r2.versionCode     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x0b92 }
            r5.put(r4, r6)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = "app_version_name"
            java.lang.String r6 = r2.versionName     // Catch:{ Throwable -> 0x0b92 }
            r5.put(r4, r6)     // Catch:{ Throwable -> 0x0b92 }
            long r6 = r2.firstInstallTime     // Catch:{ Throwable -> 0x0b92 }
            long r8 = r2.lastUpdateTime     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r2 = "date1"
            java.lang.String r4 = "UTC"
            java.util.TimeZone r4 = java.util.TimeZone.getTimeZone(r4)     // Catch:{ Throwable -> 0x0b92 }
            r3.setTimeZone(r4)     // Catch:{ Throwable -> 0x0b92 }
            java.util.Date r4 = new java.util.Date     // Catch:{ Throwable -> 0x0b92 }
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = r3.format(r4)     // Catch:{ Throwable -> 0x0b92 }
            r5.put(r2, r4)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r2 = "date2"
            java.lang.String r4 = "UTC"
            java.util.TimeZone r4 = java.util.TimeZone.getTimeZone(r4)     // Catch:{ Throwable -> 0x0b92 }
            r3.setTimeZone(r4)     // Catch:{ Throwable -> 0x0b92 }
            java.util.Date r4 = new java.util.Date     // Catch:{ Throwable -> 0x0b92 }
            r4.<init>(r8)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = r3.format(r4)     // Catch:{ Throwable -> 0x0b92 }
            r5.put(r2, r4)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r2 = "appsflyer-data"
            r4 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r4)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = "appsFlyerFirstInstall"
            r6 = 0
            java.lang.String r2 = r2.getString(r4, r6)     // Catch:{ Throwable -> 0x0b92 }
            if (r2 != 0) goto L_0x04aa
            java.lang.String r2 = "appsflyer-data"
            r4 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r4)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r4 = "appsFlyerCount"
            boolean r2 = r2.contains(r4)     // Catch:{ Throwable -> 0x0b92 }
            if (r2 != 0) goto L_0x0b8b
            r2 = 1
        L_0x0495:
            if (r2 == 0) goto L_0x0b8e
            java.lang.String r2 = "AppsFlyer: first launch detected"
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ Throwable -> 0x0b92 }
            java.util.Date r2 = new java.util.Date     // Catch:{ Throwable -> 0x0b92 }
            r2.<init>()     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r2 = r3.format(r2)     // Catch:{ Throwable -> 0x0b92 }
        L_0x04a5:
            java.lang.String r3 = "appsFlyerFirstInstall"
            m53((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0b92 }
        L_0x04aa:
            java.lang.String r3 = "AppsFlyer: first launch date: "
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x0b92 }
            com.appsflyer.AFLogger.afInfoLog(r3)     // Catch:{ Throwable -> 0x0b92 }
            java.lang.String r3 = "firstLaunchDate"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0b92 }
        L_0x04bc:
            int r2 = r17.length()     // Catch:{ Throwable -> 0x0866 }
            if (r2 <= 0) goto L_0x04c9
            java.lang.String r2 = "referrer"
            r0 = r17
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x0866 }
        L_0x04c9:
            java.lang.String r2 = "extraReferrers"
            r3 = 0
            r0 = r19
            java.lang.String r2 = r0.getString(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x04d9
            java.lang.String r3 = "extraReferrers"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x04d9:
            java.lang.String r2 = "afUninstallToken"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x04f2
            com.appsflyer.b$e$b r2 = com.appsflyer.b.e.C0000b.m111(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_gcm_token"
            java.lang.String r2 = r2.m112()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x04f2:
            boolean r2 = com.appsflyer.y.m225(r13)     // Catch:{ Throwable -> 0x0866 }
            r12.f42 = r2     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "didConfigureTokenRefreshService="
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0866 }
            boolean r3 = r12.f42     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ Throwable -> 0x0866 }
            boolean r2 = r12.f42     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x0517
            java.lang.String r2 = "tokenRefreshConfigured"
            java.lang.Boolean r3 = java.lang.Boolean.FALSE     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0517:
            if (r20 == 0) goto L_0x0540
            com.appsflyer.AFDeepLinkManager r2 = com.appsflyer.AFDeepLinkManager.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r0 = r21
            r2.processIntentForDeepLink(r0, r13, r5)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r12.f35     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x053d
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r12.f35     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "isPush"
            java.lang.String r4 = "true"
            r2.put(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_deeplink"
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x053d:
            r2 = 0
            r12.f35 = r2     // Catch:{ Throwable -> 0x0866 }
        L_0x0540:
            boolean r2 = r12.f38     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x055c
            java.lang.String r2 = "testAppMode_retargeting"
            java.lang.String r3 = "true"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            m51(r13, r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "Sent retargeting params to test app"
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x055c:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0866 }
            long r6 = r12.f37     // Catch:{ Throwable -> 0x0866 }
            long r2 = r2 - r6
            com.appsflyer.AppsFlyerProperties r4 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = r4.getReferrer(r13)     // Catch:{ Throwable -> 0x0866 }
            r6 = 30000(0x7530, double:1.4822E-319)
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 > 0) goto L_0x0b9a
            if (r4 == 0) goto L_0x0b9a
            java.lang.String r2 = "AppsFlyer_Test"
            boolean r2 = r4.contains(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0b9a
            r2 = 1
        L_0x057c:
            if (r2 == 0) goto L_0x059f
            java.lang.String r2 = "testAppMode"
            java.lang.String r3 = "true"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            m51(r13, r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "Sent params to test app"
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "Test mode ended!"
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            r2 = 0
            r12.f37 = r2     // Catch:{ Throwable -> 0x0866 }
        L_0x059f:
            java.lang.String r2 = "advertiserId"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x05c1
            com.appsflyer.n.m161(r13, r5)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "advertiserId"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0b9d
            java.lang.String r2 = "GAID_retry"
            java.lang.String r3 = "true"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x05c1:
            android.content.ContentResolver r2 = r13.getContentResolver()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.k r2 = com.appsflyer.n.m162(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x05e1
            java.lang.String r3 = "amazon_aid"
            java.lang.String r4 = r2.m155()     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "amazon_aid_limit"
            boolean r2 = r2.m154()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x05e1:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.getReferrer(r13)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x05fe
            int r3 = r2.length()     // Catch:{ Throwable -> 0x0866 }
            if (r3 <= 0) goto L_0x05fe
            java.lang.String r3 = "referrer"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x05fe
            java.lang.String r3 = "referrer"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x05fe:
            java.lang.String r2 = "true"
            java.lang.String r3 = "sentSuccessfully"
            java.lang.String r4 = ""
            r0 = r19
            java.lang.String r3 = r0.getString(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            boolean r3 = r2.equals(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "sentRegisterRequestToAF"
            r4 = 0
            r0 = r19
            boolean r2 = r0.getBoolean(r2, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "registeredUninstall"
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r4, r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "appsFlyerCount"
            r0 = r19
            r1 = r20
            int r4 = m48((android.content.SharedPreferences) r0, (java.lang.String) r2, (boolean) r1)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "counter"
            java.lang.String r6 = java.lang.Integer.toString(r4)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r6)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r6 = "iaecounter"
            if (r15 == 0) goto L_0x0ba6
            r2 = 1
        L_0x0638:
            java.lang.String r7 = "appsFlyerInAppEventCount"
            r0 = r19
            int r2 = m48((android.content.SharedPreferences) r0, (java.lang.String) r7, (boolean) r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = java.lang.Integer.toString(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r6, r2)     // Catch:{ Throwable -> 0x0866 }
            if (r20 == 0) goto L_0x066a
            r2 = 1
            if (r4 != r2) goto L_0x066a
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r2.setFirstLaunchCalled()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "waitForCustomerId"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            r7 = 0
            boolean r2 = r6.getBoolean(r2, r7)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x066a
            java.lang.String r2 = "wait_cid"
            r6 = 1
            java.lang.String r6 = java.lang.Boolean.toString(r6)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r6)     // Catch:{ Throwable -> 0x0866 }
        L_0x066a:
            java.lang.String r6 = "isFirstCall"
            if (r3 != 0) goto L_0x0ba9
            r2 = 1
        L_0x066f:
            java.lang.String r2 = java.lang.Boolean.toString(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r6, r2)     // Catch:{ Throwable -> 0x0866 }
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Throwable -> 0x0866 }
            r2.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "cpu_abi"
            java.lang.String r6 = "ro.product.cpu.abi"
            java.lang.String r6 = m20((java.lang.String) r6)     // Catch:{ Throwable -> 0x0866 }
            r2.put(r3, r6)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "cpu_abi2"
            java.lang.String r6 = "ro.product.cpu.abi2"
            java.lang.String r6 = m20((java.lang.String) r6)     // Catch:{ Throwable -> 0x0866 }
            r2.put(r3, r6)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "arch"
            java.lang.String r6 = "os.arch"
            java.lang.String r6 = m20((java.lang.String) r6)     // Catch:{ Throwable -> 0x0866 }
            r2.put(r3, r6)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "build_display_id"
            java.lang.String r6 = "ro.build.display.id"
            java.lang.String r6 = m20((java.lang.String) r6)     // Catch:{ Throwable -> 0x0866 }
            r2.put(r3, r6)     // Catch:{ Throwable -> 0x0866 }
            if (r20 == 0) goto L_0x0725
            boolean r3 = r12.f33     // Catch:{ Throwable -> 0x0866 }
            if (r3 == 0) goto L_0x06ed
            com.appsflyer.c r3 = com.appsflyer.c.a.f107     // Catch:{ Throwable -> 0x0866 }
            android.location.Location r3 = com.appsflyer.c.m115(r13)     // Catch:{ Throwable -> 0x0866 }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x0866 }
            r7 = 3
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0866 }
            if (r3 == 0) goto L_0x06e2
            java.lang.String r7 = "lat"
            double r8 = r3.getLatitude()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0866 }
            r6.put(r7, r8)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r7 = "lon"
            double r8 = r3.getLongitude()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0866 }
            r6.put(r7, r8)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r7 = "ts"
            long r8 = r3.getTime()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0866 }
            r6.put(r7, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x06e2:
            boolean r3 = r6.isEmpty()     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x06ed
            java.lang.String r3 = "loc"
            r2.put(r3, r6)     // Catch:{ Throwable -> 0x0866 }
        L_0x06ed:
            com.appsflyer.e r3 = com.appsflyer.e.b.f116     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.e$d r3 = r3.m118(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r6 = "btl"
            float r7 = r3.m120()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r7 = java.lang.Float.toString(r7)     // Catch:{ Throwable -> 0x0866 }
            r2.put(r6, r7)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r6 = r3.m119()     // Catch:{ Throwable -> 0x0866 }
            if (r6 == 0) goto L_0x070f
            java.lang.String r6 = "btch"
            java.lang.String r3 = r3.m119()     // Catch:{ Throwable -> 0x0866 }
            r2.put(r6, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x070f:
            r3 = 2
            if (r3 < r4) goto L_0x0725
            com.appsflyer.j r3 = com.appsflyer.j.m147(r13)     // Catch:{ Throwable -> 0x0866 }
            java.util.List r3 = r3.m148()     // Catch:{ Throwable -> 0x0866 }
            boolean r4 = r3.isEmpty()     // Catch:{ Throwable -> 0x0866 }
            if (r4 != 0) goto L_0x0725
            java.lang.String r4 = "sensors"
            r2.put(r4, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x0725:
            java.util.Map r3 = com.appsflyer.AFScreenManager.getScreenMetrics(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "dim"
            r2.put(r4, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "deviceData"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.t r2 = new com.appsflyer.t     // Catch:{ Throwable -> 0x0866 }
            r2.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "appsflyerKey"
            java.lang.Object r2 = r5.get(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_timestamp"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "uid"
            java.lang.Object r4 = r5.get(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r6.<init>()     // Catch:{ Throwable -> 0x0866 }
            r7 = 0
            r8 = 7
            java.lang.String r2 = r2.substring(r7, r8)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r6.append(r2)     // Catch:{ Throwable -> 0x0866 }
            r6 = 0
            r7 = 7
            java.lang.String r4 = r4.substring(r6, r7)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0866 }
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0866 }
            int r4 = r4 + -7
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = com.appsflyer.t.m221(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_v"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.t r2 = new com.appsflyer.t     // Catch:{ Throwable -> 0x0866 }
            r2.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "appsflyerKey"
            java.lang.Object r2 = r5.get(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r3.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_timestamp"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r3.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "uid"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r3.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "installDate"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r3.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "counter"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            r3.<init>()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "iaecounter"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = com.appsflyer.t.m220(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = com.appsflyer.t.m221(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "af_v2"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            boolean r2 = m69(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "ivc"
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "is_stop_tracking_used"
            r0 = r19
            boolean r2 = r0.contains(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x083a
            java.lang.String r2 = "istu"
            java.lang.String r3 = "is_stop_tracking_used"
            r4 = 0
            r0 = r19
            boolean r3 = r0.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
        L_0x083a:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "consumeAfDeepLink"
            java.lang.Object r2 = r2.getObject(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x085a
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "consumeAfDeepLink"
            r4 = 0
            boolean r2 = r2.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "is_dp_api"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x085a:
            r2 = r5
        L_0x085b:
            return r2
        L_0x085c:
            r2 = r15
            goto L_0x0036
        L_0x085f:
            java.lang.String r2 = "SDK tracking has been stopped"
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0041
        L_0x0866:
            r2 = move-exception
            java.lang.String r3 = r2.getLocalizedMessage()
            com.appsflyer.AFLogger.afErrorLog(r3, r2)
            goto L_0x085a
        L_0x086f:
            r2 = r15
            goto L_0x0049
        L_0x0872:
            r2 = move-exception
            java.lang.String r3 = "Exception while validation permissions. "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0095
        L_0x087a:
            r2 = 0
            goto L_0x00e5
        L_0x087d:
            r3 = 0
            goto L_0x010b
        L_0x0880:
            r2.m10()     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0156
        L_0x0885:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "OS SDK is="
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0866 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "; no KeyStore usage"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afRDLog(r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0174
        L_0x08a1:
            r2 = -1
            goto L_0x0199
        L_0x08a5:
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            android.content.SharedPreferences$Editor r3 = r2.edit()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "prev_event_name"
            r6 = 0
            java.lang.String r4 = r2.getString(r4, r6)     // Catch:{ Exception -> 0x0912 }
            if (r4 == 0) goto L_0x08f2
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x0912 }
            r6.<init>()     // Catch:{ Exception -> 0x0912 }
            java.lang.String r7 = "prev_event_timestamp"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0912 }
            r8.<init>()     // Catch:{ Exception -> 0x0912 }
            java.lang.String r9 = "prev_event_timestamp"
            r10 = -1
            long r10 = r2.getLong(r9, r10)     // Catch:{ Exception -> 0x0912 }
            java.lang.StringBuilder r8 = r8.append(r10)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0912 }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r7 = "prev_event_value"
            java.lang.String r8 = "prev_event_value"
            r9 = 0
            java.lang.String r2 = r2.getString(r8, r9)     // Catch:{ Exception -> 0x0912 }
            r6.put(r7, r2)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r2 = "prev_event_name"
            r6.put(r2, r4)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r2 = "prev_event"
            java.lang.String r4 = r6.toString()     // Catch:{ Exception -> 0x0912 }
            r5.put(r2, r4)     // Catch:{ Exception -> 0x0912 }
        L_0x08f2:
            java.lang.String r2 = "prev_event_name"
            r3.putString(r2, r15)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r2 = "prev_event_value"
            r0 = r16
            r3.putString(r2, r0)     // Catch:{ Exception -> 0x0912 }
            java.lang.String r2 = "prev_event_timestamp"
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0912 }
            r3.putLong(r2, r6)     // Catch:{ Exception -> 0x0912 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0912 }
            r4 = 9
            if (r2 < r4) goto L_0x091a
            r3.apply()     // Catch:{ Exception -> 0x0912 }
            goto L_0x01c0
        L_0x0912:
            r2 = move-exception
            java.lang.String r3 = "Error while processing previous event."
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x01c0
        L_0x091a:
            r3.commit()     // Catch:{ Exception -> 0x0912 }
            goto L_0x01c0
        L_0x091f:
            r2 = move-exception
            java.lang.String r3 = "Exception while getting the app's installer package. "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0210
        L_0x0927:
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r2 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "appsFlyerCount"
            boolean r2 = r2.contains(r3)     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x0944
            r2 = 1
        L_0x0937:
            if (r2 == 0) goto L_0x0946
            java.lang.String r2 = m18(r13)     // Catch:{ Throwable -> 0x0866 }
        L_0x093d:
            java.lang.String r3 = "INSTALL_STORE"
            m53((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0262
        L_0x0944:
            r2 = 0
            goto L_0x0937
        L_0x0946:
            r2 = 0
            goto L_0x093d
        L_0x0948:
            java.lang.String r3 = "appsflyer-data"
            r4 = 0
            android.content.SharedPreferences r3 = r13.getSharedPreferences(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "appsFlyerCount"
            boolean r3 = r3.contains(r4)     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x09c4
            r3 = 1
        L_0x0958:
            if (r3 == 0) goto L_0x09bb
            java.lang.String r2 = "ro.appsflyer.preinstall.path"
            java.lang.String r2 = m20((java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            java.io.File r2 = m49((java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x096c
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x09c6
        L_0x096c:
            r3 = 1
        L_0x096d:
            if (r3 == 0) goto L_0x0981
            java.lang.String r2 = "AF_PRE_INSTALL_PATH"
            android.content.pm.PackageManager r3 = r13.getPackageManager()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = r13.getPackageName()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = m21((java.lang.String) r2, (android.content.pm.PackageManager) r3, (java.lang.String) r4)     // Catch:{ Throwable -> 0x0866 }
            java.io.File r2 = m49((java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0981:
            if (r2 == 0) goto L_0x0989
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x09c8
        L_0x0989:
            r3 = 1
        L_0x098a:
            if (r3 == 0) goto L_0x0992
            java.lang.String r2 = "/data/local/tmp/pre_install.appsflyer"
            java.io.File r2 = m49((java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0992:
            if (r2 == 0) goto L_0x099a
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0866 }
            if (r3 != 0) goto L_0x09ca
        L_0x099a:
            r3 = 1
        L_0x099b:
            if (r3 == 0) goto L_0x0bb5
            java.lang.String r2 = "/etc/pre_install.appsflyer"
            java.io.File r2 = m49((java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            r3 = r2
        L_0x09a4:
            if (r3 == 0) goto L_0x09ac
            boolean r2 = r3.exists()     // Catch:{ Throwable -> 0x0866 }
            if (r2 != 0) goto L_0x09cc
        L_0x09ac:
            r2 = 1
        L_0x09ad:
            if (r2 != 0) goto L_0x09ce
            java.lang.String r2 = r13.getPackageName()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = m39((java.io.File) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x09ce
        L_0x09b9:
            if (r2 == 0) goto L_0x09d0
        L_0x09bb:
            if (r2 == 0) goto L_0x028f
            java.lang.String r3 = "preInstallName"
            m53((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x028f
        L_0x09c4:
            r3 = 0
            goto L_0x0958
        L_0x09c6:
            r3 = 0
            goto L_0x096d
        L_0x09c8:
            r3 = 0
            goto L_0x098a
        L_0x09ca:
            r3 = 0
            goto L_0x099b
        L_0x09cc:
            r2 = 0
            goto L_0x09ad
        L_0x09ce:
            r2 = 0
            goto L_0x09b9
        L_0x09d0:
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x0866 }
            r2.<init>(r13)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "AF_PRE_INSTALL_NAME"
            java.lang.String r2 = m61((java.lang.ref.WeakReference<android.content.Context>) r2, (java.lang.String) r3)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x09bb
        L_0x09dc:
            java.lang.String r2 = "AppsFlyerKey"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x09f5
            int r3 = r2.length()     // Catch:{ Throwable -> 0x0866 }
            if (r3 < 0) goto L_0x09f5
            java.lang.String r3 = "appsflyerKey"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x02c1
        L_0x09f5:
            java.lang.String r2 = "AppsFlyer dev key is missing!!! Please use  AppsFlyerLib.getInstance().setAppsFlyerKey(...) to set it. "
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "AppsFlyer_4.8.18"
            java.lang.String r3 = "DEV_KEY_MISSING"
            r4 = 0
            m64((android.content.Context) r13, (java.lang.String) r2, (java.lang.String) r3, (java.lang.String) r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "AppsFlyer will not track this event."
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            r2 = 0
            goto L_0x085b
        L_0x0a0a:
            java.lang.String r2 = "userEmail"
            com.appsflyer.AppsFlyerProperties r3 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x02e3
            java.lang.String r3 = "sha1_el"
            java.lang.String r2 = com.appsflyer.t.m221(r2)     // Catch:{ Throwable -> 0x0866 }
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x02e3
        L_0x0a21:
            r2 = move-exception
            r2 = 0
            java.lang.String r3 = "Exception while collecting facebook's attribution ID. "
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x037b
        L_0x0a2a:
            r2 = move-exception
            r3 = 0
            java.lang.String r4 = "Exception while collecting facebook's attribution ID. "
            com.appsflyer.AFLogger.afErrorLog(r4, r2)     // Catch:{ Throwable -> 0x0866 }
            r2 = r3
            goto L_0x037b
        L_0x0a34:
            java.lang.String r2 = "appsflyer-data"
            r3 = 0
            android.content.SharedPreferences r6 = r13.getSharedPreferences(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "collectIMEI"
            r4 = 1
            boolean r2 = r2.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "imeiCached"
            r4 = 0
            java.lang.String r3 = r6.getString(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            r4 = 0
            if (r2 == 0) goto L_0x0b09
            java.lang.String r2 = r12.f45     // Catch:{ Throwable -> 0x0866 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0b09
            boolean r2 = m68(r13)     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0baf
            java.lang.String r2 = "phone"
            java.lang.Object r2 = r13.getSystemService(r2)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.Class r7 = r2.getClass()     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.String r8 = "getDeviceId"
            r9 = 0
            java.lang.Class[] r9 = new java.lang.Class[r9]     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.reflect.Method r7 = r7.getMethod(r8, r9)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.Object r2 = r7.invoke(r2, r8)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            if (r2 == 0) goto L_0x0ac8
        L_0x0a7e:
            if (r2 == 0) goto L_0x0b11
            java.lang.String r3 = "imeiCached"
            m53((android.content.Context) r13, (java.lang.String) r3, (java.lang.String) r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "imei"
            r5.put(r3, r2)     // Catch:{ Throwable -> 0x0866 }
        L_0x0a8a:
            com.appsflyer.AppsFlyerProperties r2 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = "collectAndroidId"
            r4 = 1
            boolean r4 = r2.getBoolean(r3, r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "androidIdCached"
            r3 = 0
            java.lang.String r2 = r6.getString(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            r3 = 0
            if (r4 == 0) goto L_0x0b43
            java.lang.String r4 = r12.f46     // Catch:{ Throwable -> 0x0866 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0866 }
            if (r4 == 0) goto L_0x0b43
            boolean r4 = m68(r13)     // Catch:{ Throwable -> 0x0866 }
            if (r4 == 0) goto L_0x0aba
            android.content.ContentResolver r4 = r13.getContentResolver()     // Catch:{ Exception -> 0x0b29 }
            java.lang.String r6 = "android_id"
            java.lang.String r4 = android.provider.Settings.Secure.getString(r4, r6)     // Catch:{ Exception -> 0x0b29 }
            if (r4 == 0) goto L_0x0b18
            r3 = r4
        L_0x0aba:
            if (r3 == 0) goto L_0x0b4b
            java.lang.String r2 = "androidIdCached"
            m53((android.content.Context) r13, (java.lang.String) r2, (java.lang.String) r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = "android_id"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0396
        L_0x0ac8:
            if (r3 == 0) goto L_0x0baf
            java.lang.String r2 = "use cached IMEI: "
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            java.lang.String r2 = r2.concat(r7)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ InvocationTargetException -> 0x0ad9, Exception -> 0x0af0 }
            r2 = r3
            goto L_0x0a7e
        L_0x0ad9:
            r2 = move-exception
            if (r3 == 0) goto L_0x0bb2
            java.lang.String r2 = "use cached IMEI: "
            java.lang.String r4 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r2 = r2.concat(r4)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ Throwable -> 0x0866 }
            r2 = r3
        L_0x0aea:
            java.lang.String r3 = "WARNING: READ_PHONE_STATE is missing."
            com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0a7e
        L_0x0af0:
            r2 = move-exception
            if (r3 == 0) goto L_0x0b01
            java.lang.String r4 = "use cached IMEI: "
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = r4.concat(r7)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ Throwable -> 0x0866 }
            r4 = r3
        L_0x0b01:
            java.lang.String r3 = "WARNING: other reason: "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            r2 = r4
            goto L_0x0a7e
        L_0x0b09:
            java.lang.String r2 = r12.f45     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0baf
            java.lang.String r2 = r12.f45     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0a7e
        L_0x0b11:
            java.lang.String r2 = "IMEI was not collected."
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0a8a
        L_0x0b18:
            if (r2 == 0) goto L_0x0bac
            java.lang.String r4 = "use cached AndroidId: "
            java.lang.String r6 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x0b29 }
            java.lang.String r4 = r4.concat(r6)     // Catch:{ Exception -> 0x0b29 }
            com.appsflyer.AFLogger.afDebugLog(r4)     // Catch:{ Exception -> 0x0b29 }
        L_0x0b27:
            r3 = r2
            goto L_0x0aba
        L_0x0b29:
            r4 = move-exception
            if (r2 == 0) goto L_0x0b3a
            java.lang.String r3 = "use cached AndroidId: "
            java.lang.String r6 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r3.concat(r6)     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0866 }
            r3 = r2
        L_0x0b3a:
            java.lang.String r2 = r4.getMessage()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afErrorLog(r2, r4)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0aba
        L_0x0b43:
            java.lang.String r2 = r12.f46     // Catch:{ Throwable -> 0x0866 }
            if (r2 == 0) goto L_0x0aba
            java.lang.String r3 = r12.f46     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0aba
        L_0x0b4b:
            java.lang.String r2 = "Android ID was not collected."
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x0396
        L_0x0b52:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = "ERROR: could not get uid "
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r4 = r2.getMessage()     // Catch:{ Throwable -> 0x0866 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x0866 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0866 }
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x03a6
        L_0x0b6b:
            r2 = move-exception
            java.lang.String r3 = "Exception while collecting display language name. "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x03b3
        L_0x0b73:
            r2 = move-exception
            java.lang.String r3 = "Exception while collecting display language code. "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x03c0
        L_0x0b7b:
            r2 = move-exception
            java.lang.String r3 = "Exception while collecting country name. "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x03cd
        L_0x0b83:
            r2 = move-exception
            java.lang.String r4 = "Exception while collecting install date. "
            com.appsflyer.AFLogger.afErrorLog(r4, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x040a
        L_0x0b8b:
            r2 = 0
            goto L_0x0495
        L_0x0b8e:
            java.lang.String r2 = ""
            goto L_0x04a5
        L_0x0b92:
            r2 = move-exception
            java.lang.String r3 = "Exception while collecting app version data "
            com.appsflyer.AFLogger.afErrorLog(r3, r2)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x04bc
        L_0x0b9a:
            r2 = 0
            goto L_0x057c
        L_0x0b9d:
            java.lang.String r2 = "GAID_retry"
            java.lang.String r3 = "false"
            r5.put(r2, r3)     // Catch:{ Throwable -> 0x0866 }
            goto L_0x05c1
        L_0x0ba6:
            r2 = 0
            goto L_0x0638
        L_0x0ba9:
            r2 = 0
            goto L_0x066f
        L_0x0bac:
            r2 = r3
            goto L_0x0b27
        L_0x0baf:
            r2 = r4
            goto L_0x0a7e
        L_0x0bb2:
            r2 = r4
            goto L_0x0aea
        L_0x0bb5:
            r3 = r2
            goto L_0x09a4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.m72(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, android.content.SharedPreferences, boolean, android.content.Intent):java.util.Map");
    }

    public void setConsumeAFDeepLinks(boolean z) {
        AppsFlyerProperties.getInstance().set("consumeAfDeepLink", z);
        C0001r.m185().m193("setConsumeAFDeepLinks: ".concat(String.valueOf(z)), new String[0]);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static void m25(Context context, Map<String, ? super String> map) {
        i iVar = i.c.f148;
        i.b r0 = i.m141(context);
        map.put("network", r0.m143());
        if (r0.m144() != null) {
            map.put("operator", r0.m144());
        }
        if (r0.m145() != null) {
            map.put("carrier", r0.m145());
        }
    }

    /* access modifiers changed from: protected */
    public void handleDeepLinkCallback(Context context, Map<String, Object> map, Uri uri) {
        final Map hashMap;
        map.put("af_deeplink", uri.toString());
        if (uri.getQueryParameter("af_deeplink") != null) {
            this.f38 = "AppsFlyer_Test".equals(uri.getQueryParameter("media_source")) && Boolean.parseBoolean(uri.getQueryParameter("is_retargeting"));
            hashMap = m42(context, uri.getQuery());
            String path = uri.getPath();
            if (path != null) {
                hashMap.put("path", path);
            }
            String scheme = uri.getScheme();
            if (scheme != null) {
                hashMap.put("scheme", scheme);
            }
            String host = uri.getHost();
            if (host != null) {
                hashMap.put("host", host);
            }
        } else {
            hashMap = new HashMap();
            hashMap.put("link", uri.toString());
        }
        final WeakReference weakReference = new WeakReference(context);
        s sVar = new s(uri, this);
        sVar.setConnProvider(new OneLinkHttpTask.HttpsUrlConnectionProvider());
        if (sVar.m207()) {
            sVar.m205((s.c) new s.c() {
                /* renamed from: ˎ  reason: contains not printable characters */
                public final void m79(String str) {
                    if (AppsFlyerLib.f21 != null) {
                        m77(hashMap);
                        AppsFlyerLib.f21.onAttributionFailure(str);
                    }
                }

                /* renamed from: ˋ  reason: contains not printable characters */
                private void m77(Map<String, String> map) {
                    if (weakReference.get() != null) {
                        AppsFlyerLib.m53((Context) weakReference.get(), "deeplinkAttribution", new JSONObject(map).toString());
                    }
                }

                /* renamed from: ˊ  reason: contains not printable characters */
                public final void m78(Map<String, String> map) {
                    for (String next : map.keySet()) {
                        hashMap.put(next, map.get(next));
                    }
                    m77(hashMap);
                    AppsFlyerLib.m65(hashMap);
                }
            });
            AFExecutor.getInstance().getThreadPoolExecutor().execute(sVar);
        } else if (f21 != null) {
            try {
                f21.onAppOpenAttribution(hashMap);
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getLocalizedMessage(), th);
            }
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static boolean m27(Context context) {
        try {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == 0) {
                return true;
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog("WARNING:  Google play services is unavailable. ", th);
        }
        try {
            context.getPackageManager().getPackageInfo("com.google.android.gms", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e2) {
            AFLogger.afErrorLog("WARNING:  Google Play Services is unavailable. ", e2);
            return false;
        }
    }

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static boolean m68(Context context) {
        boolean z;
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.COLLECT_ANDROID_ID_FORCE_BY_USER, false) || AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.COLLECT_IMEI_FORCE_BY_USER, false)) {
            z = true;
        } else {
            z = false;
        }
        if (z || !m27(context)) {
            return true;
        }
        return false;
    }

    /* renamed from: ʽ  reason: contains not printable characters */
    private static String m18(Context context) {
        String string = AppsFlyerProperties.getInstance().getString("api_store_value");
        if (string != null) {
            return string;
        }
        String r0 = m61((WeakReference<Context>) new WeakReference(context), "AF_STORE");
        if (r0 == null) {
            return null;
        }
        return r0;
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static String m20(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke((Object) null, new Object[]{str});
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            return null;
        }
    }

    @Nullable
    /* renamed from: ॱ  reason: contains not printable characters */
    private static String m61(WeakReference<Context> weakReference, String str) {
        if (weakReference.get() == null) {
            return null;
        }
        return m21(str, weakReference.get().getPackageManager(), weakReference.get().getPackageName());
    }

    @Nullable
    /* renamed from: ˊ  reason: contains not printable characters */
    private static String m21(String str, PackageManager packageManager, String str2) {
        Object obj;
        try {
            Bundle bundle = packageManager.getApplicationInfo(str2, 128).metaData;
            if (bundle == null || (obj = bundle.get(str)) == null) {
                return null;
            }
            return obj.toString();
        } catch (Throwable th) {
            AFLogger.afErrorLog(new StringBuilder("Could not find ").append(str).append(" value in the manifest").toString(), th);
            return null;
        }
    }

    public void setPreinstallAttribution(String str, String str2, String str3) {
        AFLogger.afDebugLog("setPreinstallAttribution API called");
        JSONObject jSONObject = new JSONObject();
        if (str != null) {
            try {
                jSONObject.put(Constants.URL_MEDIA_SOURCE, str);
            } catch (JSONException e2) {
                AFLogger.afErrorLog(e2.getMessage(), e2);
            }
        }
        if (str2 != null) {
            jSONObject.put(Constants.URL_CAMPAIGN, str2);
        }
        if (str3 != null) {
            jSONObject.put(Constants.URL_SITE_ID, str3);
        }
        if (jSONObject.has(Constants.URL_MEDIA_SOURCE)) {
            AppsFlyerProperties.getInstance().set("preInstallName", jSONObject.toString());
            return;
        }
        AFLogger.afWarnLog("Cannot set preinstall attribution data without a media source");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0056 A[SYNTHETIC, Splitter:B:24:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0069 A[SYNTHETIC, Splitter:B:31:0x0069] */
    /* renamed from: ˎ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m39(java.io.File r5, java.lang.String r6) {
        /*
            r0 = 0
            java.util.Properties r2 = new java.util.Properties     // Catch:{ FileNotFoundException -> 0x0024, Throwable -> 0x004b, all -> 0x0063 }
            r2.<init>()     // Catch:{ FileNotFoundException -> 0x0024, Throwable -> 0x004b, all -> 0x0063 }
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0024, Throwable -> 0x004b, all -> 0x0063 }
            r1.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0024, Throwable -> 0x004b, all -> 0x0063 }
            r2.load(r1)     // Catch:{ FileNotFoundException -> 0x0080, Throwable -> 0x007b }
            java.lang.String r3 = "Found PreInstall property!"
            com.appsflyer.AFLogger.afInfoLog(r3)     // Catch:{ FileNotFoundException -> 0x0080, Throwable -> 0x007b }
            java.lang.String r0 = r2.getProperty(r6)     // Catch:{ FileNotFoundException -> 0x0080, Throwable -> 0x007b }
            r1.close()     // Catch:{ Throwable -> 0x001b }
        L_0x001a:
            return r0
        L_0x001b:
            r1 = move-exception
            java.lang.String r2 = r1.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x001a
        L_0x0024:
            r1 = move-exception
            r1 = r0
        L_0x0026:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = "PreInstall file wasn't found: "
            r2.<init>(r3)     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = r5.getAbsolutePath()     // Catch:{ all -> 0x0076 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0076 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0076 }
            com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ all -> 0x0076 }
            if (r1 == 0) goto L_0x001a
            r1.close()     // Catch:{ Throwable -> 0x0042 }
            goto L_0x001a
        L_0x0042:
            r1 = move-exception
            java.lang.String r2 = r1.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x001a
        L_0x004b:
            r1 = move-exception
            r2 = r0
        L_0x004d:
            java.lang.String r3 = r1.getMessage()     // Catch:{ all -> 0x0078 }
            com.appsflyer.AFLogger.afErrorLog(r3, r1)     // Catch:{ all -> 0x0078 }
            if (r2 == 0) goto L_0x001a
            r2.close()     // Catch:{ Throwable -> 0x005a }
            goto L_0x001a
        L_0x005a:
            r1 = move-exception
            java.lang.String r2 = r1.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x001a
        L_0x0063:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0067:
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ Throwable -> 0x006d }
        L_0x006c:
            throw r0
        L_0x006d:
            r1 = move-exception
            java.lang.String r2 = r1.getMessage()
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x006c
        L_0x0076:
            r0 = move-exception
            goto L_0x0067
        L_0x0078:
            r0 = move-exception
            r1 = r2
            goto L_0x0067
        L_0x007b:
            r2 = move-exception
            r4 = r2
            r2 = r1
            r1 = r4
            goto L_0x004d
        L_0x0080:
            r2 = move-exception
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.m39(java.io.File, java.lang.String):java.lang.String");
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static File m49(String str) {
        if (str != null) {
            try {
                if (str.trim().length() > 0) {
                    return new File(str.trim());
                }
            } catch (Throwable th) {
                AFLogger.afErrorLog(th.getMessage(), th);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    @Nullable
    /* renamed from: ˎ  reason: contains not printable characters */
    public static String m40(WeakReference<Context> weakReference) {
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.CHANNEL);
        if (string == null) {
            string = m61(weakReference, "CHANNEL");
        }
        if (string == null || !string.equals("")) {
            return string;
        }
        return null;
    }

    public boolean isPreInstalledApp(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 1) != 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            AFLogger.afErrorLog("Could not check if app is pre installed", e2);
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ॱॱ  reason: contains not printable characters */
    public static String m67(Context context, String str) throws PackageManager.NameNotFoundException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appsflyer-data", 0);
        if (sharedPreferences.contains("CACHED_CHANNEL")) {
            return sharedPreferences.getString("CACHED_CHANNEL", (String) null);
        }
        m53(context, "CACHED_CHANNEL", str);
        return str;
    }

    public String getAttributionId(ContentResolver contentResolver) {
        String str = null;
        ContentResolver contentResolver2 = contentResolver;
        Cursor query = contentResolver2.query(Uri.parse(ATTRIBUTION_ID_CONTENT_URI), new String[]{ATTRIBUTION_ID_COLUMN_NAME}, (String) null, (String[]) null, (String) null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    str = query.getString(query.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Exception e2) {
                            AFLogger.afErrorLog(e2.getMessage(), e2);
                        }
                    }
                    return str;
                }
            } catch (Exception e3) {
                AFLogger.afErrorLog("Could not collect cursor attribution. ", e3);
                if (query != null) {
                    try {
                        query.close();
                    } catch (Exception e4) {
                        AFLogger.afErrorLog(e4.getMessage(), e4);
                    }
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Exception e5) {
                        AFLogger.afErrorLog(e5.getMessage(), e5);
                    }
                }
                throw th;
            }
        }
        if (query != null) {
            try {
                query.close();
            } catch (Exception e6) {
                AFLogger.afErrorLog(e6.getMessage(), e6);
            }
        }
        return str;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static SharedPreferences m57(Context context) {
        return context.getSharedPreferences("appsflyer-data", 0);
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    static int m29(SharedPreferences sharedPreferences) {
        return m48(sharedPreferences, "appsFlyerCount", false);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static int m48(SharedPreferences sharedPreferences, String str, boolean z) {
        int i = sharedPreferences.getInt(str, 0);
        if (z) {
            i++;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(str, i);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
        if (C0001r.m185().m201()) {
            C0001r.m185().m197(String.valueOf(i));
        }
        return i;
    }

    public String getAppsFlyerUID(Context context) {
        C0001r.m185().m193("getAppsFlyerUID", new String[0]);
        return p.m174((WeakReference<Context>) new WeakReference(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x0181 A[SYNTHETIC, Splitter:B:63:0x0181] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x018c A[SYNTHETIC, Splitter:B:70:0x018c] */
    /* renamed from: ˊ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m26(java.net.URL r11, java.lang.String r12, java.lang.String r13, java.lang.ref.WeakReference<android.content.Context> r14, java.lang.String r15, boolean r16) throws java.io.IOException {
        /*
            r10 = this;
            java.lang.Object r0 = r14.get()
            android.content.Context r0 = (android.content.Context) r0
            if (r16 == 0) goto L_0x0179
            com.appsflyer.AppsFlyerConversionListener r1 = f21
            if (r1 == 0) goto L_0x0179
            r1 = 1
            r2 = r1
        L_0x000e:
            r3 = 0
            com.appsflyer.r r1 = com.appsflyer.C0001r.m185()     // Catch:{ all -> 0x0243 }
            java.lang.String r4 = r11.toString()     // Catch:{ all -> 0x0243 }
            r1.m198(r4, r12)     // Catch:{ all -> 0x0243 }
            java.net.URLConnection r1 = r11.openConnection()     // Catch:{ all -> 0x0243 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ all -> 0x0243 }
            java.lang.String r3 = "POST"
            r1.setRequestMethod(r3)     // Catch:{ all -> 0x0185 }
            byte[] r3 = r12.getBytes()     // Catch:{ all -> 0x0185 }
            int r3 = r3.length     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = "Content-Length"
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0185 }
            r1.setRequestProperty(r4, r3)     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = "Content-Type"
            java.lang.String r4 = "application/json"
            r1.setRequestProperty(r3, r4)     // Catch:{ all -> 0x0185 }
            r3 = 10000(0x2710, float:1.4013E-41)
            r1.setConnectTimeout(r3)     // Catch:{ all -> 0x0185 }
            r3 = 1
            r1.setDoOutput(r3)     // Catch:{ all -> 0x0185 }
            r4 = 0
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x017d }
            java.io.OutputStream r5 = r1.getOutputStream()     // Catch:{ all -> 0x017d }
            java.lang.String r6 = "UTF-8"
            r3.<init>(r5, r6)     // Catch:{ all -> 0x017d }
            r3.write(r12)     // Catch:{ all -> 0x0247 }
            r3.close()     // Catch:{ all -> 0x0185 }
            int r3 = r1.getResponseCode()     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = m31((java.net.HttpURLConnection) r1)     // Catch:{ all -> 0x0185 }
            com.appsflyer.r r5 = com.appsflyer.C0001r.m185()     // Catch:{ all -> 0x0185 }
            java.lang.String r6 = r11.toString()     // Catch:{ all -> 0x0185 }
            r5.m195(r6, r3, r4)     // Catch:{ all -> 0x0185 }
            java.lang.String r5 = "response code: "
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0185 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ all -> 0x0185 }
            com.appsflyer.AFLogger.afInfoLog(r5)     // Catch:{ all -> 0x0185 }
            java.lang.String r5 = "AppsFlyer_4.8.18"
            java.lang.String r6 = "SERVER_RESPONSE_CODE"
            java.lang.String r7 = java.lang.Integer.toString(r3)     // Catch:{ all -> 0x0185 }
            m64((android.content.Context) r0, (java.lang.String) r5, (java.lang.String) r6, (java.lang.String) r7)     // Catch:{ all -> 0x0185 }
            java.lang.String r5 = "appsflyer-data"
            r6 = 0
            android.content.SharedPreferences r5 = r0.getSharedPreferences(r5, r6)     // Catch:{ all -> 0x0185 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r3 != r6) goto L_0x01d9
            java.lang.Object r3 = r14.get()     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x00a2
            if (r16 == 0) goto L_0x00a2
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0185 }
            r10.f36 = r6     // Catch:{ all -> 0x0185 }
            com.appsflyer.AppsFlyerTrackingRequestListener r3 = f26     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x00a2
            com.appsflyer.AppsFlyerTrackingRequestListener r3 = f26     // Catch:{ all -> 0x0185 }
            r3.onTrackingRequestSuccess()     // Catch:{ all -> 0x0185 }
        L_0x00a2:
            java.lang.String r3 = "afUninstallToken"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x0198
            java.lang.String r6 = "Uninstall Token exists: "
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0185 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ all -> 0x0185 }
            com.appsflyer.AFLogger.afDebugLog(r6)     // Catch:{ all -> 0x0185 }
            java.lang.String r6 = "sentRegisterRequestToAF"
            r7 = 0
            boolean r6 = r5.getBoolean(r6, r7)     // Catch:{ all -> 0x0185 }
            if (r6 != 0) goto L_0x00d9
            java.lang.String r6 = "Resending Uninstall token to AF servers: "
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0185 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ all -> 0x0185 }
            com.appsflyer.AFLogger.afDebugLog(r6)     // Catch:{ all -> 0x0185 }
            com.appsflyer.b$e$b r6 = new com.appsflyer.b$e$b     // Catch:{ all -> 0x0185 }
            r6.<init>(r3)     // Catch:{ all -> 0x0185 }
            com.appsflyer.y.m227(r0, r6)     // Catch:{ all -> 0x0185 }
        L_0x00d9:
            android.net.Uri r3 = r10.latestDeepLink     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x00e0
            r3 = 0
            r10.latestDeepLink = r3     // Catch:{ all -> 0x0185 }
        L_0x00e0:
            if (r15 == 0) goto L_0x00e9
            com.appsflyer.cache.CacheManager r3 = com.appsflyer.cache.CacheManager.getInstance()     // Catch:{ all -> 0x0185 }
            r3.deleteRequest(r15, r0)     // Catch:{ all -> 0x0185 }
        L_0x00e9:
            java.lang.Object r3 = r14.get()     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x0109
            if (r15 != 0) goto L_0x0109
            java.lang.String r3 = "sentSuccessfully"
            java.lang.String r6 = "true"
            m53((android.content.Context) r0, (java.lang.String) r3, (java.lang.String) r6)     // Catch:{ all -> 0x0185 }
            boolean r3 = r10.f28     // Catch:{ all -> 0x0185 }
            if (r3 != 0) goto L_0x0109
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0185 }
            long r8 = r10.f49     // Catch:{ all -> 0x0185 }
            long r6 = r6 - r8
            r8 = 15000(0x3a98, double:7.411E-320)
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x01bb
        L_0x0109:
            org.json.JSONObject r3 = com.appsflyer.ServerConfigHandler.m97(r4)     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = "send_background"
            r6 = 0
            boolean r3 = r3.optBoolean(r4, r6)     // Catch:{ all -> 0x0185 }
            r10.f43 = r3     // Catch:{ all -> 0x0185 }
        L_0x0116:
            java.lang.String r3 = "appsflyerConversionDataRequestRetries"
            r4 = 0
            int r3 = r5.getInt(r3, r4)     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = "appsflyerConversionDataCacheExpiration"
            r6 = 0
            long r6 = r5.getLong(r4, r6)     // Catch:{ all -> 0x0185 }
            r8 = 0
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 == 0) goto L_0x0147
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0185 }
            long r6 = r8 - r6
            r8 = 5184000000(0x134fd9000, double:2.561236308E-314)
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x0147
            java.lang.String r4 = "attributionId"
            r6 = 0
            m53((android.content.Context) r0, (java.lang.String) r4, (java.lang.String) r6)     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = "appsflyerConversionDataCacheExpiration"
            r6 = 0
            m36(r0, r4, r6)     // Catch:{ all -> 0x0185 }
        L_0x0147:
            java.lang.String r4 = "attributionId"
            r6 = 0
            java.lang.String r4 = r5.getString(r4, r6)     // Catch:{ all -> 0x0185 }
            if (r4 != 0) goto L_0x01ee
            if (r13 == 0) goto L_0x01ee
            if (r2 == 0) goto L_0x01ee
            com.appsflyer.AppsFlyerConversionListener r4 = f21     // Catch:{ all -> 0x0185 }
            if (r4 == 0) goto L_0x01ee
            r4 = 5
            if (r3 > r4) goto L_0x01ee
            com.appsflyer.AFExecutor r2 = com.appsflyer.AFExecutor.getInstance()     // Catch:{ all -> 0x0185 }
            java.util.concurrent.ScheduledThreadPoolExecutor r2 = r2.m2()     // Catch:{ all -> 0x0185 }
            com.appsflyer.AppsFlyerLib$b r3 = new com.appsflyer.AppsFlyerLib$b     // Catch:{ all -> 0x0185 }
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ all -> 0x0185 }
            r3.<init>(r0, r13, r2)     // Catch:{ all -> 0x0185 }
            r4 = 10
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0185 }
            m66((java.util.concurrent.ScheduledExecutorService) r2, (java.lang.Runnable) r3, (long) r4, (java.util.concurrent.TimeUnit) r0)     // Catch:{ all -> 0x0185 }
        L_0x0173:
            if (r1 == 0) goto L_0x0178
            r1.disconnect()
        L_0x0178:
            return
        L_0x0179:
            r1 = 0
            r2 = r1
            goto L_0x000e
        L_0x017d:
            r0 = move-exception
            r2 = r4
        L_0x017f:
            if (r2 == 0) goto L_0x018c
            r2.close()     // Catch:{ all -> 0x0185 }
        L_0x0184:
            throw r0     // Catch:{ all -> 0x0185 }
        L_0x0185:
            r0 = move-exception
        L_0x0186:
            if (r1 == 0) goto L_0x018b
            r1.disconnect()
        L_0x018b:
            throw r0
        L_0x018c:
            com.appsflyer.AppsFlyerTrackingRequestListener r2 = f26     // Catch:{ all -> 0x0185 }
            if (r2 == 0) goto L_0x0184
            com.appsflyer.AppsFlyerTrackingRequestListener r2 = f26     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = "No Connectivity"
            r2.onTrackingRequestFailure(r3)     // Catch:{ all -> 0x0185 }
            goto L_0x0184
        L_0x0198:
            java.lang.String r3 = "gcmProjectNumber"
            com.appsflyer.AppsFlyerProperties r6 = com.appsflyer.AppsFlyerProperties.getInstance()     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = r6.getString(r3)     // Catch:{ all -> 0x0185 }
            if (r3 == 0) goto L_0x00d9
            java.lang.String r3 = "GCM Project number exists. Fetching token and sending to AF servers"
            com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ all -> 0x0185 }
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0185 }
            r3.<init>(r0)     // Catch:{ all -> 0x0185 }
            com.appsflyer.y$b r6 = new com.appsflyer.y$b     // Catch:{ all -> 0x0185 }
            r6.<init>(r3)     // Catch:{ all -> 0x0185 }
            r3 = 0
            java.lang.Void[] r3 = new java.lang.Void[r3]     // Catch:{ all -> 0x0185 }
            r6.execute(r3)     // Catch:{ all -> 0x0185 }
            goto L_0x00d9
        L_0x01bb:
            java.util.concurrent.ScheduledExecutorService r3 = r10.f31     // Catch:{ all -> 0x0185 }
            if (r3 != 0) goto L_0x0109
            com.appsflyer.AFExecutor r3 = com.appsflyer.AFExecutor.getInstance()     // Catch:{ all -> 0x0185 }
            java.util.concurrent.ScheduledThreadPoolExecutor r3 = r3.m2()     // Catch:{ all -> 0x0185 }
            r10.f31 = r3     // Catch:{ all -> 0x0185 }
            com.appsflyer.AppsFlyerLib$d r3 = new com.appsflyer.AppsFlyerLib$d     // Catch:{ all -> 0x0185 }
            r3.<init>(r0)     // Catch:{ all -> 0x0185 }
            java.util.concurrent.ScheduledExecutorService r6 = r10.f31     // Catch:{ all -> 0x0185 }
            r8 = 1
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x0185 }
            m66((java.util.concurrent.ScheduledExecutorService) r6, (java.lang.Runnable) r3, (long) r8, (java.util.concurrent.TimeUnit) r7)     // Catch:{ all -> 0x0185 }
            goto L_0x0109
        L_0x01d9:
            com.appsflyer.AppsFlyerTrackingRequestListener r4 = f26     // Catch:{ all -> 0x0185 }
            if (r4 == 0) goto L_0x0116
            java.lang.String r4 = "Failure: "
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ all -> 0x0185 }
            com.appsflyer.AppsFlyerTrackingRequestListener r4 = f26     // Catch:{ all -> 0x0185 }
            r4.onTrackingRequestFailure(r3)     // Catch:{ all -> 0x0185 }
            goto L_0x0116
        L_0x01ee:
            if (r13 != 0) goto L_0x01f7
            java.lang.String r0 = "AppsFlyer dev key is missing."
            com.appsflyer.AFLogger.afWarnLog(r0)     // Catch:{ all -> 0x0185 }
            goto L_0x0173
        L_0x01f7:
            if (r2 == 0) goto L_0x0173
            com.appsflyer.AppsFlyerConversionListener r2 = f21     // Catch:{ all -> 0x0185 }
            if (r2 == 0) goto L_0x0173
            java.lang.String r2 = "attributionId"
            r3 = 0
            java.lang.String r2 = r5.getString(r2, r3)     // Catch:{ all -> 0x0185 }
            if (r2 == 0) goto L_0x0173
            java.lang.String r2 = "appsFlyerCount"
            r3 = 0
            int r2 = m48((android.content.SharedPreferences) r5, (java.lang.String) r2, (boolean) r3)     // Catch:{ all -> 0x0185 }
            r3 = 1
            if (r2 <= r3) goto L_0x0173
            java.util.Map r0 = m33((android.content.Context) r0)     // Catch:{ l -> 0x0239 }
            if (r0 == 0) goto L_0x0173
            java.lang.String r2 = "is_first_launch"
            boolean r2 = r0.containsKey(r2)     // Catch:{ Throwable -> 0x022f }
            if (r2 != 0) goto L_0x0228
            java.lang.String r2 = "is_first_launch"
            r3 = 0
            java.lang.String r3 = java.lang.Boolean.toString(r3)     // Catch:{ Throwable -> 0x022f }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x022f }
        L_0x0228:
            com.appsflyer.AppsFlyerConversionListener r2 = f21     // Catch:{ Throwable -> 0x022f }
            r2.onInstallConversionDataLoaded(r0)     // Catch:{ Throwable -> 0x022f }
            goto L_0x0173
        L_0x022f:
            r0 = move-exception
            java.lang.String r2 = r0.getLocalizedMessage()     // Catch:{ l -> 0x0239 }
            com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ l -> 0x0239 }
            goto L_0x0173
        L_0x0239:
            r0 = move-exception
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0185 }
            com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ all -> 0x0185 }
            goto L_0x0173
        L_0x0243:
            r0 = move-exception
            r1 = r3
            goto L_0x0186
        L_0x0247:
            r0 = move-exception
            r2 = r3
            goto L_0x017f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.m26(java.net.URL, java.lang.String, java.lang.String, java.lang.ref.WeakReference, java.lang.String, boolean):void");
    }

    public void validateAndTrackInAppPurchase(Context context, String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        C0001r r3 = C0001r.m185();
        String[] strArr = new String[6];
        strArr[0] = str;
        strArr[1] = str2;
        strArr[2] = str3;
        strArr[3] = str4;
        strArr[4] = str5;
        strArr[5] = map == null ? "" : map.toString();
        r3.m193("validateAndTrackInAppPurchase", strArr);
        if (!isTrackingStopped()) {
            AFLogger.afInfoLog(new StringBuilder("Validate in app called with parameters: ").append(str3).append(" ").append(str4).append(" ").append(str5).toString());
        }
        if (str != null && str4 != null && str2 != null && str5 != null && str3 != null) {
            ScheduledThreadPoolExecutor r11 = AFExecutor.getInstance().m2();
            m66((ScheduledExecutorService) r11, (Runnable) new h(context.getApplicationContext(), AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY), str, str2, str3, str4, str5, map, r11, context instanceof Activity ? ((Activity) context).getIntent() : null), 10, TimeUnit.MILLISECONDS);
        } else if (f18 != null) {
            f18.onValidateInAppFailure("Please provide purchase parameters");
        }
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static void m66(ScheduledExecutorService scheduledExecutorService, Runnable runnable, long j, TimeUnit timeUnit) {
        if (scheduledExecutorService != null) {
            try {
                if (!scheduledExecutorService.isShutdown() && !scheduledExecutorService.isTerminated()) {
                    scheduledExecutorService.schedule(runnable, j, timeUnit);
                    return;
                }
            } catch (RejectedExecutionException e2) {
                AFLogger.afErrorLog("scheduleJob failed with RejectedExecutionException Exception", e2);
                return;
            } catch (Throwable th) {
                AFLogger.afErrorLog("scheduleJob failed with Exception", th);
                return;
            }
        }
        AFLogger.afWarnLog("scheduler is null, shut downed or terminated");
    }

    public void onHandleReferrer(Map<String, String> map) {
        this.f47 = map;
    }

    public boolean isTrackingStopped() {
        return this.f44;
    }

    class c implements Runnable {

        /* renamed from: ʻ  reason: contains not printable characters */
        private boolean f63;

        /* renamed from: ʼ  reason: contains not printable characters */
        private boolean f64;

        /* renamed from: ʽ  reason: contains not printable characters */
        private ExecutorService f65;

        /* renamed from: ˊ  reason: contains not printable characters */
        private String f66;

        /* renamed from: ˋ  reason: contains not printable characters */
        private String f67;

        /* renamed from: ˎ  reason: contains not printable characters */
        private WeakReference<Context> f68;

        /* renamed from: ˏ  reason: contains not printable characters */
        private final Intent f69;

        /* renamed from: ॱ  reason: contains not printable characters */
        private String f70;

        /* renamed from: ॱॱ  reason: contains not printable characters */
        private String f71;

        /* synthetic */ c(AppsFlyerLib appsFlyerLib, WeakReference weakReference, String str, String str2, String str3, String str4, ExecutorService executorService, boolean z, Intent intent, byte b) {
            this(weakReference, str, str2, str3, str4, executorService, z, intent);
        }

        /* JADX WARNING: type inference failed for: r8v0, types: [java.util.concurrent.ExecutorService, boolean] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private c(java.lang.ref.WeakReference<android.content.Context> r3, java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, boolean r8, boolean r9, android.content.Intent r10) {
            /*
                r1 = this;
                com.appsflyer.AppsFlyerLib.this = r2
                r1.<init>()
                r1.f68 = r3
                r1.f67 = r4
                r1.f66 = r5
                r1.f70 = r6
                r1.f71 = r7
                r0 = 1
                r1.f63 = r0
                r1.f65 = r8
                r1.f64 = r9
                r1.f69 = r10
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.c.<init>(com.appsflyer.AppsFlyerLib, java.lang.ref.WeakReference, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.concurrent.ExecutorService, boolean, android.content.Intent):void");
        }

        public final void run() {
            AppsFlyerLib.m45(AppsFlyerLib.this, this.f68.get(), this.f67, this.f66, this.f70, this.f71, this.f63, this.f64, this.f69);
        }
    }

    static class e implements Runnable {

        /* renamed from: ˊ  reason: contains not printable characters */
        private int f75;

        /* renamed from: ˋ  reason: contains not printable characters */
        private String f76;

        /* renamed from: ˎ  reason: contains not printable characters */
        private Map<String, Object> f77;

        /* renamed from: ˏ  reason: contains not printable characters */
        private WeakReference<Context> f78;

        /* renamed from: ॱ  reason: contains not printable characters */
        private boolean f79;

        /* synthetic */ e(AppsFlyerLib appsFlyerLib, String str, Map map, Context context, boolean z, int i, byte b) {
            this(str, map, context, z, i);
        }

        private e(String str, Map<String, Object> map, Context context, boolean z, int i) {
            this.f78 = null;
            this.f76 = str;
            this.f77 = map;
            this.f78 = new WeakReference<>(context);
            this.f79 = z;
            this.f75 = i;
        }

        public final void run() {
            String str = null;
            if (!AppsFlyerLib.this.isTrackingStopped()) {
                if (this.f79 && this.f75 <= 2 && AppsFlyerLib.m38(AppsFlyerLib.this)) {
                    this.f77.put("rfr", AppsFlyerLib.this.f47);
                }
                this.f77.putAll(new b.e(this.f77, this.f78.get()));
                try {
                    str = AFHelper.convertToJsonObject(this.f77).toString();
                    AppsFlyerLib.m54(AppsFlyerLib.this, this.f76, str, (String) this.f77.get("appsflyerKey"), this.f78, (String) null, this.f79);
                } catch (IOException e) {
                    IOException iOException = e;
                    AFLogger.afErrorLog("Exception while sending request to server. ", iOException);
                    if (str != null && this.f78 != null && !this.f76.contains("&isCachedRequest=true&timeincache=")) {
                        CacheManager.getInstance().cacheRequest(new RequestCacheData(this.f76, str, BuildConfig.VERSION_NAME), this.f78.get());
                        AFLogger.afErrorLog(iOException.getMessage(), iOException);
                    }
                } catch (Throwable th) {
                    AFLogger.afErrorLog(th.getMessage(), th);
                }
            }
        }
    }

    class b extends a {
        public b(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
            super(context, str, scheduledExecutorService);
        }

        /* renamed from: ˊ  reason: contains not printable characters */
        public final String m85() {
            return ServerConfigHandler.getUrl("https://api.%s/install_data/v3/");
        }

        /* access modifiers changed from: protected */
        /* renamed from: ˎ  reason: contains not printable characters */
        public final void m87(Map<String, String> map) {
            map.put("is_first_launch", Boolean.toString(true));
            AppsFlyerLib.f21.onInstallConversionDataLoaded(map);
            AppsFlyerLib.m52(this.f60.get(), "appsflyerConversionDataRequestRetries", 0);
        }

        /* access modifiers changed from: protected */
        /* renamed from: ˊ  reason: contains not printable characters */
        public final void m86(String str, int i) {
            AppsFlyerLib.f21.onInstallConversionFailure(str);
            if (i >= 400 && i < 500) {
                AppsFlyerLib.m52(this.f60.get(), "appsflyerConversionDataRequestRetries", AppsFlyerLib.m57(this.f60.get()).getInt("appsflyerConversionDataRequestRetries", 0) + 1);
            }
        }
    }

    abstract class a implements Runnable {

        /* renamed from: ˊ  reason: contains not printable characters */
        private ScheduledExecutorService f57;

        /* renamed from: ˋ  reason: contains not printable characters */
        private String f58;

        /* renamed from: ˎ  reason: contains not printable characters */
        private AtomicInteger f59 = new AtomicInteger(0);

        /* renamed from: ˏ  reason: contains not printable characters */
        WeakReference<Context> f60 = null;

        /* renamed from: ˊ  reason: contains not printable characters */
        public abstract String m82();

        /* access modifiers changed from: protected */
        /* renamed from: ˊ  reason: contains not printable characters */
        public abstract void m83(String str, int i);

        /* access modifiers changed from: protected */
        /* renamed from: ˎ  reason: contains not printable characters */
        public abstract void m84(Map<String, String> map);

        a(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
            this.f60 = new WeakReference<>(context);
            this.f58 = str;
            if (scheduledExecutorService == null) {
                this.f57 = AFExecutor.getInstance().m2();
            } else {
                this.f57 = scheduledExecutorService;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:75:0x0231  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r11 = this;
                r10 = 1
                java.lang.String r0 = r11.f58
                if (r0 == 0) goto L_0x000d
                java.lang.String r0 = r11.f58
                int r0 = r0.length()
                if (r0 != 0) goto L_0x000e
            L_0x000d:
                return
            L_0x000e:
                com.appsflyer.AppsFlyerLib r0 = com.appsflyer.AppsFlyerLib.this
                boolean r0 = r0.isTrackingStopped()
                if (r0 != 0) goto L_0x000d
                java.util.concurrent.atomic.AtomicInteger r0 = r11.f59
                r0.incrementAndGet()
                r2 = 0
                java.lang.ref.WeakReference<android.content.Context> r0 = r11.f60     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.Object r0 = r0.get()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                android.content.Context r0 = (android.content.Context) r0     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                if (r0 != 0) goto L_0x002c
                java.util.concurrent.atomic.AtomicInteger r0 = r11.f59
                r0.decrementAndGet()
                goto L_0x000d
            L_0x002c:
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.ref.WeakReference r1 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                r1.<init>(r0)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r1 = com.appsflyer.AppsFlyerLib.m40((java.lang.ref.WeakReference<android.content.Context>) r1)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = com.appsflyer.AppsFlyerLib.m67(r0, r1)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r1 = ""
                if (r3 == 0) goto L_0x0059
                java.util.List r6 = com.appsflyer.AppsFlyerLib.f22     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r7 = r3.toLowerCase()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                boolean r6 = r6.contains(r7)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                if (r6 != 0) goto L_0x01d6
                java.lang.String r1 = "-"
                java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r1 = r1.concat(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
            L_0x0059:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                r3.<init>()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = r11.m82()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = r0.getPackageName()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = "?devkey="
                java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = r11.f58     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = "&device_id="
                java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                r3.<init>(r0)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = com.appsflyer.p.m174((java.lang.ref.WeakReference<android.content.Context>) r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r3 = r1.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                com.appsflyer.r r1 = com.appsflyer.C0001r.m185()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = r3.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r7 = ""
                r1.m198(r6, r7)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = "Calling server for attribution url: "
                r1.<init>(r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = r3.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.StringBuilder r1 = r1.append(r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                com.appsflyer.j.AnonymousClass2.m152(r1)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r6 = r3.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                r1.<init>(r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r2 = "GET"
                r1.setRequestMethod(r2)     // Catch:{ Throwable -> 0x0220 }
                r2 = 10000(0x2710, float:1.4013E-41)
                r1.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = "Connection"
                java.lang.String r6 = "close"
                r1.setRequestProperty(r2, r6)     // Catch:{ Throwable -> 0x0220 }
                r1.connect()     // Catch:{ Throwable -> 0x0220 }
                int r2 = r1.getResponseCode()     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r6 = com.appsflyer.AppsFlyerLib.m31((java.net.HttpURLConnection) r1)     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.r r7 = com.appsflyer.C0001r.m185()     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r8 = r3.toString()     // Catch:{ Throwable -> 0x0220 }
                r7.m195(r8, r2, r6)     // Catch:{ Throwable -> 0x0220 }
                r7 = 200(0xc8, float:2.8E-43)
                if (r2 != r7) goto L_0x023d
                long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r7 = "appsflyerGetConversionDataTiming"
                long r2 = r2 - r4
                r4 = 1000(0x3e8, double:4.94E-321)
                long r2 = r2 / r4
                com.appsflyer.AppsFlyerLib.m36(r0, r7, r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = "Attribution data: "
                java.lang.String r3 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.j.AnonymousClass2.m152(r2)     // Catch:{ Throwable -> 0x0220 }
                int r2 = r6.length()     // Catch:{ Throwable -> 0x0220 }
                if (r2 <= 0) goto L_0x01c5
                if (r0 == 0) goto L_0x01c5
                java.util.Map r4 = com.appsflyer.AppsFlyerLib.m34((java.lang.String) r6)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = "iscache"
                java.lang.Object r2 = r4.get(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0220 }
                if (r2 == 0) goto L_0x0132
                r3 = 0
                java.lang.String r3 = java.lang.Boolean.toString(r3)     // Catch:{ Throwable -> 0x0220 }
                boolean r3 = r3.equals(r2)     // Catch:{ Throwable -> 0x0220 }
                if (r3 == 0) goto L_0x0132
                java.lang.String r3 = "appsflyerConversionDataCacheExpiration"
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AppsFlyerLib.m36(r0, r3, r8)     // Catch:{ Throwable -> 0x0220 }
            L_0x0132:
                java.lang.String r3 = "af_siteid"
                boolean r3 = r4.containsKey(r3)     // Catch:{ Throwable -> 0x0220 }
                if (r3 == 0) goto L_0x015c
                java.lang.String r3 = "af_channel"
                boolean r3 = r4.containsKey(r3)     // Catch:{ Throwable -> 0x0220 }
                if (r3 == 0) goto L_0x0209
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = "[Invite] Detected App-Invite via channel: "
                r5.<init>(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = "af_channel"
                java.lang.Object r3 = r4.get(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0220 }
                java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0220 }
            L_0x015c:
                java.lang.String r3 = "af_siteid"
                boolean r3 = r4.containsKey(r3)     // Catch:{ Throwable -> 0x0220 }
                if (r3 == 0) goto L_0x017e
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = "[Invite] Detected App-Invite via channel: "
                r5.<init>(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = "af_channel"
                java.lang.Object r3 = r4.get(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0220 }
                java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0220 }
            L_0x017e:
                java.lang.String r3 = "is_first_launch"
                r5 = 0
                java.lang.String r5 = java.lang.Boolean.toString(r5)     // Catch:{ Throwable -> 0x0220 }
                r4.put(r3, r5)     // Catch:{ Throwable -> 0x0220 }
                org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0220 }
                r3.<init>(r4)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0220 }
                if (r3 == 0) goto L_0x0222
                java.lang.String r5 = "attributionId"
                com.appsflyer.AppsFlyerLib.m53((android.content.Context) r0, (java.lang.String) r5, (java.lang.String) r3)     // Catch:{ Throwable -> 0x0220 }
            L_0x0198:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r5 = "iscache="
                r3.<init>(r5)     // Catch:{ Throwable -> 0x0220 }
                java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = " caching conversion data"
                java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AFLogger.afDebugLog(r2)     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AppsFlyerConversionListener r2 = com.appsflyer.AppsFlyerLib.f21     // Catch:{ Throwable -> 0x0220 }
                if (r2 == 0) goto L_0x01c5
                java.util.concurrent.atomic.AtomicInteger r2 = r11.f59     // Catch:{ Throwable -> 0x0220 }
                int r2 = r2.intValue()     // Catch:{ Throwable -> 0x0220 }
                if (r2 > r10) goto L_0x01c5
                java.util.Map r0 = com.appsflyer.AppsFlyerLib.m33((android.content.Context) r0)     // Catch:{ l -> 0x0235 }
            L_0x01c2:
                r11.m84(r0)     // Catch:{ Throwable -> 0x0220 }
            L_0x01c5:
                java.util.concurrent.atomic.AtomicInteger r0 = r11.f59
                r0.decrementAndGet()
                if (r1 == 0) goto L_0x01cf
                r1.disconnect()
            L_0x01cf:
                java.util.concurrent.ScheduledExecutorService r0 = r11.f57
                r0.shutdown()
                goto L_0x000d
            L_0x01d6:
                java.lang.String r6 = "AF detected using redundant Google-Play channel for attribution - %s. Using without channel postfix."
                r7 = 1
                java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                r8 = 0
                r7[r8] = r3     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                java.lang.String r3 = java.lang.String.format(r6, r7)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                com.appsflyer.AFLogger.afWarnLog(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x026e }
                goto L_0x0059
            L_0x01e7:
                r0 = move-exception
                r1 = r2
            L_0x01e9:
                com.appsflyer.AppsFlyerConversionListener r2 = com.appsflyer.AppsFlyerLib.f21     // Catch:{ all -> 0x0229 }
                if (r2 == 0) goto L_0x01f7
                java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0229 }
                r3 = 0
                r11.m83(r2, r3)     // Catch:{ all -> 0x0229 }
            L_0x01f7:
                java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0229 }
                com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ all -> 0x0229 }
                java.util.concurrent.atomic.AtomicInteger r0 = r11.f59
                r0.decrementAndGet()
                if (r1 == 0) goto L_0x01cf
                r1.disconnect()
                goto L_0x01cf
            L_0x0209:
                java.lang.String r3 = "[CrossPromotion] App was installed via %s's Cross Promotion"
                r5 = 1
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0220 }
                r7 = 0
                java.lang.String r8 = "af_siteid"
                java.lang.Object r8 = r4.get(r8)     // Catch:{ Throwable -> 0x0220 }
                r5[r7] = r8     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r3 = java.lang.String.format(r3, r5)     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.AFLogger.afDebugLog(r3)     // Catch:{ Throwable -> 0x0220 }
                goto L_0x015c
            L_0x0220:
                r0 = move-exception
                goto L_0x01e9
            L_0x0222:
                java.lang.String r3 = "attributionId"
                com.appsflyer.AppsFlyerLib.m53((android.content.Context) r0, (java.lang.String) r3, (java.lang.String) r6)     // Catch:{ Throwable -> 0x0220 }
                goto L_0x0198
            L_0x0229:
                r0 = move-exception
            L_0x022a:
                java.util.concurrent.atomic.AtomicInteger r2 = r11.f59
                r2.decrementAndGet()
                if (r1 == 0) goto L_0x0234
                r1.disconnect()
            L_0x0234:
                throw r0
            L_0x0235:
                r0 = move-exception
                java.lang.String r2 = "Exception while trying to fetch attribution data. "
                com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ Throwable -> 0x0220 }
                r0 = r4
                goto L_0x01c2
            L_0x023d:
                com.appsflyer.AppsFlyerConversionListener r0 = com.appsflyer.AppsFlyerLib.f21     // Catch:{ Throwable -> 0x0220 }
                if (r0 == 0) goto L_0x0250
                java.lang.String r0 = "Error connection to server: "
                java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r0 = r0.concat(r4)     // Catch:{ Throwable -> 0x0220 }
                r11.m83(r0, r2)     // Catch:{ Throwable -> 0x0220 }
            L_0x0250:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r4 = "AttributionIdFetcher response code: "
                r0.<init>(r4)     // Catch:{ Throwable -> 0x0220 }
                java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r2 = "  url: "
                java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Throwable -> 0x0220 }
                java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x0220 }
                java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0220 }
                com.appsflyer.j.AnonymousClass2.m152(r0)     // Catch:{ Throwable -> 0x0220 }
                goto L_0x01c5
            L_0x026e:
                r0 = move-exception
                r1 = r2
                goto L_0x022a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.a.run():void");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0049 A[SYNTHETIC, Splitter:B:17:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004e A[Catch:{ Throwable -> 0x0099 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0068 A[SYNTHETIC, Splitter:B:30:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006d A[Catch:{ Throwable -> 0x008c }] */
    @android.support.annotation.NonNull
    /* renamed from: ˋ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String m31(java.net.HttpURLConnection r7) {
        /*
            r1 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.io.InputStream r0 = r7.getErrorStream()     // Catch:{ Throwable -> 0x009b, all -> 0x0064 }
            if (r0 != 0) goto L_0x0010
            java.io.InputStream r0 = r7.getInputStream()     // Catch:{ Throwable -> 0x009b, all -> 0x0064 }
        L_0x0010:
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x009b, all -> 0x0064 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x009b, all -> 0x0064 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x009e, all -> 0x008e }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x009e, all -> 0x008e }
        L_0x001a:
            java.lang.String r0 = r3.readLine()     // Catch:{ Throwable -> 0x002a, all -> 0x0093 }
            if (r0 == 0) goto L_0x005b
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ Throwable -> 0x002a, all -> 0x0093 }
            r1 = 10
            r0.append(r1)     // Catch:{ Throwable -> 0x002a, all -> 0x0093 }
            goto L_0x001a
        L_0x002a:
            r0 = move-exception
            r1 = r2
            r2 = r3
        L_0x002d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0097 }
            java.lang.String r5 = "Could not read connection response from: "
            r3.<init>(r5)     // Catch:{ all -> 0x0097 }
            java.net.URL r5 = r7.getURL()     // Catch:{ all -> 0x0097 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0097 }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x0097 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0097 }
            com.appsflyer.AFLogger.afErrorLog(r3, r0)     // Catch:{ all -> 0x0097 }
            if (r2 == 0) goto L_0x004c
            r2.close()     // Catch:{ Throwable -> 0x0099 }
        L_0x004c:
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ Throwable -> 0x0099 }
        L_0x0051:
            java.lang.String r0 = r4.toString()
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0071 }
            r1.<init>(r0)     // Catch:{ JSONException -> 0x0071 }
        L_0x005a:
            return r0
        L_0x005b:
            r3.close()     // Catch:{ Throwable -> 0x0062 }
            r2.close()     // Catch:{ Throwable -> 0x0062 }
            goto L_0x0051
        L_0x0062:
            r0 = move-exception
            goto L_0x0051
        L_0x0064:
            r0 = move-exception
            r2 = r1
        L_0x0066:
            if (r2 == 0) goto L_0x006b
            r2.close()     // Catch:{ Throwable -> 0x008c }
        L_0x006b:
            if (r1 == 0) goto L_0x0070
            r1.close()     // Catch:{ Throwable -> 0x008c }
        L_0x0070:
            throw r0
        L_0x0071:
            r1 = move-exception
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r2 = "string_response"
            r1.put(r2, r0)     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r0 = r1.toString()     // Catch:{ JSONException -> 0x0081 }
            goto L_0x005a
        L_0x0081:
            r0 = move-exception
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r0 = r0.toString()
            goto L_0x005a
        L_0x008c:
            r1 = move-exception
            goto L_0x0070
        L_0x008e:
            r0 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x0066
        L_0x0093:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0066
        L_0x0097:
            r0 = move-exception
            goto L_0x0066
        L_0x0099:
            r0 = move-exception
            goto L_0x0051
        L_0x009b:
            r0 = move-exception
            r2 = r1
            goto L_0x002d
        L_0x009e:
            r0 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyerLib.m31(java.net.HttpURLConnection):java.lang.String");
    }

    class d implements Runnable {

        /* renamed from: ˋ  reason: contains not printable characters */
        private WeakReference<Context> f73 = null;

        public d(Context context) {
            this.f73 = new WeakReference<>(context);
        }

        public final void run() {
            if (!AppsFlyerLib.this.f28) {
                long unused = AppsFlyerLib.this.f49 = System.currentTimeMillis();
                if (this.f73 != null) {
                    boolean unused2 = AppsFlyerLib.this.f28 = true;
                    try {
                        String r5 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY);
                        synchronized (this.f73) {
                            for (RequestCacheData next : CacheManager.getInstance().getCachedRequests(this.f73.get())) {
                                AFLogger.afInfoLog(new StringBuilder("resending request: ").append(next.getRequestURL()).toString());
                                try {
                                    AppsFlyerLib.m54(AppsFlyerLib.this, new StringBuilder().append(next.getRequestURL()).append("&isCachedRequest=true&timeincache=").append(Long.toString((System.currentTimeMillis() - Long.parseLong(next.getCacheKey(), 10)) / 1000)).toString(), next.getPostData(), r5, this.f73, next.getCacheKey(), false);
                                } catch (Exception e) {
                                    AFLogger.afErrorLog("Failed to resend cached request", e);
                                }
                            }
                        }
                        boolean unused3 = AppsFlyerLib.this.f28 = false;
                    } catch (Exception e2) {
                        try {
                            AFLogger.afErrorLog("failed to check cache. ", e2);
                        } finally {
                            boolean unused4 = AppsFlyerLib.this.f28 = false;
                        }
                    }
                    AppsFlyerLib.this.f31.shutdown();
                    ScheduledExecutorService unused5 = AppsFlyerLib.this.f31 = null;
                }
            }
        }
    }

    /* renamed from: ʼ  reason: contains not printable characters */
    private static float m17(Context context) {
        try {
            Intent registerReceiver = context.getApplicationContext().registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra("scale", -1);
            if (intExtra == -1 || intExtra2 == -1) {
                return 50.0f;
            }
            return (((float) intExtra) / ((float) intExtra2)) * 100.0f;
        } catch (Throwable th) {
            AFLogger.afErrorLog(th.getMessage(), th);
            return 1.0f;
        }
    }

    /* renamed from: ᐝ  reason: contains not printable characters */
    private static boolean m69(Context context) {
        if (context != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                    for (Network networkCapabilities : connectivityManager.getAllNetworks()) {
                        NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities(networkCapabilities);
                        if (networkCapabilities2.hasTransport(4) && !networkCapabilities2.hasCapability(15)) {
                            return true;
                        }
                    }
                    return false;
                } catch (Exception e2) {
                    AFLogger.afErrorLog("Failed collecting ivc data", e2);
                }
            } else if (Build.VERSION.SDK_INT >= 16) {
                ArrayList arrayList = new ArrayList();
                try {
                    Iterator<T> it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
                    while (it.hasNext()) {
                        NetworkInterface networkInterface = (NetworkInterface) it.next();
                        if (networkInterface.isUp()) {
                            arrayList.add(networkInterface.getName());
                        }
                    }
                    return arrayList.contains("tun0");
                } catch (Exception e3) {
                    AFLogger.afErrorLog("Failed collecting ivc data", e3);
                }
            }
        }
        return false;
    }

    public void setLogLevel(AFLogger.LogLevel logLevel) {
        AppsFlyerProperties.getInstance().set("logLevel", logLevel.getLevel());
    }

    public void setHostName(String str) {
        AppsFlyerProperties.getInstance().set("custom_host", str);
    }

    public String getHost() {
        String string = AppsFlyerProperties.getInstance().getString("custom_host");
        return string != null ? string : ServerParameters.DEFAULT_HOST;
    }

    public void setMinTimeBetweenSessions(int i) {
        this.f40 = TimeUnit.SECONDS.toMillis((long) i);
    }

    /* access modifiers changed from: private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public static void m53(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putString(str, str2);
        if (Build.VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public static void m52(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putInt(str, i);
        if (Build.VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public static void m36(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
        edit.putLong(str, j);
        if (Build.VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }
}

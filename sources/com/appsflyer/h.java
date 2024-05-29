package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

final class h implements Runnable {

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static String f132 = new StringBuilder("https://validate.%s/api/v").append(AppsFlyerLib.f19).append("/androidevent?buildnumber=4.8.18&app_id=").toString();

    /* renamed from: ʻ  reason: contains not printable characters */
    private String f133;
    /* access modifiers changed from: private */

    /* renamed from: ʼ  reason: contains not printable characters */
    public Map<String, String> f134;

    /* renamed from: ʽ  reason: contains not printable characters */
    private ScheduledExecutorService f135;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f136;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f137;
    /* access modifiers changed from: private */

    /* renamed from: ˎ  reason: contains not printable characters */
    public WeakReference<Context> f138 = null;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f139;

    /* renamed from: ͺ  reason: contains not printable characters */
    private final Intent f140;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f141;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private String f142;

    h(Context context, String str, String str2, String str3, String str4, String str5, String str6, Map<String, String> map, ScheduledExecutorService scheduledExecutorService, Intent intent) {
        this.f138 = new WeakReference<>(context);
        this.f139 = str;
        this.f136 = str2;
        this.f141 = str4;
        this.f142 = str5;
        this.f133 = str6;
        this.f134 = map;
        this.f137 = str3;
        this.f135 = scheduledExecutorService;
        this.f140 = intent;
    }

    public final void run() {
        boolean z;
        if (this.f139 != null && this.f139.length() != 0 && !AppsFlyerLib.getInstance().isTrackingStopped()) {
            HttpURLConnection httpURLConnection = null;
            try {
                Context context = this.f138.get();
                if (context != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("public-key", this.f136);
                    hashMap.put("sig-data", this.f141);
                    hashMap.put("signature", this.f137);
                    final HashMap hashMap2 = new HashMap();
                    hashMap2.putAll(hashMap);
                    Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                        public final void run() {
                            h.m138(h.this, hashMap2, h.this.f134, h.this.f138);
                        }
                    }, 5, TimeUnit.MILLISECONDS);
                    hashMap.put("dev_key", this.f139);
                    hashMap.put("app_id", context.getPackageName());
                    hashMap.put("uid", AppsFlyerLib.getInstance().getAppsFlyerUID(context));
                    hashMap.put(ServerParameters.ADVERTISING_ID_PARAM, AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM));
                    String jSONObject = new JSONObject(hashMap).toString();
                    String url = ServerConfigHandler.getUrl("https://sdk-services.%s/validate-android-signature");
                    C0001r.m185().m198(url, jSONObject);
                    httpURLConnection = m140(jSONObject, url);
                    int i = -1;
                    if (httpURLConnection != null) {
                        i = httpURLConnection.getResponseCode();
                    }
                    AppsFlyerLib.getInstance();
                    String r4 = AppsFlyerLib.m31(httpURLConnection);
                    C0001r.m185().m195(url, i, r4);
                    JSONObject jSONObject2 = new JSONObject(r4);
                    jSONObject2.put("code", i);
                    if (i == 200) {
                        AFLogger.afInfoLog(new StringBuilder("Validate response 200 ok: ").append(jSONObject2.toString()).toString());
                        if (jSONObject2.optBoolean("result")) {
                            z = jSONObject2.getBoolean("result");
                        } else {
                            z = false;
                        }
                        m137(z, this.f141, this.f142, this.f133, jSONObject2.toString());
                    } else {
                        AFLogger.afInfoLog("Failed Validate request");
                        m137(false, this.f141, this.f142, this.f133, jSONObject2.toString());
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            } catch (Throwable th) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        }
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static HttpURLConnection m140(String str, String str2) throws IOException {
        try {
            m mVar = new m((Context) null, AppsFlyerLib.getInstance().isTrackingStopped());
            mVar.f181 = str;
            mVar.m157();
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                AFLogger.afDebugLog(new StringBuilder("Main thread detected. Calling ").append(str2).append(" in a new thread.").toString());
                mVar.execute(new String[]{str2});
            } else {
                AFLogger.afDebugLog(new StringBuilder("Calling ").append(str2).append(" (on current thread: ").append(Thread.currentThread().toString()).append(" )").toString());
                mVar.onPreExecute();
                mVar.onPostExecute(mVar.doInBackground(str2));
            }
            return mVar.m160();
        } catch (Throwable th) {
            AFLogger.afErrorLog("Could not send callStats request", th);
            return null;
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private static void m137(boolean z, String str, String str2, String str3, String str4) {
        if (AppsFlyerLib.f18 != null) {
            AFLogger.afDebugLog(new StringBuilder("Validate callback parameters: ").append(str).append(" ").append(str2).append(" ").append(str3).toString());
            if (z) {
                AFLogger.afDebugLog("Validate in app purchase success: ".concat(String.valueOf(str4)));
                AppsFlyerLib.f18.onValidateInApp();
                return;
            }
            AFLogger.afDebugLog("Validate in app purchase failed: ".concat(String.valueOf(str4)));
            AppsFlyerInAppPurchaseValidatorListener appsFlyerInAppPurchaseValidatorListener = AppsFlyerLib.f18;
            if (str4 == null) {
                str4 = "Failed validating";
            }
            appsFlyerInAppPurchaseValidatorListener.onValidateInAppFailure(str4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0151  */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void m138(com.appsflyer.h r13, java.util.Map r14, java.util.Map r15, java.lang.ref.WeakReference r16) {
        /*
            r11 = 0
            r9 = 0
            java.lang.Object r1 = r16.get()
            if (r1 == 0) goto L_0x0111
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = f132
            java.lang.String r2 = com.appsflyer.ServerConfigHandler.getUrl(r2)
            java.lang.StringBuilder r2 = r1.append(r2)
            java.lang.Object r1 = r16.get()
            android.content.Context r1 = (android.content.Context) r1
            java.lang.String r1 = r1.getPackageName()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r12 = r1.toString()
            java.lang.Object r1 = r16.get()
            android.content.Context r1 = (android.content.Context) r1
            java.lang.String r2 = "appsflyer-data"
            android.content.SharedPreferences r8 = r1.getSharedPreferences(r2, r9)
            java.lang.String r1 = "referrer"
            java.lang.String r6 = r8.getString(r1, r11)
            if (r6 != 0) goto L_0x003f
            java.lang.String r6 = ""
        L_0x003f:
            com.appsflyer.AppsFlyerLib r1 = com.appsflyer.AppsFlyerLib.getInstance()
            java.lang.Object r2 = r16.get()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r3 = r13.f139
            java.lang.String r4 = "af_purchase"
            java.lang.String r5 = ""
            r7 = 1
            android.content.Intent r10 = r13.f140
            java.util.Map r1 = r1.m72((android.content.Context) r2, (java.lang.String) r3, (java.lang.String) r4, (java.lang.String) r5, (java.lang.String) r6, (boolean) r7, (android.content.SharedPreferences) r8, (boolean) r9, (android.content.Intent) r10)
            java.lang.String r2 = "price"
            java.lang.String r3 = r13.f142
            r1.put(r2, r3)
            java.lang.String r2 = "currency"
            java.lang.String r3 = r13.f133
            r1.put(r2, r3)
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>(r1)
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.util.Set r1 = r14.entrySet()     // Catch:{ JSONException -> 0x0092 }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ JSONException -> 0x0092 }
        L_0x0076:
            boolean r1 = r5.hasNext()     // Catch:{ JSONException -> 0x0092 }
            if (r1 == 0) goto L_0x0112
            java.lang.Object r1 = r5.next()     // Catch:{ JSONException -> 0x0092 }
            r0 = r1
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ JSONException -> 0x0092 }
            r2 = r0
            java.lang.Object r1 = r2.getKey()     // Catch:{ JSONException -> 0x0092 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ JSONException -> 0x0092 }
            java.lang.Object r2 = r2.getValue()     // Catch:{ JSONException -> 0x0092 }
            r4.put(r1, r2)     // Catch:{ JSONException -> 0x0092 }
            goto L_0x0076
        L_0x0092:
            r1 = move-exception
            java.lang.String r2 = "Failed to build 'receipt_data'"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
        L_0x0098:
            if (r15 == 0) goto L_0x00c9
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.util.Set r1 = r15.entrySet()     // Catch:{ JSONException -> 0x00c3 }
            java.util.Iterator r5 = r1.iterator()     // Catch:{ JSONException -> 0x00c3 }
        L_0x00a7:
            boolean r1 = r5.hasNext()     // Catch:{ JSONException -> 0x00c3 }
            if (r1 == 0) goto L_0x0118
            java.lang.Object r1 = r5.next()     // Catch:{ JSONException -> 0x00c3 }
            r0 = r1
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ JSONException -> 0x00c3 }
            r2 = r0
            java.lang.Object r1 = r2.getKey()     // Catch:{ JSONException -> 0x00c3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ JSONException -> 0x00c3 }
            java.lang.Object r2 = r2.getValue()     // Catch:{ JSONException -> 0x00c3 }
            r4.put(r1, r2)     // Catch:{ JSONException -> 0x00c3 }
            goto L_0x00a7
        L_0x00c3:
            r1 = move-exception
            java.lang.String r2 = "Failed to build 'extra_prms'"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
        L_0x00c9:
            java.lang.String r1 = r3.toString()
            com.appsflyer.r r2 = com.appsflyer.C0001r.m185()
            r2.m198(r12, r1)
            java.net.HttpURLConnection r2 = m140(r1, r12)     // Catch:{ Throwable -> 0x0157, all -> 0x014d }
            r1 = -1
            if (r2 == 0) goto L_0x00df
            int r1 = r2.getResponseCode()     // Catch:{ Throwable -> 0x013f }
        L_0x00df:
            com.appsflyer.AppsFlyerLib.getInstance()     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = com.appsflyer.AppsFlyerLib.m31((java.net.HttpURLConnection) r2)     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.r r4 = com.appsflyer.C0001r.m185()     // Catch:{ Throwable -> 0x013f }
            r4.m195(r12, r1, r3)     // Catch:{ Throwable -> 0x013f }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Throwable -> 0x013f }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x013f }
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x011e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = "Validate-WH response - 200: "
            r1.<init>(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = r4.toString()     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.AFLogger.afInfoLog(r1)     // Catch:{ Throwable -> 0x013f }
        L_0x010c:
            if (r2 == 0) goto L_0x0111
            r2.disconnect()
        L_0x0111:
            return
        L_0x0112:
            java.lang.String r1 = "receipt_data"
            r3.put(r1, r4)     // Catch:{ JSONException -> 0x0092 }
            goto L_0x0098
        L_0x0118:
            java.lang.String r1 = "extra_prms"
            r3.put(r1, r4)     // Catch:{ JSONException -> 0x00c3 }
            goto L_0x00c9
        L_0x011e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013f }
            java.lang.String r5 = "Validate-WH response failed - "
            r3.<init>(r5)     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = ": "
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r3 = r4.toString()     // Catch:{ Throwable -> 0x013f }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x013f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x013f }
            com.appsflyer.AFLogger.afWarnLog(r1)     // Catch:{ Throwable -> 0x013f }
            goto L_0x010c
        L_0x013f:
            r1 = move-exception
        L_0x0140:
            java.lang.String r3 = r1.getMessage()     // Catch:{ all -> 0x0155 }
            com.appsflyer.AFLogger.afErrorLog(r3, r1)     // Catch:{ all -> 0x0155 }
            if (r2 == 0) goto L_0x0111
            r2.disconnect()
            goto L_0x0111
        L_0x014d:
            r1 = move-exception
            r2 = r11
        L_0x014f:
            if (r2 == 0) goto L_0x0154
            r2.disconnect()
        L_0x0154:
            throw r1
        L_0x0155:
            r1 = move-exception
            goto L_0x014f
        L_0x0157:
            r1 = move-exception
            r2 = r11
            goto L_0x0140
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.h.m138(com.appsflyer.h, java.util.Map, java.util.Map, java.lang.ref.WeakReference):void");
    }
}

package com.appsflyer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.appsflyer.r  reason: case insensitive filesystem */
final class C0001r {

    /* renamed from: ˋ  reason: contains not printable characters */
    private static C0001r f201;

    /* renamed from: ʻ  reason: contains not printable characters */
    private final String f202;

    /* renamed from: ʻॱ  reason: contains not printable characters */
    private final String f203;

    /* renamed from: ʼ  reason: contains not printable characters */
    private final String f204;

    /* renamed from: ʼॱ  reason: contains not printable characters */
    private final String f205;

    /* renamed from: ʽ  reason: contains not printable characters */
    private final String f206;

    /* renamed from: ʽॱ  reason: contains not printable characters */
    private final String f207;

    /* renamed from: ʾ  reason: contains not printable characters */
    private final String f208;

    /* renamed from: ʿ  reason: contains not printable characters */
    private final String f209;

    /* renamed from: ˈ  reason: contains not printable characters */
    private final String f210;

    /* renamed from: ˉ  reason: contains not printable characters */
    private int f211;

    /* renamed from: ˊ  reason: contains not printable characters */
    private final String f212;

    /* renamed from: ˊˊ  reason: contains not printable characters */
    private JSONArray f213;

    /* renamed from: ˊˋ  reason: contains not printable characters */
    private final String f214;

    /* renamed from: ˊॱ  reason: contains not printable characters */
    private final String f215;

    /* renamed from: ˊᐝ  reason: contains not printable characters */
    private JSONObject f216;

    /* renamed from: ˋˊ  reason: contains not printable characters */
    private final String f217;

    /* renamed from: ˋˋ  reason: contains not printable characters */
    private boolean f218;

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private final String f219;

    /* renamed from: ˎ  reason: contains not printable characters */
    private final String f220;

    /* renamed from: ˎˎ  reason: contains not printable characters */
    private String f221;

    /* renamed from: ˏ  reason: contains not printable characters */
    private boolean f222;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private final String f223;

    /* renamed from: ͺ  reason: contains not printable characters */
    private final String f224;

    /* renamed from: ॱ  reason: contains not printable characters */
    private boolean f225;

    /* renamed from: ॱˊ  reason: contains not printable characters */
    private final String f226;

    /* renamed from: ॱˋ  reason: contains not printable characters */
    private final String f227;

    /* renamed from: ॱˎ  reason: contains not printable characters */
    private final String f228;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private final String f229;

    /* renamed from: ॱᐝ  reason: contains not printable characters */
    private final String f230;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private final String f231;

    /* renamed from: ᐝॱ  reason: contains not printable characters */
    private final String f232;

    private C0001r() {
        this.f222 = true;
        this.f225 = true;
        this.f220 = "brand";
        this.f212 = "model";
        this.f206 = "platform";
        this.f202 = "platform_version";
        this.f229 = ServerParameters.ADVERTISING_ID_PARAM;
        this.f231 = "imei";
        this.f204 = "android_id";
        this.f215 = "sdk_version";
        this.f219 = "devkey";
        this.f224 = "originalAppsFlyerId";
        this.f226 = "uid";
        this.f223 = "app_id";
        this.f203 = "app_version";
        this.f230 = AppsFlyerProperties.CHANNEL;
        this.f232 = "preInstall";
        this.f228 = "data";
        this.f227 = "r_debugging_off";
        this.f207 = "r_debugging_on";
        this.f208 = "public_api_call";
        this.f205 = "exception";
        this.f210 = "server_request";
        this.f209 = "server_response";
        this.f217 = "yyyy-MM-dd HH:mm:ssZ";
        this.f214 = "MM-dd HH:mm:ss.SSS";
        this.f211 = 0;
        this.f221 = "-1";
        this.f213 = new JSONArray();
        this.f211 = 0;
        this.f218 = false;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static C0001r m185() {
        if (f201 == null) {
            f201 = new C0001r();
        }
        return f201;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final synchronized void m197(String str) {
        this.f221 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final synchronized void m191() {
        this.f218 = true;
        m188("r_debugging_on", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final synchronized void m196() {
        m188("r_debugging_off", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
        this.f218 = false;
        this.f222 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final synchronized void m192() {
        this.f216 = null;
        this.f213 = null;
        f201 = null;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˋ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m184(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r3 = this;
            monitor-enter(r3)
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "brand"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "model"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "platform"
            java.lang.String r2 = "Android"
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "platform_version"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r7 == 0) goto L_0x002e
            int r0 = r7.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x002e
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "advertiserId"
            r0.put(r1, r7)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x002e:
            if (r8 == 0) goto L_0x003d
            int r0 = r8.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x003d
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "imei"
            r0.put(r1, r8)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x003d:
            if (r9 == 0) goto L_0x004c
            int r0 = r9.length()     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            if (r0 <= 0) goto L_0x004c
            org.json.JSONObject r0 = r3.f216     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
            java.lang.String r1 = "android_id"
            r0.put(r1, r9)     // Catch:{ Throwable -> 0x0051, all -> 0x004e }
        L_0x004c:
            monitor-exit(r3)
            return
        L_0x004e:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0051:
            r0 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.C0001r.m184(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˊ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m182(java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r2 = this;
            monitor-enter(r2)
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "sdk_version"
            r0.put(r1, r3)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r4 == 0) goto L_0x0017
            int r0 = r4.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0017
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "devkey"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0017:
            if (r5 == 0) goto L_0x0026
            int r0 = r5.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0026
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "originalAppsFlyerId"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0026:
            if (r6 == 0) goto L_0x0035
            int r0 = r6.length()     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            if (r0 <= 0) goto L_0x0035
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
            java.lang.String r1 = "uid"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x003a, all -> 0x0037 }
        L_0x0035:
            monitor-exit(r2)
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x003a:
            r0 = move-exception
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.C0001r.m182(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m186(java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x0010
            int r0 = r3.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x0010
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "app_id"
            r0.put(r1, r3)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x0010:
            if (r4 == 0) goto L_0x001f
            int r0 = r4.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x001f
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "app_version"
            r0.put(r1, r4)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x001f:
            if (r5 == 0) goto L_0x002e
            int r0 = r5.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x002e
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "channel"
            r0.put(r1, r5)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x002e:
            if (r6 == 0) goto L_0x003d
            int r0 = r6.length()     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            if (r0 <= 0) goto L_0x003d
            org.json.JSONObject r0 = r2.f216     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
            java.lang.String r1 = "preInstall"
            r0.put(r1, r6)     // Catch:{ Throwable -> 0x0042, all -> 0x003f }
        L_0x003d:
            monitor-exit(r2)
            return
        L_0x003f:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x0042:
            r0 = move-exception
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.C0001r.m186(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m193(String str, String... strArr) {
        m188("public_api_call", str, strArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m194(Throwable th) {
        String message;
        StackTraceElement[] stackTrace;
        String[] strArr;
        Throwable cause = th.getCause();
        String simpleName = th.getClass().getSimpleName();
        if (cause == null) {
            message = th.getMessage();
        } else {
            message = cause.getMessage();
        }
        if (cause == null) {
            stackTrace = th.getStackTrace();
        } else {
            stackTrace = cause.getStackTrace();
        }
        if (stackTrace == null) {
            strArr = new String[]{message};
        } else {
            String[] strArr2 = new String[(stackTrace.length + 1)];
            strArr2[0] = message;
            for (int i = 1; i < stackTrace.length; i++) {
                strArr2[i] = stackTrace[i].toString();
            }
            strArr = strArr2;
        }
        m188("exception", simpleName, strArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m198(String str, String str2) {
        m188("server_request", str, str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m195(String str, int i, String str2) {
        m188("server_response", str, String.valueOf(i), str2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final void m200(String str, String str2) {
        m188((String) null, str, str2);
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private synchronized void m188(String str, String str2, String... strArr) {
        String format;
        boolean z = true;
        synchronized (this) {
            if (!this.f225 || (!this.f222 && !this.f218)) {
                z = false;
            }
            if (z && this.f211 < 98304) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    String str3 = "";
                    if (strArr.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int length = strArr.length - 1; length > 0; length--) {
                            sb.append(strArr[length]).append(", ");
                        }
                        sb.append(strArr[0]);
                        str3 = sb.toString();
                    }
                    String format2 = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.ENGLISH).format(Long.valueOf(currentTimeMillis));
                    if (str != null) {
                        format = String.format("%18s %5s _/%s [%s] %s %s", new Object[]{format2, Long.valueOf(Thread.currentThread().getId()), AppsFlyerLib.LOG_TAG, str, str2, str3});
                    } else {
                        format = String.format("%18s %5s %s/%s %s", new Object[]{format2, Long.valueOf(Thread.currentThread().getId()), str2, AppsFlyerLib.LOG_TAG, str3});
                    }
                    this.f213.put(format);
                    this.f211 = format.getBytes().length + this.f211;
                } catch (Throwable th) {
                }
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* renamed from: ʼ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String m181() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            org.json.JSONObject r1 = r4.f216     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            java.lang.String r2 = "data"
            org.json.JSONArray r3 = r4.f213     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            org.json.JSONObject r1 = r4.f216     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            java.lang.String r0 = r1.toString()     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
            r4.m189()     // Catch:{ JSONException -> 0x0019, all -> 0x0016 }
        L_0x0014:
            monitor-exit(r4)
            return r0
        L_0x0016:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x0019:
            r1 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.C0001r.m181():java.lang.String");
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private synchronized void m183(String str, PackageManager packageManager) {
        AppsFlyerProperties instance = AppsFlyerProperties.getInstance();
        AppsFlyerLib instance2 = AppsFlyerLib.getInstance();
        String string = instance.getString("remote_debug_static_data");
        if (string != null) {
            try {
                this.f216 = new JSONObject(string);
            } catch (Throwable th) {
            }
        } else {
            this.f216 = new JSONObject();
            m184(Build.BRAND, Build.MODEL, Build.VERSION.RELEASE, instance.getString(ServerParameters.ADVERTISING_ID_PARAM), instance2.f45, instance2.f46);
            m182("4.8.18.413", instance.getString(AppsFlyerProperties.AF_KEY), instance.getString("KSAppsFlyerId"), instance.getString("uid"));
            try {
                int i = packageManager.getPackageInfo(str, 0).versionCode;
                m186(str, String.valueOf(i), instance.getString(AppsFlyerProperties.CHANNEL), instance.getString("preInstallName"));
            } catch (Throwable th2) {
            }
            instance.set("remote_debug_static_data", this.f216.toString());
        }
        try {
            this.f216.put("launch_counter", this.f221);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    /* renamed from: ᐝ  reason: contains not printable characters */
    private synchronized void m189() {
        this.f213 = null;
        this.f213 = new JSONArray();
        this.f211 = 0;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final synchronized void m199() {
        this.f222 = false;
        m189();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ʻ  reason: contains not printable characters */
    public final void m190() {
        this.f225 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱॱ  reason: contains not printable characters */
    public final boolean m201() {
        return this.f218;
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static void m187(String str, PackageManager packageManager) {
        try {
            if (f201 == null) {
                f201 = new C0001r();
            }
            f201.m183(str, packageManager);
            if (f201 == null) {
                f201 = new C0001r();
            }
            String r0 = f201.m181();
            m mVar = new m((Context) null, AppsFlyerLib.getInstance().isTrackingStopped());
            mVar.f181 = r0;
            mVar.m159();
            mVar.execute(new String[]{new StringBuilder().append(ServerConfigHandler.getUrl("https://monitorsdk.%s/remote-debug?app_id=")).append(str).toString()});
        } catch (Throwable th) {
        }
    }
}

package com.appsflyer.share;

import android.content.Context;
import android.os.AsyncTask;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.ServerConfigHandler;
import com.appsflyer.ServerParameters;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CrossPromotionHelper {
    public static void trackAndOpenStore(Context context, String str, String str2) {
        trackAndOpenStore(context, str, str2, (Map<String, String>) null);
    }

    public static void trackAndOpenStore(Context context, String str, String str2, Map<String, String> map) {
        LinkGenerator r0 = m210(context, str, str2, map, ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, track And Open Store is disabled", true);
            return;
        }
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        hashMap.put("af_campaign", str2);
        AppsFlyerLib.getInstance().trackEvent(context, "af_cross_promotion", hashMap);
        new e(new a(), context, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{r0.generateLink()});
    }

    public static void trackCrossPromoteImpression(Context context, String str, String str2) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, Promote Impression is disabled", true);
            return;
        }
        LinkGenerator r0 = m210(context, str, str2, (Map<String, String>) null, ServerConfigHandler.getUrl("https://impression.%s"));
        new e((a) null, (Context) null, AppsFlyerLib.getInstance().isTrackingStopped()).execute(new String[]{r0.generateLink()});
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static LinkGenerator m210(Context context, String str, String str2, Map<String, String> map, String str3) {
        LinkGenerator linkGenerator = new LinkGenerator("af_cross_promotion");
        linkGenerator.m215(str3).m214(str).addParameter(Constants.URL_SITE_ID, context.getPackageName());
        if (str2 != null) {
            linkGenerator.setCampaign(str2);
        }
        if (map != null) {
            linkGenerator.addParameters(map);
        }
        String string = AppsFlyerProperties.getInstance().getString(ServerParameters.ADVERTISING_ID_PARAM);
        if (string != null) {
            linkGenerator.addParameter(Constants.URL_ADVERTISING_ID, string);
        }
        return linkGenerator;
    }

    static class e extends AsyncTask<String, Void, Void> {

        /* renamed from: ˊ  reason: contains not printable characters */
        private a f236;

        /* renamed from: ˏ  reason: contains not printable characters */
        private boolean f237;

        /* renamed from: ॱ  reason: contains not printable characters */
        private WeakReference<Context> f238;

        e(a aVar, Context context, boolean z) {
            this.f236 = aVar;
            this.f238 = new WeakReference<>(context);
            this.f237 = z;
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00a8  */
        /* renamed from: ˊ  reason: contains not printable characters */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.String... r8) {
            /*
                r7 = this;
                r2 = 0
                boolean r0 = r7.f237
                if (r0 == 0) goto L_0x0006
            L_0x0005:
                return r2
            L_0x0006:
                r0 = 0
                r1 = r8[r0]     // Catch:{ Throwable -> 0x00b1, all -> 0x00ac }
                java.net.URL r0 = new java.net.URL     // Catch:{ Throwable -> 0x00b1, all -> 0x00ac }
                r0.<init>(r1)     // Catch:{ Throwable -> 0x00b1, all -> 0x00ac }
                java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Throwable -> 0x00b1, all -> 0x00ac }
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Throwable -> 0x00b1, all -> 0x00ac }
                r3 = 10000(0x2710, float:1.4013E-41)
                r0.setConnectTimeout(r3)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r3 = 0
                r0.setInstanceFollowRedirects(r3)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                int r3 = r0.getResponseCode()     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r4 = 200(0xc8, float:2.8E-43)
                if (r3 != r4) goto L_0x0039
                java.lang.String r3 = "Cross promotion impressions success: "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.String r1 = r3.concat(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r3 = 0
                com.appsflyer.AFLogger.afInfoLog(r1, r3)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
            L_0x0033:
                if (r0 == 0) goto L_0x0005
                r0.disconnect()
                goto L_0x0005
            L_0x0039:
                r4 = 301(0x12d, float:4.22E-43)
                if (r3 == r4) goto L_0x0041
                r4 = 302(0x12e, float:4.23E-43)
                if (r3 != r4) goto L_0x0086
            L_0x0041:
                java.lang.String r3 = "Cross promotion redirection success: "
                java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.String r1 = r3.concat(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r3 = 0
                com.appsflyer.AFLogger.afInfoLog(r1, r3)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                com.appsflyer.share.a r1 = r7.f236     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                if (r1 == 0) goto L_0x0033
                java.lang.ref.WeakReference<android.content.Context> r1 = r7.f238     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.Object r1 = r1.get()     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                if (r1 == 0) goto L_0x0033
                java.lang.String r1 = "Location"
                java.lang.String r1 = r0.getHeaderField(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                com.appsflyer.share.a r3 = r7.f236     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r3.m217(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                com.appsflyer.share.a r3 = r7.f236     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.ref.WeakReference<android.content.Context> r1 = r7.f238     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.Object r1 = r1.get()     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                android.content.Context r1 = (android.content.Context) r1     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                r3.m216(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                goto L_0x0033
            L_0x0074:
                r1 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
            L_0x0078:
                java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x00ae }
                r4 = 1
                com.appsflyer.AFLogger.afErrorLog(r3, r0, r4)     // Catch:{ all -> 0x00ae }
                if (r1 == 0) goto L_0x0005
                r1.disconnect()
                goto L_0x0005
            L_0x0086:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.String r5 = "call to "
                r4.<init>(r5)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.String r4 = " failed: "
                java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                com.appsflyer.AFLogger.afInfoLog(r1)     // Catch:{ Throwable -> 0x0074, all -> 0x00a3 }
                goto L_0x0033
            L_0x00a3:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L_0x00a6:
                if (r2 == 0) goto L_0x00ab
                r2.disconnect()
            L_0x00ab:
                throw r0
            L_0x00ac:
                r0 = move-exception
                goto L_0x00a6
            L_0x00ae:
                r0 = move-exception
                r2 = r1
                goto L_0x00a6
            L_0x00b1:
                r0 = move-exception
                r1 = r2
                goto L_0x0078
            */
            throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.share.CrossPromotionHelper.e.doInBackground(java.lang.String[]):java.lang.Void");
        }
    }
}

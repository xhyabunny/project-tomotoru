package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;

final class i {

    static final class c {

        /* renamed from: ˎ  reason: contains not printable characters */
        static final i f148 = new i();
    }

    i() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        if (1 != r9.getType()) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
        r3 = "WIFI";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
        if (r1.isEmpty() != false) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0065, code lost:
        if (r9.getType() != 0) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0067, code lost:
        r3 = "MOBILE";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006a, code lost:
        r3 = android.support.v4.os.EnvironmentCompat.MEDIA_UNKNOWN;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0048 A[SYNTHETIC, Splitter:B:21:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0055 A[Catch:{ Throwable -> 0x00d7 }] */
    /* renamed from: ॱ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.appsflyer.i.b m141(@android.support.annotation.NonNull android.content.Context r11) {
        /*
            r1 = 0
            r4 = 0
            r2 = 1
            java.lang.String r3 = "unknown"
            java.lang.String r0 = "connectivity"
            java.lang.Object r0 = r11.getSystemService(r0)     // Catch:{ Throwable -> 0x00c6 }
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0     // Catch:{ Throwable -> 0x00c6 }
            if (r0 == 0) goto L_0x00c2
            r5 = 21
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00c6 }
            if (r5 > r6) goto L_0x0074
            android.net.Network[] r7 = r0.getAllNetworks()     // Catch:{ Throwable -> 0x00c6 }
            int r8 = r7.length     // Catch:{ Throwable -> 0x00c6 }
            r6 = r4
        L_0x001b:
            if (r6 >= r8) goto L_0x0071
            r5 = r7[r6]     // Catch:{ Throwable -> 0x00c6 }
            android.net.NetworkInfo r9 = r0.getNetworkInfo(r5)     // Catch:{ Throwable -> 0x00c6 }
            if (r9 == 0) goto L_0x005f
            boolean r5 = r9.isConnectedOrConnecting()     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x005f
            r5 = r2
        L_0x002c:
            if (r5 == 0) goto L_0x006d
            int r0 = r9.getType()     // Catch:{ Throwable -> 0x00c6 }
            if (r2 != r0) goto L_0x0061
            java.lang.String r3 = "WIFI"
        L_0x0036:
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r11.getSystemService(r0)     // Catch:{ Throwable -> 0x00c6 }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x00c6 }
            java.lang.String r2 = r0.getSimOperatorName()     // Catch:{ Throwable -> 0x00c6 }
            java.lang.String r1 = r0.getNetworkOperatorName()     // Catch:{ Throwable -> 0x00d0 }
            if (r1 == 0) goto L_0x004e
            boolean r4 = r1.isEmpty()     // Catch:{ Throwable -> 0x00d7 }
            if (r4 == 0) goto L_0x00de
        L_0x004e:
            r4 = 2
            int r0 = r0.getPhoneType()     // Catch:{ Throwable -> 0x00d7 }
            if (r4 != r0) goto L_0x00de
            java.lang.String r0 = "CDMA"
        L_0x0057:
            r1 = r2
            r2 = r3
        L_0x0059:
            com.appsflyer.i$b r3 = new com.appsflyer.i$b
            r3.<init>(r2, r0, r1)
            return r3
        L_0x005f:
            r5 = r4
            goto L_0x002c
        L_0x0061:
            int r0 = r9.getType()     // Catch:{ Throwable -> 0x00c6 }
            if (r0 != 0) goto L_0x006a
            java.lang.String r3 = "MOBILE"
            goto L_0x0036
        L_0x006a:
            java.lang.String r3 = "unknown"
            goto L_0x0036
        L_0x006d:
            int r5 = r6 + 1
            r6 = r5
            goto L_0x001b
        L_0x0071:
            java.lang.String r3 = "unknown"
            goto L_0x0036
        L_0x0074:
            r5 = 1
            android.net.NetworkInfo r5 = r0.getNetworkInfo(r5)     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x0087
            boolean r5 = r5.isConnectedOrConnecting()     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x0087
            r5 = r2
        L_0x0082:
            if (r5 == 0) goto L_0x0089
            java.lang.String r3 = "WIFI"
            goto L_0x0036
        L_0x0087:
            r5 = r4
            goto L_0x0082
        L_0x0089:
            r5 = 0
            android.net.NetworkInfo r5 = r0.getNetworkInfo(r5)     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x009c
            boolean r5 = r5.isConnectedOrConnecting()     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x009c
            r5 = r2
        L_0x0097:
            if (r5 == 0) goto L_0x009e
            java.lang.String r3 = "MOBILE"
            goto L_0x0036
        L_0x009c:
            r5 = r4
            goto L_0x0097
        L_0x009e:
            android.net.NetworkInfo r5 = r0.getActiveNetworkInfo()     // Catch:{ Throwable -> 0x00c6 }
            if (r5 == 0) goto L_0x00b6
            boolean r0 = r5.isConnectedOrConnecting()     // Catch:{ Throwable -> 0x00c6 }
            if (r0 == 0) goto L_0x00b6
            r0 = r2
        L_0x00ab:
            if (r0 == 0) goto L_0x00c2
            int r0 = r5.getType()     // Catch:{ Throwable -> 0x00c6 }
            if (r2 != r0) goto L_0x00b8
            java.lang.String r3 = "WIFI"
            goto L_0x0036
        L_0x00b6:
            r0 = r4
            goto L_0x00ab
        L_0x00b8:
            int r0 = r5.getType()     // Catch:{ Throwable -> 0x00c6 }
            if (r0 != 0) goto L_0x00c2
            java.lang.String r3 = "MOBILE"
            goto L_0x0036
        L_0x00c2:
            java.lang.String r3 = "unknown"
            goto L_0x0036
        L_0x00c6:
            r0 = move-exception
            r2 = r3
            r3 = r0
            r0 = r1
        L_0x00ca:
            java.lang.String r4 = "Exception while collecting network info. "
            com.appsflyer.AFLogger.afErrorLog(r4, r3)
            goto L_0x0059
        L_0x00d0:
            r0 = move-exception
            r10 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r10
            goto L_0x00ca
        L_0x00d7:
            r0 = move-exception
            r10 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r10
            goto L_0x00ca
        L_0x00de:
            r0 = r1
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.i.m141(android.content.Context):com.appsflyer.i$b");
    }

    static final class b {

        /* renamed from: ˊ  reason: contains not printable characters */
        private final String f145;

        /* renamed from: ˋ  reason: contains not printable characters */
        private final String f146;

        /* renamed from: ॱ  reason: contains not printable characters */
        private final String f147;

        b(@NonNull String str, @Nullable String str2, @Nullable String str3) {
            this.f146 = str;
            this.f145 = str2;
            this.f147 = str3;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ˋ  reason: contains not printable characters */
        public final String m143() {
            return this.f146;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        /* renamed from: ॱ  reason: contains not printable characters */
        public final String m145() {
            return this.f145;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        /* renamed from: ˎ  reason: contains not printable characters */
        public final String m144() {
            return this.f147;
        }

        b() {
        }

        /* renamed from: ˋ  reason: contains not printable characters */
        static void m142(Context context) {
            Context applicationContext = context.getApplicationContext();
            AFLogger.afInfoLog("onBecameBackground");
            AppsFlyerLib.getInstance().m70();
            AFLogger.afInfoLog("callStatsBackground background call");
            AppsFlyerLib.getInstance().m71((WeakReference<Context>) new WeakReference(applicationContext));
            C0001r r1 = C0001r.m185();
            if (r1.m201()) {
                r1.m196();
                if (applicationContext != null) {
                    C0001r.m187(applicationContext.getPackageName(), applicationContext.getPackageManager());
                }
                r1.m192();
            } else {
                AFLogger.afDebugLog("RD status is OFF");
            }
            AFExecutor.getInstance().m1();
        }
    }
}

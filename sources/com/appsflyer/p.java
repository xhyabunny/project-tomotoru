package com.appsflyer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.io.File;
import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.util.UUID;

final class p {

    /* renamed from: ˎ  reason: contains not printable characters */
    private static String f190 = null;

    p() {
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    public static synchronized String m174(WeakReference<Context> weakReference) {
        String str;
        String obj;
        String str2 = null;
        synchronized (p.class) {
            if (weakReference.get() == null) {
                str = f190;
            } else {
                if (f190 == null) {
                    if (weakReference.get() != null) {
                        str2 = weakReference.get().getSharedPreferences("appsflyer-data", 0).getString("AF_INSTALLATION", (String) null);
                    }
                    if (str2 != null) {
                        f190 = str2;
                    } else {
                        try {
                            File file = new File(weakReference.get().getFilesDir(), "AF_INSTALLATION");
                            if (!file.exists()) {
                                if (Build.VERSION.SDK_INT >= 9) {
                                    obj = new StringBuilder().append(System.currentTimeMillis()).append("-").append(Math.abs(new SecureRandom().nextLong())).toString();
                                } else {
                                    obj = UUID.randomUUID().toString();
                                }
                                f190 = obj;
                            } else {
                                f190 = m173(file);
                                file.delete();
                            }
                            String str3 = f190;
                            SharedPreferences.Editor edit = weakReference.get().getSharedPreferences("appsflyer-data", 0).edit();
                            edit.putString("AF_INSTALLATION", str3);
                            if (Build.VERSION.SDK_INT >= 9) {
                                edit.apply();
                            } else {
                                edit.commit();
                            }
                        } catch (Exception e) {
                            AFLogger.afErrorLog("Error getting AF unique ID", e);
                        }
                    }
                    if (f190 != null) {
                        AppsFlyerProperties.getInstance().set("uid", f190);
                    }
                }
                str = f190;
            }
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0033 A[SYNTHETIC, Splitter:B:21:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0042 A[SYNTHETIC, Splitter:B:28:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x004d  */
    /* renamed from: ॱ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m173(java.io.File r7) {
        /*
            r1 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x0027, all -> 0x003e }
            java.lang.String r0 = "r"
            r2.<init>(r7, r0)     // Catch:{ IOException -> 0x0027, all -> 0x003e }
            long r4 = r2.length()     // Catch:{ IOException -> 0x0053 }
            int r0 = (int) r4     // Catch:{ IOException -> 0x0053 }
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x0053 }
            r2.readFully(r0)     // Catch:{ IOException -> 0x0058 }
            r2.close()     // Catch:{ IOException -> 0x0058 }
            r2.close()     // Catch:{ IOException -> 0x0020 }
        L_0x0018:
            java.lang.String r1 = new java.lang.String
            if (r0 == 0) goto L_0x004d
        L_0x001c:
            r1.<init>(r0)
            return r1
        L_0x0020:
            r1 = move-exception
            java.lang.String r2 = "Exception while trying to close the InstallationFile"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x0018
        L_0x0027:
            r0 = move-exception
            r2 = r1
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x002c:
            java.lang.String r3 = "Exception while reading InstallationFile: "
            com.appsflyer.AFLogger.afErrorLog(r3, r1)     // Catch:{ all -> 0x0051 }
            if (r2 == 0) goto L_0x0018
            r2.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x0018
        L_0x0037:
            r1 = move-exception
            java.lang.String r2 = "Exception while trying to close the InstallationFile"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x0018
        L_0x003e:
            r0 = move-exception
            r2 = r1
        L_0x0040:
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ IOException -> 0x0046 }
        L_0x0045:
            throw r0
        L_0x0046:
            r1 = move-exception
            java.lang.String r2 = "Exception while trying to close the InstallationFile"
            com.appsflyer.AFLogger.afErrorLog(r2, r1)
            goto L_0x0045
        L_0x004d:
            r0 = 0
            byte[] r0 = new byte[r0]
            goto L_0x001c
        L_0x0051:
            r0 = move-exception
            goto L_0x0040
        L_0x0053:
            r0 = move-exception
            r6 = r0
            r0 = r1
            r1 = r6
            goto L_0x002c
        L_0x0058:
            r1 = move-exception
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.p.m173(java.io.File):java.lang.String");
    }
}

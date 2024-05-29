package com.appsflyer.cache;

import android.content.Context;
import android.util.Log;
import com.appsflyer.AppsFlyerLib;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    public static final String AF_CACHE_DIR = "AFRequestCache";
    public static final int CACHE_MAX_SIZE = 40;

    /* renamed from: ˋ  reason: contains not printable characters */
    private static CacheManager f108 = new CacheManager();

    public static CacheManager getInstance() {
        return f108;
    }

    private CacheManager() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0033 A[SYNTHETIC, Splitter:B:15:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ae A[SYNTHETIC, Splitter:B:30:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[Catch:{ Exception -> 0x0029, all -> 0x00a8 }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cacheRequest(com.appsflyer.cache.RequestCacheData r8, android.content.Context r9) {
        /*
            r7 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.File r2 = r9.getFilesDir()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.lang.String r3 = "AFRequestCache"
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            if (r2 != 0) goto L_0x0016
            r1.mkdir()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
        L_0x0015:
            return
        L_0x0016:
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            if (r1 == 0) goto L_0x0039
            int r1 = r1.length     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            r2 = 40
            if (r1 <= r2) goto L_0x0039
            java.lang.String r1 = "AppsFlyer_4.8.18"
            java.lang.String r2 = "reached cache limit, not caching request"
            android.util.Log.i(r1, r2)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            goto L_0x0015
        L_0x0029:
            r1 = move-exception
        L_0x002a:
            java.lang.String r1 = "AppsFlyer_4.8.18"
            java.lang.String r2 = "Could not cache request"
            android.util.Log.i(r1, r2)     // Catch:{ all -> 0x00b6 }
            if (r0 == 0) goto L_0x0015
            r0.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x0015
        L_0x0037:
            r0 = move-exception
            goto L_0x0015
        L_0x0039:
            java.lang.String r1 = "AppsFlyer_4.8.18"
            java.lang.String r2 = "caching request..."
            android.util.Log.i(r1, r2)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.File r3 = r9.getFilesDir()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.lang.String r4 = "AFRequestCache"
            r1.<init>(r3, r4)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.lang.String r3 = java.lang.Long.toString(r4)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            r2.createNewFile()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.OutputStreamWriter r1 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.lang.String r2 = r2.getPath()     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            r4 = 1
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0029, all -> 0x00a8 }
            java.lang.String r0 = "version="
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            java.lang.String r0 = r8.getVersion()     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r0 = 10
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            java.lang.String r0 = "url="
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            java.lang.String r0 = r8.getRequestURL()     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r0 = 10
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            java.lang.String r0 = "data="
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            java.lang.String r0 = r8.getPostData()     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r0 = 10
            r1.write(r0)     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r1.flush()     // Catch:{ Exception -> 0x00bb, all -> 0x00b4 }
            r1.close()     // Catch:{ IOException -> 0x00a5 }
            goto L_0x0015
        L_0x00a5:
            r0 = move-exception
            goto L_0x0015
        L_0x00a8:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x00ac:
            if (r1 == 0) goto L_0x00b1
            r1.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00b1:
            throw r0
        L_0x00b2:
            r1 = move-exception
            goto L_0x00b1
        L_0x00b4:
            r0 = move-exception
            goto L_0x00ac
        L_0x00b6:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x00ac
        L_0x00bb:
            r0 = move-exception
            r0 = r1
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.cache.CacheManager.cacheRequest(com.appsflyer.cache.RequestCacheData, android.content.Context):void");
    }

    public List<RequestCacheData> getCachedRequests(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(context.getFilesDir(), AF_CACHE_DIR);
            if (!file.exists()) {
                file.mkdir();
            } else {
                for (File file2 : file.listFiles()) {
                    Log.i(AppsFlyerLib.LOG_TAG, new StringBuilder("Found cached request").append(file2.getName()).toString());
                    arrayList.add(m116(file2));
                }
            }
        } catch (Exception e) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not cache request");
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0024 A[SYNTHETIC, Splitter:B:10:0x0024] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002d A[SYNTHETIC, Splitter:B:16:0x002d] */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.appsflyer.cache.RequestCacheData m116(java.io.File r6) {
        /*
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x0020, all -> 0x0029 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0020, all -> 0x0029 }
            long r4 = r6.length()     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            int r0 = (int) r4     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            char[] r3 = new char[r0]     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            r2.read(r3)     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            com.appsflyer.cache.RequestCacheData r0 = new com.appsflyer.cache.RequestCacheData     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            java.lang.String r3 = r6.getName()     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            r0.setCacheKey(r3)     // Catch:{ Exception -> 0x0039, all -> 0x0037 }
            r2.close()     // Catch:{ IOException -> 0x0031 }
        L_0x001f:
            return r0
        L_0x0020:
            r0 = move-exception
            r0 = r1
        L_0x0022:
            if (r0 == 0) goto L_0x0027
            r0.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0027:
            r0 = r1
            goto L_0x001f
        L_0x0029:
            r0 = move-exception
            r2 = r1
        L_0x002b:
            if (r2 == 0) goto L_0x0030
            r2.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0030:
            throw r0
        L_0x0031:
            r1 = move-exception
            goto L_0x001f
        L_0x0033:
            r0 = move-exception
            goto L_0x0027
        L_0x0035:
            r1 = move-exception
            goto L_0x0030
        L_0x0037:
            r0 = move-exception
            goto L_0x002b
        L_0x0039:
            r0 = move-exception
            r0 = r2
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.cache.CacheManager.m116(java.io.File):com.appsflyer.cache.RequestCacheData");
    }

    public void init(Context context) {
        try {
            if (!new File(context.getFilesDir(), AF_CACHE_DIR).exists()) {
                new File(context.getFilesDir(), AF_CACHE_DIR).mkdir();
            }
        } catch (Exception e) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not create cache directory");
        }
    }

    public void deleteRequest(String str, Context context) {
        File file = new File(new File(context.getFilesDir(), AF_CACHE_DIR), str);
        Log.i(AppsFlyerLib.LOG_TAG, new StringBuilder("Deleting ").append(str).append(" from cache").toString());
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                Log.i(AppsFlyerLib.LOG_TAG, new StringBuilder("Could not delete ").append(str).append(" from cache").toString(), e);
            }
        }
    }

    public void clearCache(Context context) {
        try {
            File file = new File(context.getFilesDir(), AF_CACHE_DIR);
            if (!file.exists()) {
                file.mkdir();
                return;
            }
            for (File file2 : file.listFiles()) {
                Log.i(AppsFlyerLib.LOG_TAG, new StringBuilder("Found cached request").append(file2.getName()).toString());
                deleteRequest(m116(file2).getCacheKey(), context);
            }
        } catch (Exception e) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not cache request");
        }
    }
}

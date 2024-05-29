package com.appsflyer;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import javax.security.auth.x500.X500Principal;

class AFKeystoreWrapper {

    /* renamed from: ˊ  reason: contains not printable characters */
    private int f7;

    /* renamed from: ˋ  reason: contains not printable characters */
    private KeyStore f8;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f9;

    /* renamed from: ˏ  reason: contains not printable characters */
    private final Object f10 = new Object();

    /* renamed from: ॱ  reason: contains not printable characters */
    private Context f11;

    public AFKeystoreWrapper(Context context) {
        this.f11 = context;
        this.f9 = "";
        this.f7 = 0;
        AFLogger.afInfoLog("Initialising KeyStore..");
        try {
            this.f8 = KeyStore.getInstance("AndroidKeyStore");
            this.f8.load((KeyStore.LoadStoreParameter) null);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            AFLogger.afErrorLog("Couldn't load keystore instance of type: AndroidKeyStore", e);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m7(String str) {
        this.f9 = str;
        this.f7 = 0;
        m5(m4());
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* renamed from: ॱ  reason: contains not printable characters */
    final void m10() {
        /*
            r4 = this;
            java.lang.String r0 = r4.m4()
            java.lang.Object r1 = r4.f10
            monitor-enter(r1)
            int r2 = r4.f7     // Catch:{ all -> 0x004d }
            int r2 = r2 + 1
            r4.f7 = r2     // Catch:{ all -> 0x004d }
            java.lang.String r2 = "Deleting key with alias: "
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x004d }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x004d }
            com.appsflyer.AFLogger.afInfoLog(r2)     // Catch:{ all -> 0x004d }
            java.lang.Object r2 = r4.f10     // Catch:{ KeyStoreException -> 0x002f }
            monitor-enter(r2)     // Catch:{ KeyStoreException -> 0x002f }
            java.security.KeyStore r3 = r4.f8     // Catch:{ all -> 0x002c }
            r3.deleteEntry(r0)     // Catch:{ all -> 0x002c }
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
        L_0x0023:
            monitor-exit(r1)     // Catch:{ all -> 0x004d }
            java.lang.String r0 = r4.m4()
            r4.m5(r0)
            return
        L_0x002c:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ KeyStoreException -> 0x002f }
            throw r0     // Catch:{ KeyStoreException -> 0x002f }
        L_0x002f:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x004d }
            java.lang.String r3 = "Exception "
            r2.<init>(r3)     // Catch:{ all -> 0x004d }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x004d }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x004d }
            java.lang.String r3 = " occurred"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x004d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x004d }
            com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ all -> 0x004d }
            goto L_0x0023
        L_0x004d:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AFKeystoreWrapper.m10():void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        r4 = r0.split(",");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if (r4.length != 3) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        com.appsflyer.AFLogger.afInfoLog("Found a matching AF key with alias:\n".concat(java.lang.String.valueOf(r0)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r0 = r4[1].trim().split("=");
        r2 = r4[2].trim().split("=");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0058, code lost:
        if (r0.length != 2) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005b, code lost:
        if (r2.length != 2) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005d, code lost:
        r8.f9 = r0[1].trim();
        r8.f7 = java.lang.Integer.parseInt(r2[1].trim());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0097, code lost:
        r0 = th;
     */
    /* renamed from: ˎ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m9() {
        /*
            r8 = this;
            r7 = 2
            r1 = 1
            r2 = 0
            java.lang.Object r3 = r8.f10
            monitor-enter(r3)
            java.security.KeyStore r0 = r8.f8     // Catch:{ all -> 0x0094 }
            if (r0 == 0) goto L_0x0099
            java.security.KeyStore r0 = r8.f8     // Catch:{ Throwable -> 0x0077 }
            java.util.Enumeration r4 = r0.aliases()     // Catch:{ Throwable -> 0x0077 }
        L_0x0010:
            boolean r0 = r4.hasMoreElements()     // Catch:{ Throwable -> 0x0077 }
            if (r0 == 0) goto L_0x0075
            java.lang.Object r0 = r4.nextElement()     // Catch:{ Throwable -> 0x0077 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0077 }
            if (r0 == 0) goto L_0x0010
            java.lang.String r5 = "com.appsflyer"
            boolean r5 = r0.startsWith(r5)     // Catch:{ Throwable -> 0x0077 }
            if (r5 == 0) goto L_0x0010
            java.lang.String r4 = ","
            java.lang.String[] r4 = r0.split(r4)     // Catch:{ Throwable -> 0x0077 }
            int r5 = r4.length     // Catch:{ Throwable -> 0x0077 }
            r6 = 3
            if (r5 != r6) goto L_0x0075
            java.lang.String r5 = "Found a matching AF key with alias:\n"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0077 }
            java.lang.String r0 = r5.concat(r0)     // Catch:{ Throwable -> 0x0077 }
            com.appsflyer.AFLogger.afInfoLog(r0)     // Catch:{ Throwable -> 0x0077 }
            r0 = 1
            r0 = r4[r0]     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r2 = "="
            java.lang.String[] r0 = r0.split(r2)     // Catch:{ Throwable -> 0x0097 }
            r2 = 2
            r2 = r4[r2]     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r2 = r2.trim()     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r4 = "="
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ Throwable -> 0x0097 }
            int r4 = r0.length     // Catch:{ Throwable -> 0x0097 }
            if (r4 != r7) goto L_0x0073
            int r4 = r2.length     // Catch:{ Throwable -> 0x0097 }
            if (r4 != r7) goto L_0x0073
            r4 = 1
            r0 = r0[r4]     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0097 }
            r8.f9 = r0     // Catch:{ Throwable -> 0x0097 }
            r0 = 1
            r0 = r2[r0]     // Catch:{ Throwable -> 0x0097 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x0097 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0097 }
            r8.f7 = r0     // Catch:{ Throwable -> 0x0097 }
        L_0x0073:
            monitor-exit(r3)     // Catch:{ all -> 0x0094 }
            return r1
        L_0x0075:
            r1 = r2
            goto L_0x0073
        L_0x0077:
            r0 = move-exception
            r1 = r2
        L_0x0079:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0094 }
            java.lang.String r4 = "Couldn't list KeyStore Aliases: "
            r2.<init>(r4)     // Catch:{ all -> 0x0094 }
            java.lang.Class r4 = r0.getClass()     // Catch:{ all -> 0x0094 }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x0094 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0094 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0094 }
            com.appsflyer.AFLogger.afErrorLog(r2, r0)     // Catch:{ all -> 0x0094 }
            goto L_0x0073
        L_0x0094:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0097:
            r0 = move-exception
            goto L_0x0079
        L_0x0099:
            r1 = r2
            goto L_0x0073
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AFKeystoreWrapper.m9():boolean");
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private void m5(String str) {
        boolean z = true;
        AFLogger.afInfoLog("Creating a new key with alias: ".concat(String.valueOf(str)));
        try {
            Calendar instance = Calendar.getInstance();
            Calendar instance2 = Calendar.getInstance();
            instance2.add(1, 5);
            AlgorithmParameterSpec algorithmParameterSpec = null;
            synchronized (this.f10) {
                if (!this.f8.containsAlias(str)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        algorithmParameterSpec = new KeyGenParameterSpec.Builder(str, 3).setCertificateSubject(new X500Principal("CN=AndroidSDK, O=AppsFlyer")).setCertificateSerialNumber(BigInteger.ONE).setCertificateNotBefore(instance.getTime()).setCertificateNotAfter(instance2.getTime()).build();
                    } else if (Build.VERSION.SDK_INT >= 18) {
                        if (!"OPPO".equals(Build.BRAND)) {
                            z = false;
                        }
                        if (!z) {
                            algorithmParameterSpec = new KeyPairGeneratorSpec.Builder(this.f11).setAlias(str).setSubject(new X500Principal("CN=AndroidSDK, O=AppsFlyer")).setSerialNumber(BigInteger.ONE).setStartDate(instance.getTime()).setEndDate(instance2.getTime()).build();
                        }
                    }
                    KeyPairGenerator instance3 = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                    instance3.initialize(algorithmParameterSpec);
                    instance3.generateKeyPair();
                } else {
                    AFLogger.afInfoLog("Alias already exists: ".concat(String.valueOf(str)));
                }
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog(new StringBuilder("Exception ").append(th.getMessage()).append(" occurred").toString(), th);
        }
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private String m4() {
        StringBuilder sb = new StringBuilder();
        sb.append("com.appsflyer,");
        synchronized (this.f10) {
            sb.append("KSAppsFlyerId=").append(this.f9).append(",");
            sb.append("KSAppsFlyerRICounter=").append(this.f7);
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final String m6() {
        String str;
        synchronized (this.f10) {
            str = this.f9;
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final int m8() {
        int i;
        synchronized (this.f10) {
            i = this.f7;
        }
        return i;
    }
}

package com.appsflyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ls.sakana.SakanaView;

final class b {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static long f98 = 3649797082317871566L;

    /* renamed from: ˎ  reason: contains not printable characters */
    private static char[] f99 = {'a', 22944, 46072, 3352, 26455, 49519, 6832, 29836, 52740, 10331, 33376, 56255, 13784, 36638, 59691, 17276, 40089, 63104, 20520, 43567, 1140, 23939, 47044, 4586, 27455, 50544, 7829, 30967, 53993, 11320, 34373, 57237, 14757, 37884, '1', '0', 3346, 21724, 48782, 'x', 27262, 52256, 6130, 31204, 50047, 9521, 36664, 55033, 14475, 33392, 58434, 19978, 37368, 64415, 'b', 22959, 46078, 3343, 26457, 49507, 6884, 29847, 52809, 10330, 33337, 56297, 13769, 36628, 59773, 17185, 40148, 63176, 20552, 43567, 1068, 23939, 46976, 4530, 27444, 50471, 7901, 30940, 53946, 11366, 34325, 57283, 'a', 22944, 46072, 3352, 26455, 49519, 6832, 29836, 52767, 10317, 33314, 56216, 13769, 36610, 59696, 17271, 40082, 63191, 20529, 43563, 1142, 23943, 47059, 4583, 27426, 'a', 22944, 46072, 3352, 26455, 49519, 6832, 29836, 52760, 10335, 33406, 56254, 13791, 36631, 59702, 17271, 40142, 63229, 20505, 43556, 1131, 23945, 47046, 17618, 7443, 63307, 18859, 9188, 34268, 24067, 12351, 35500, 27902, 50833, 40744, 29032, 52156, 44441, 1986, 55303, 45692, 5308, 61074, 37822, 51839, 8231, 40647, 62600, 21168, 35183, 59219, 24001, 48004, 4519, 18475, 42530, 7387, 31474, '.', '\\', 23008, 53155, 38502, 31779, 49858, 43231, 3750, 54642, 47941, 461, 59290, 19893, 5239, 64008, 16589, '/', 22957, 46077, 3337, 26448, 49507, 3543, 21533, 48714, 207, 27314, 52437, 5893, 31093, 50083, 9710, 'C', 22950, 46073, 3337, 26451, 49525, 6817, 29903, 52789, 10310, 33391, 56255, 13784, 36610, 59693, 17277, 40078, 8143, 17958, 44156, 4751, 30932, 57067, 1405, 27487, 53654, 14231, 40418, 50230, 10837, 37087, 63150, 23802, 33546, 59727, 20368, 46563, 7137, 16910, 43081, 3683, 29945, 56032, 268, 26439, 52585, 13311, 39368, 49155, 9770, 35938, 62117, 22743, 48920, 58656, 19315, 45521, 6041, 41089, 63821, 4878, 44485, 51120, 24979, 47686, 54309, 28410, 34996, 8847, 31576, 38159, 12256, 18898, 58264, 15471, 22059, 61691, 2776, 42135, 64879, 5948, 45335, 'C', 22950, 46073, 3337, 26451, 49525, 6817, 29903, 52770, 10331, 33386, 56246, 13773, 36629, 59696, 17239, 40088, 63181, 20505, 43578, 1132, 23951, 47067, 4588, 37519, 52070, 8508, 40911, 62868, 21419, 34877, 58882, 23767, 47745, 4266, 18808, 42756, 7583, 31743, 53694, 3663, 25611, 49872, 14560, 38565, 53066, 9497, 33643, 63988, 22450, 35921, 59931, 16430, 48891, 5325, 19788, 43872, 307, 32765, 54723, 12884, 26743, 50750, 15566, 39561, 62275, 10604, 34620, 64975, 23493, 45549};

    /* renamed from: ˏ  reason: contains not printable characters */
    private static int f100 = 1;

    /* renamed from: ॱ  reason: contains not printable characters */
    private static int f101 = 0;

    b() {
    }

    @Nullable
    /* renamed from: ˎ  reason: contains not printable characters */
    static String m101(Context context, long j) {
        String intern;
        String intern2;
        String intern3;
        String intern4;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        if (m105(m98(34, 0, 0).intern())) {
            intern = m98(1, 34, 0).intern();
        } else {
            intern = m98(1, 35, 0).intern();
            int i = f100 + 3;
            f101 = i % 128;
            if (i % 2 != 0) {
            }
        }
        sb2.append(intern);
        StringBuilder sb4 = new StringBuilder();
        String packageName = context.getPackageName();
        String r0 = m99(packageName);
        sb2.append(m98(1, 34, 0).intern());
        sb4.append(r0);
        switch (m104(context) == null ? '_' : 19) {
            case 19:
                sb2.append(m98(1, 34, 0).intern());
                sb4.append(packageName);
                break;
            default:
                sb2.append(m98(1, 35, 0).intern());
                sb4.append(packageName);
                break;
        }
        String r02 = m100(context);
        if (r02 == null) {
            int i2 = f100 + 65;
            f101 = i2 % 128;
            switch (i2 % 2 != 0 ? '5' : '3') {
                case '3':
                    sb2.append(m98(1, 35, 0).intern());
                    sb4.append(packageName);
                    break;
                default:
                    sb2.append(m98(0, 14, 0).intern());
                    sb4.append(packageName);
                    break;
            }
        } else {
            sb2.append(m98(1, 34, 0).intern());
            sb4.append(r02);
        }
        sb4.append(m102(context, packageName));
        sb.append(sb4.toString());
        try {
            long j2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(m98(18, 36, 3435).intern(), Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            sb.append(simpleDateFormat.format(new Date(j2)));
            int i3 = f100 + 13;
            f101 = i3 % 128;
            if (i3 % 2 != 0) {
            }
            sb.append(j);
            sb3.append(m105(m98(25, 86, 0).intern()) ? m98(1, 34, 0).intern() : m98(1, 35, 0).intern());
            if (m105(m98(23, SakanaView.MODAL_MOUSE_PRESSED, 0).intern())) {
                int i4 = f101 + SakanaView.MOUSE_CANCELLED;
                f100 = i4 % 128;
                intern2 = i4 % 2 == 0 ? m98(1, 10, 0).intern() : m98(1, 34, 0).intern();
            } else {
                intern2 = m98(1, 35, 0).intern();
                int i5 = f100 + 9;
                f101 = i5 % 128;
                if (i5 % 2 != 0) {
                }
            }
            sb3.append(intern2);
            if (m105(m98(20, 134, 17587).intern())) {
                int i6 = f100 + 23;
                f101 = i6 % 128;
                if (i6 % 2 != 0) {
                }
                intern3 = m98(1, 34, 0).intern();
            } else {
                intern3 = m98(1, 35, 0).intern();
            }
            sb3.append(intern3);
            switch (m105(m98(15, 154, 37855).intern()) ? (char) 16 : 23) {
                case MotionEventCompat.AXIS_BRAKE:
                    intern4 = m98(1, 35, 0).intern();
                    break;
                default:
                    int i7 = f100 + 3;
                    f101 = i7 % 128;
                    if (i7 % 2 != 0) {
                    }
                    intern4 = m98(1, 34, 0).intern();
                    break;
            }
            sb3.append(intern4);
            String r03 = t.m220(t.m219(sb.toString()));
            String obj = sb2.toString();
            StringBuilder sb5 = new StringBuilder(r03);
            sb5.setCharAt(17, Integer.toString(Integer.parseInt(obj, 2), 16).charAt(0));
            String obj2 = sb5.toString();
            String obj3 = sb3.toString();
            StringBuilder sb6 = new StringBuilder(obj2);
            sb6.setCharAt(27, Integer.toString(Integer.parseInt(obj3, 2), 16).charAt(0));
            return m103(sb6.toString(), Long.valueOf(j));
        } catch (PackageManager.NameNotFoundException e2) {
            return m98(32, 54, 0).intern();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        if (r15 != null) goto L_0x0031;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x009a  */
    /* renamed from: ˏ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m103(java.lang.String r14, java.lang.Long r15) {
        /*
            r12 = 100
            r3 = 32
            r10 = 23
            r6 = 1
            r1 = 0
            if (r14 == 0) goto L_0x0024
            int r0 = f100
            int r0 = r0 + 27
            int r2 = r0 % 128
            f101 = r2
            int r0 = r0 % 2
            if (r0 == 0) goto L_0x00e3
            r0 = r6
        L_0x0017:
            switch(r0) {
                case 0: goto L_0x002f;
                default: goto L_0x001a;
            }
        L_0x001a:
            r0 = 0
            super.hashCode()
            if (r15 == 0) goto L_0x00e6
            r0 = r1
        L_0x0021:
            switch(r0) {
                case 0: goto L_0x0031;
                default: goto L_0x0024;
            }
        L_0x0024:
            r0 = 54
            java.lang.String r0 = m98(r3, r0, r1)
            java.lang.String r0 = r0.intern()
        L_0x002e:
            return r0
        L_0x002f:
            if (r15 == 0) goto L_0x0024
        L_0x0031:
            int r0 = r14.length()
            if (r0 != r3) goto L_0x0024
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r14)
            java.lang.String r3 = r15.toString()
            r4 = 0
            r0 = r1
            r2 = r1
        L_0x0044:
            int r8 = r3.length()
            if (r0 >= r8) goto L_0x0062
            char r8 = r3.charAt(r0)
            int r8 = java.lang.Character.getNumericValue(r8)
            int r2 = r2 + r8
            int r0 = r0 + 1
            int r8 = f101
            int r8 = r8 + 51
            int r9 = r8 % 128
            f100 = r9
            int r8 = r8 % 2
            if (r8 != 0) goto L_0x0044
            goto L_0x0044
        L_0x0062:
            java.lang.String r0 = java.lang.Integer.toHexString(r2)
            r2 = 7
            int r3 = r0.length()
            int r3 = r3 + 7
            r7.replace(r2, r3, r0)
            int r0 = f101
            int r0 = r0 + 25
            int r2 = r0 % 128
            f100 = r2
            int r0 = r0 % 2
            if (r0 != 0) goto L_0x00dd
            r2 = r4
            r0 = r1
        L_0x007e:
            int r4 = r7.length()
            if (r0 >= r4) goto L_0x00e0
            r4 = 53
        L_0x0086:
            switch(r4) {
                case 64: goto L_0x0096;
                default: goto L_0x0089;
            }
        L_0x0089:
            char r4 = r7.charAt(r0)
            int r4 = java.lang.Character.getNumericValue(r4)
            long r4 = (long) r4
            long r2 = r2 + r4
            int r0 = r0 + 1
            goto L_0x007e
        L_0x0096:
            int r0 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a8
            long r2 = r2 % r12
            int r0 = f100
            int r0 = r0 + 113
            int r4 = r0 % 128
            f101 = r4
            int r0 = r0 % 2
            if (r0 == 0) goto L_0x0096
            goto L_0x0096
        L_0x00a8:
            int r0 = (int) r2
            r7.insert(r10, r0)
            r4 = 10
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 >= 0) goto L_0x00d7
            int r0 = f101
            int r0 = r0 + 95
            int r2 = r0 % 128
            f100 = r2
            int r0 = r0 % 2
            if (r0 != 0) goto L_0x00be
        L_0x00be:
            r0 = 35
            java.lang.String r0 = m98(r6, r0, r1)
            java.lang.String r0 = r0.intern()
            r7.insert(r10, r0)
            int r0 = f101
            int r0 = r0 + 21
            int r1 = r0 % 128
            f100 = r1
            int r0 = r0 % 2
            if (r0 != 0) goto L_0x00d7
        L_0x00d7:
            java.lang.String r0 = r7.toString()
            goto L_0x002e
        L_0x00dd:
            r2 = r4
            r0 = r1
            goto L_0x007e
        L_0x00e0:
            r4 = 64
            goto L_0x0086
        L_0x00e3:
            r0 = r1
            goto L_0x0017
        L_0x00e6:
            r0 = r6
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.b.m103(java.lang.String, java.lang.Long):java.lang.String");
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static boolean m105(String str) {
        boolean z = false;
        int i = f100 + 43;
        f101 = i % 128;
        if (i % 2 != 0) {
        }
        try {
            Class.forName(str);
            int i2 = f101 + 41;
            f100 = i2 % 128;
            if (i2 % 2 != 0) {
                z = true;
            }
            switch (z) {
                case true:
                    return true;
                default:
                    Object[] objArr = null;
                    int length = objArr.length;
                    return true;
            }
        } catch (ClassNotFoundException e2) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0093 A[SYNTHETIC] */
    /* renamed from: ˊ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m99(java.lang.String r9) {
        /*
            r8 = 169(0xa9, float:2.37E-43)
            r2 = 1
            r1 = 0
            int r0 = f101
            int r0 = r0 + 9
            int r3 = r0 % 128
            f100 = r3
            int r0 = r0 % 2
            if (r0 != 0) goto L_0x0010
        L_0x0010:
            java.lang.String r0 = m98(r2, r8, r1)
            java.lang.String r0 = r0.intern()
            boolean r0 = r9.contains(r0)
            if (r0 != 0) goto L_0x00a3
            r0 = 19
        L_0x0020:
            switch(r0) {
                case 92: goto L_0x0031;
                default: goto L_0x0023;
            }
        L_0x0023:
            int r0 = f101
            int r0 = r0 + 63
            int r1 = r0 % 128
            f100 = r1
            int r0 = r0 % 2
            if (r0 != 0) goto L_0x002f
        L_0x002f:
        L_0x0030:
            return r9
        L_0x0031:
            r0 = 2
            r3 = 170(0xaa, float:2.38E-43)
            java.lang.String r0 = m98(r0, r3, r1)
            java.lang.String r0 = r0.intern()
            java.lang.String[] r4 = r9.split(r0)
            int r5 = r4.length
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            int r0 = r5 + -1
            r0 = r4[r0]
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.String r3 = m98(r2, r8, r1)
            java.lang.String r3 = r3.intern()
            r0.append(r3)
            r3 = r2
        L_0x005a:
            int r0 = r5 + -1
            if (r3 >= r0) goto L_0x009e
            r0 = r1
        L_0x005f:
            switch(r0) {
                case 1: goto L_0x0093;
                default: goto L_0x0062;
            }
        L_0x0062:
            int r0 = f100
            int r0 = r0 + 99
            int r7 = r0 % 128
            f101 = r7
            int r0 = r0 % 2
            if (r0 == 0) goto L_0x006e
        L_0x006e:
            r0 = r4[r3]
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.String r7 = m98(r2, r8, r1)
            java.lang.String r7 = r7.intern()
            r0.append(r7)
            int r3 = r3 + 1
            int r0 = f100
            int r0 = r0 + 57
            int r7 = r0 % 128
            f101 = r7
            int r0 = r0 % 2
            if (r0 == 0) goto L_0x00a0
            r0 = 55
        L_0x008f:
            switch(r0) {
                case 55: goto L_0x005a;
                default: goto L_0x0092;
            }
        L_0x0092:
            goto L_0x005a
        L_0x0093:
            r0 = r4[r1]
            r6.append(r0)
            java.lang.String r9 = r6.toString()
            goto L_0x0030
        L_0x009e:
            r0 = r2
            goto L_0x005f
        L_0x00a0:
            r0 = 35
            goto L_0x008f
        L_0x00a3:
            r0 = 92
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.b.m99(java.lang.String):java.lang.String");
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    private static String m104(Context context) {
        boolean z;
        boolean z2 = true;
        int i = f101 + 123;
        f100 = i % 128;
        if (i % 2 == 0) {
            z = true;
        } else {
            z = false;
        }
        switch (z) {
            case false:
                if (System.getProperties().containsKey(m98(14, 172, 53193).intern())) {
                    z2 = false;
                }
                switch (z2) {
                    case true:
                        return null;
                }
            default:
                switch (System.getProperties().containsKey(m98(68, 5866, 53193).intern()) ? 'Z' : 15) {
                    case 15:
                        return null;
                }
        }
        try {
            Matcher matcher = Pattern.compile(m98(10, 192, 3577).intern()).matcher(context.getCacheDir().getPath().replace(m98(6, 186, 0).intern(), ""));
            if (!matcher.find()) {
                return null;
            }
            int i2 = f101 + 33;
            f100 = i2 % 128;
            return i2 % 2 == 0 ? matcher.group(1) : matcher.group(1);
        } catch (Exception e2) {
            C0001r.m185().m200(m98(17, SakanaView.KEY_REPEATED, 0).intern(), new StringBuilder().append(m98(41, 219, 8073).intern()).append(e2).toString());
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: ˋ  reason: contains not printable characters */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m100(android.content.Context r4) {
        /*
            r1 = 0
            android.content.pm.PackageManager r0 = r4.getPackageManager()     // Catch:{ NameNotFoundException -> 0x003b }
            java.lang.String r2 = r4.getPackageName()     // Catch:{ NameNotFoundException -> 0x003b }
            r3 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r2, r3)     // Catch:{ NameNotFoundException -> 0x003b }
            java.lang.String r0 = r0.packageName     // Catch:{ NameNotFoundException -> 0x003b }
            int r2 = f100
            int r2 = r2 + 109
            int r3 = r2 % 128
            f101 = r3
            int r2 = r2 % 2
            if (r2 == 0) goto L_0x001c
        L_0x001c:
            int r2 = f100
            int r2 = r2 + 23
            int r3 = r2 % 128
            f101 = r3
            int r2 = r2 % 2
            if (r2 == 0) goto L_0x0028
        L_0x0028:
            int r2 = f101
            int r2 = r2 + 51
            int r3 = r2 % 128
            f100 = r3
            int r2 = r2 % 2
            if (r2 != 0) goto L_0x0041
            r2 = 62
        L_0x0036:
            switch(r2) {
                case 62: goto L_0x003e;
                default: goto L_0x0039;
            }
        L_0x0039:
        L_0x003a:
            return r0
        L_0x003b:
            r0 = move-exception
            r0 = r1
            goto L_0x003a
        L_0x003e:
            int r1 = r1.length
            goto L_0x003a
        L_0x0041:
            r2 = 40
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.b.m100(android.content.Context):java.lang.String");
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static String m102(Context context, String str) {
        char c;
        try {
            Iterator it = ((List) PackageManager.class.getDeclaredMethod(m98(24, 260, 41190).intern(), new Class[]{Integer.TYPE}).invoke(context.getPackageManager(), new Object[]{0})).iterator();
            do {
                switch (it.hasNext() ? 'c' : 23) {
                    case 'c':
                        break;
                    default:
                        int i = f100 + 93;
                        f101 = i % 128;
                        if (i % 2 != 0) {
                            c = 14;
                        } else {
                            c = ':';
                        }
                        switch (c) {
                        }
                }
                return Boolean.FALSE.toString();
            } while (!((ApplicationInfo) it.next()).packageName.equals(str));
            int i2 = f101 + 83;
            f100 = i2 % 128;
            switch (i2 % 2 == 0) {
                case true:
                    int i3 = 11 / 0;
                    return Boolean.TRUE.toString();
                default:
                    return Boolean.TRUE.toString();
            }
        } catch (IllegalAccessException e2) {
            C0001r.m185().m200(m98(24, 284, 0).intern(), new StringBuilder().append(m98(47, 308, 37577).intern()).append(e2).toString());
        } catch (NoSuchMethodException e3) {
            C0001r.m185().m200(m98(24, 284, 0).intern(), new StringBuilder().append(m98(47, 308, 37577).intern()).append(e3).toString());
        } catch (InvocationTargetException e4) {
            C0001r.m185().m200(m98(24, 284, 0).intern(), new StringBuilder().append(m98(47, 308, 37577).intern()).append(e4).toString());
        }
    }

    static class e extends HashMap<String, Object> {

        /* renamed from: ˎ  reason: contains not printable characters */
        private final Context f102;

        /* renamed from: ˏ  reason: contains not printable characters */
        private final Map<String, Object> f103;

        e(Map<String, Object> map, Context context) {
            this.f103 = map;
            this.f102 = context;
            put(m106(), m108());
        }

        /* renamed from: ˎ  reason: contains not printable characters */
        private static StringBuilder m107(@NonNull String... strArr) throws Exception {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < 3; i++) {
                arrayList.add(Integer.valueOf(strArr[i].length()));
            }
            Collections.sort(arrayList);
            int intValue = ((Integer) arrayList.get(0)).intValue();
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < intValue; i2++) {
                Integer num = null;
                for (int i3 = 0; i3 < 3; i3++) {
                    char charAt = strArr[i3].charAt(i2);
                    if (num != null) {
                        charAt ^= num.intValue();
                    }
                    num = Integer.valueOf(charAt);
                }
                sb.append(Integer.toHexString(num.intValue()));
            }
            return sb;
        }

        @NonNull
        /* renamed from: ˋ  reason: contains not printable characters */
        private String m106() {
            try {
                String num = Integer.toString(Build.VERSION.SDK_INT);
                String displayLanguage = Locale.getDefault().getDisplayLanguage();
                StringBuilder sb = new StringBuilder(this.f103.get("af_timestamp").toString());
                sb.reverse();
                StringBuilder r1 = m107(num, displayLanguage, sb.toString());
                int length = r1.length();
                if (length > 4) {
                    r1.delete(4, length);
                } else {
                    while (length < 4) {
                        length++;
                        r1.append('1');
                    }
                }
                r1.insert(0, "kef");
                return r1.toString();
            } catch (Exception e) {
                AFLogger.afRDLog("failed generating kef key with exception: ".concat(String.valueOf(e)));
                return "kef9999";
            }
        }

        /* renamed from: ॱ  reason: contains not printable characters */
        private String m108() {
            String obj;
            int i = 0;
            try {
                obj = new StringBuilder().append("").append(t.m219(new StringBuilder().append(this.f103.get("af_timestamp").toString()).append(this.f103.get("firstLaunchDate").toString()).append(BuildConfig.VERSION_NAME).toString()).substring(0, 16)).toString();
            } catch (Exception e) {
                AFLogger.afRDLog("failed generating kef value with exception: ".concat(String.valueOf(e)));
                obj = new StringBuilder().append("").append("babeae0540d91f2018").toString();
            }
            try {
                int intExtra = this.f102.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("temperature", -2700);
                if (this.f102.getApplicationInfo().nativeLibraryDir.contains("x86")) {
                    i = 1;
                }
                return new StringBuilder().append(obj).append(C0000b.m109(new StringBuilder("b").append(intExtra).append("&x").append(i).append("&s").append(((SensorManager) this.f102.getSystemService("sensor")).getSensorList(-1).size()).append("&p").append(this.f103.size()).toString())).toString();
            } catch (Exception e2) {
                AFLogger.afRDLog("failed generating kef value with exception: ".concat(String.valueOf(e2)));
                return new StringBuilder().append(obj).append("f0q0q1p1p22chtam").toString();
            }
        }

        /* renamed from: com.appsflyer.b$e$b  reason: collision with other inner class name */
        static class C0000b {

            /* renamed from: ˊ  reason: contains not printable characters */
            private long f104;

            /* renamed from: ˋ  reason: contains not printable characters */
            private String f105;

            /* renamed from: ˏ  reason: contains not printable characters */
            private final Object f106;

            C0000b() {
            }

            @NonNull
            /* renamed from: ˎ  reason: contains not printable characters */
            static String m109(String str) throws Exception {
                byte[] bytes = str.getBytes();
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i] ^ ((i % 2) + 42));
                }
                StringBuilder sb = new StringBuilder();
                for (byte hexString : bytes) {
                    String hexString2 = Integer.toHexString(hexString);
                    if (hexString2.length() == 1) {
                        hexString2 = "0".concat(String.valueOf(hexString2));
                    }
                    sb.append(hexString2);
                }
                return sb.toString();
            }

            C0000b(long j, String str) {
                this.f106 = new Object();
                this.f104 = 0;
                this.f105 = "";
                this.f104 = j;
                this.f105 = str;
            }

            C0000b(String str) {
                this(System.currentTimeMillis(), str);
            }

            @NonNull
            /* renamed from: ॱ  reason: contains not printable characters */
            static C0000b m111(String str) {
                if (str == null) {
                    return new C0000b(0, "");
                }
                String[] split = str.split(",");
                if (split.length < 2) {
                    return new C0000b(0, "");
                }
                return new C0000b(Long.parseLong(split[0]), split[1]);
            }

            /* access modifiers changed from: package-private */
            /* renamed from: ˎ  reason: contains not printable characters */
            public final boolean m113(C0000b bVar) {
                return m110(bVar.f104, bVar.f105);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
                return false;
             */
            /* renamed from: ˏ  reason: contains not printable characters */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            private boolean m110(long r10, java.lang.String r12) {
                /*
                    r9 = this;
                    r0 = 1
                    r1 = 0
                    java.lang.Object r3 = r9.f106
                    monitor-enter(r3)
                    if (r12 == 0) goto L_0x0024
                    java.lang.String r2 = r9.f105     // Catch:{ all -> 0x0027 }
                    boolean r2 = r12.equals(r2)     // Catch:{ all -> 0x0027 }
                    if (r2 != 0) goto L_0x0024
                    long r4 = r9.f104     // Catch:{ all -> 0x0027 }
                    long r4 = r10 - r4
                    r6 = 2000(0x7d0, double:9.88E-321)
                    int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    if (r2 <= 0) goto L_0x0022
                    r2 = r0
                L_0x001a:
                    if (r2 == 0) goto L_0x0024
                    r9.f104 = r10     // Catch:{ all -> 0x0027 }
                    r9.f105 = r12     // Catch:{ all -> 0x0027 }
                    monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                L_0x0021:
                    return r0
                L_0x0022:
                    r2 = r1
                    goto L_0x001a
                L_0x0024:
                    monitor-exit(r3)
                    r0 = r1
                    goto L_0x0021
                L_0x0027:
                    r0 = move-exception
                    monitor-exit(r3)
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.b.e.C0000b.m110(long, java.lang.String):boolean");
            }

            public final String toString() {
                return new StringBuilder().append(this.f104).append(",").append(this.f105).toString();
            }

            /* access modifiers changed from: package-private */
            /* renamed from: ˊ  reason: contains not printable characters */
            public final String m112() {
                return this.f105;
            }
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static String m98(int i, int i2, char c) {
        int i3;
        char[] cArr = new char[i];
        int i4 = f100 + 87;
        f101 = i4 % 128;
        if (i4 % 2 != 0) {
        }
        int i5 = 0;
        while (true) {
            switch (i5 < i) {
                case true:
                    int i6 = f101 + 1;
                    f100 = i6 % 128;
                    switch (i6 % 2 != 0) {
                        case false:
                            cArr[i5] = (char) ((int) ((((long) f99[i2 + i5]) / (((long) i5) + f98)) - ((long) c)));
                            i3 = i5 + 100;
                            break;
                        default:
                            cArr[i5] = (char) ((int) ((((long) f99[i2 + i5]) ^ (((long) i5) * f98)) ^ ((long) c)));
                            i3 = i5 + 1;
                            break;
                    }
                    int i7 = f100 + 35;
                    f101 = i7 % 128;
                    if (i7 % 2 != 0) {
                    }
                    i5 = i3;
                default:
                    String str = new String(cArr);
                    int i8 = f101 + 123;
                    f100 = i8 % 128;
                    if (i8 % 2 == 0) {
                    }
                    return str;
            }
        }
    }
}

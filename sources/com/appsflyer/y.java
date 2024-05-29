package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.appsflyer.b;
import com.appsflyer.j;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.lang.ref.WeakReference;

final class y {
    y() {
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static boolean m225(Context context) {
        return m226(context) | m223(context);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static boolean m226(Context context) {
        boolean z;
        boolean z2;
        boolean z3;
        if (AppsFlyerLib.getInstance().isTrackingStopped()) {
            return false;
        }
        try {
            Class.forName("com.google.android.gms.iid.InstanceIDListenerService");
            Intent intent = new Intent("com.google.android.gms.iid.InstanceID", (Uri) null, context, GcmInstanceIdListener.class);
            Intent intent2 = new Intent("com.google.android.gms.iid.InstanceID", (Uri) null, context, InstanceIDListenerService.class);
            if (context.getPackageManager().queryIntentServices(intent, 0).size() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (context.getPackageManager().queryIntentServices(intent2, 0).size() > 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    return false;
                }
            }
            if (context.getPackageManager().queryBroadcastReceivers(new Intent("com.google.android.c2dm.intent.RECEIVE", (Uri) null, context, Class.forName("com.google.android.gms.gcm.GcmReceiver")), 0).size() > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (j.AnonymousClass1.m151(context, new StringBuilder().append(context.getPackageName()).append(".permission.C2D_MESSAGE").toString())) {
                    return true;
                }
                AFLogger.afWarnLog("Cannot verify existence of the app's \"permission.C2D_MESSAGE\" permission in the manifest. Please refer to documentation.");
                return false;
            }
            AFLogger.afWarnLog("Cannot verify existence of GcmReceiver receiver in the manifest. Please refer to documentation.");
            return false;
        } catch (ClassNotFoundException e) {
            AFLogger.afRDLog(e.getMessage());
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest declarations: ", th);
            return false;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private static boolean m223(Context context) {
        boolean z;
        boolean z2;
        if (AppsFlyerLib.getInstance().isTrackingStopped()) {
            return false;
        }
        try {
            Class.forName("com.google.firebase.iid.FirebaseInstanceIdService");
            Intent intent = new Intent("com.google.firebase.INSTANCE_ID_EVENT", (Uri) null, context, FirebaseInstanceIdListener.class);
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT", (Uri) null, context, FirebaseInstanceIdService.class);
            if (context.getPackageManager().queryIntentServices(intent, 0).size() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (context.getPackageManager().queryIntentServices(intent2, 0).size() > 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    AFLogger.afWarnLog("Cannot verify existence of our InstanceID Listener Service in the manifest. Please refer to documentation.");
                    return false;
                }
            }
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest declarations: ", th);
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public static String m222(WeakReference<Context> weakReference, String str) {
        try {
            Class<?> cls = Class.forName("com.google.android.gms.iid.InstanceID");
            Class.forName("com.google.android.gms.gcm.GcmReceiver");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(cls, new Object[]{weakReference.get()});
            String str2 = (String) cls.getDeclaredMethod("getToken", new Class[]{String.class, String.class}).invoke(invoke, new Object[]{str, "GCM"});
            if (str2 != null) {
                return str2;
            }
            AFLogger.afWarnLog("Couldn't get token using reflection.");
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Throwable th) {
            AFLogger.afErrorLog("Couldn't get token using GoogleCloudMessaging. ", th);
            return null;
        }
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static void m227(Context context, b.e.C0000b bVar) {
        AFLogger.afInfoLog(new StringBuilder("updateServerUninstallToken called with: ").append(bVar.toString()).toString());
        b.e.C0000b r0 = b.e.C0000b.m111(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
        if (!context.getSharedPreferences("appsflyer-data", 0).getBoolean("sentRegisterRequestToAF", false) || r0.m112() == null || !r0.m112().equals(bVar.m112())) {
            AppsFlyerProperties.getInstance().set("afUninstallToken", bVar.toString());
            AppsFlyerLib.getInstance().m75(context, bVar.m112());
        }
    }

    static class b extends AsyncTask<Void, Void, String> {

        /* renamed from: ˏ  reason: contains not printable characters */
        private final WeakReference<Context> f253;

        /* renamed from: ॱ  reason: contains not printable characters */
        private String f254;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return m228();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                String string = AppsFlyerProperties.getInstance().getString("afUninstallToken");
                b.e.C0000b bVar = new b.e.C0000b(str);
                if (string == null) {
                    y.m227(this.f253.get(), bVar);
                    return;
                }
                b.e.C0000b r2 = b.e.C0000b.m111(string);
                if (r2.m113(bVar)) {
                    y.m227(this.f253.get(), r2);
                }
            }
        }

        b(WeakReference<Context> weakReference) {
            this.f253 = weakReference;
        }

        /* access modifiers changed from: protected */
        public final void onPreExecute() {
            super.onPreExecute();
            this.f254 = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        private String m228() {
            try {
                if (this.f254 != null) {
                    return y.m222(this.f253, this.f254);
                }
                return null;
            } catch (Throwable th) {
                AFLogger.afErrorLog("Error registering for uninstall feature", th);
                return null;
            }
        }
    }
}

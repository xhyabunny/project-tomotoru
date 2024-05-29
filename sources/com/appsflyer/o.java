package com.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import java.lang.ref.WeakReference;
import java.util.concurrent.RejectedExecutionException;

@RequiresApi(api = 14)
final class o implements Application.ActivityLifecycleCallbacks {

    /* renamed from: ˏ  reason: contains not printable characters */
    private static o f184;
    /* access modifiers changed from: private */

    /* renamed from: ˊ  reason: contains not printable characters */
    public d f185 = null;
    /* access modifiers changed from: private */

    /* renamed from: ˋ  reason: contains not printable characters */
    public boolean f186 = false;
    /* access modifiers changed from: private */

    /* renamed from: ॱ  reason: contains not printable characters */
    public boolean f187 = true;

    interface d {
        /* renamed from: ˊ  reason: contains not printable characters */
        void m170(Activity activity);

        /* renamed from: ˊ  reason: contains not printable characters */
        void m171(WeakReference<Context> weakReference);
    }

    o() {
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    static o m163() {
        if (f184 == null) {
            f184 = new o();
        }
        return f184;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public static o m166() {
        if (f184 != null) {
            return f184;
        }
        throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameter init/get");
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m169(Application application, d dVar) {
        this.f185 = dVar;
        if (Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(f184);
        }
    }

    public final void onActivityResumed(Activity activity) {
        boolean z = false;
        this.f187 = false;
        if (!this.f186) {
            z = true;
        }
        this.f186 = true;
        if (z) {
            try {
                this.f185.m170(activity);
            } catch (Exception e2) {
                AFLogger.afErrorLog("Listener threw exception! ", e2);
            }
        }
    }

    public final void onActivityPaused(Activity activity) {
        this.f187 = true;
        try {
            new e(new WeakReference(activity.getApplicationContext())).executeOnExecutor(AFExecutor.getInstance().getThreadPoolExecutor(), new Void[0]);
        } catch (RejectedExecutionException e2) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with RejectedExecutionException Exception", e2);
        } catch (Throwable th) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with Exception", th);
        }
    }

    class e extends AsyncTask<Void, Void, Void> {

        /* renamed from: ˋ  reason: contains not printable characters */
        private WeakReference<Context> f188;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return m172();
        }

        public e(WeakReference<Context> weakReference) {
            this.f188 = weakReference;
        }

        /* renamed from: ॱ  reason: contains not printable characters */
        private Void m172() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                AFLogger.afErrorLog("Sleeping attempt failed (essential for background state verification)\n", e);
            }
            if (o.this.f186 && o.this.f187) {
                boolean unused = o.this.f186 = false;
                try {
                    o.this.f185.m171(this.f188);
                } catch (Exception e2) {
                    AFLogger.afErrorLog("Listener threw exception! ", e2);
                    cancel(true);
                }
            }
            this.f188.clear();
            return null;
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        AFDeepLinkManager.getInstance().collectIntentsFromActivities(activity.getIntent());
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }
}

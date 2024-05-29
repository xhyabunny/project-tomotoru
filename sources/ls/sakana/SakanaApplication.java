package ls.sakana;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class SakanaApplication extends Application implements Application.ActivityLifecycleCallbacks {
    static int running = 0;

    public void onCreate() {
        registerActivityLifecycleCallbacks(this);
        System.out.println("ls.sakana.SakanaApplication started.");
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        synchronized (SakanaApplication.class) {
            running--;
        }
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
        synchronized (SakanaApplication.class) {
            running--;
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public static boolean isForeground() {
        boolean z;
        synchronized (SakanaApplication.class) {
            z = running > 0;
        }
        return z;
    }
}

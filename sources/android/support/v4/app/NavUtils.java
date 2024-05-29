package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    public static boolean shouldUpRecreateTask(Activity sourceActivity, Intent targetIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return sourceActivity.shouldUpRecreateTask(targetIntent);
        }
        String action = sourceActivity.getIntent().getAction();
        return action != null && !action.equals("android.intent.action.MAIN");
    }

    public static void navigateUpFromSameTask(Activity sourceActivity) {
        Intent upIntent = getParentActivityIntent(sourceActivity);
        if (upIntent == null) {
            throw new IllegalArgumentException("Activity " + sourceActivity.getClass().getSimpleName() + " does not have a parent activity name specified." + " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " + " element in your manifest?)");
        }
        navigateUpTo(sourceActivity, upIntent);
    }

    public static void navigateUpTo(Activity sourceActivity, Intent upIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            sourceActivity.navigateUpTo(upIntent);
            return;
        }
        upIntent.addFlags(67108864);
        sourceActivity.startActivity(upIntent);
        sourceActivity.finish();
    }

    public static Intent getParentActivityIntent(Activity sourceActivity) {
        Intent result;
        Intent result2;
        if (Build.VERSION.SDK_INT >= 16 && (result2 = sourceActivity.getParentActivityIntent()) != null) {
            return result2;
        }
        String parentName = getParentActivityName(sourceActivity);
        if (parentName == null) {
            return null;
        }
        ComponentName target = new ComponentName(sourceActivity, parentName);
        try {
            if (getParentActivityName(sourceActivity, target) == null) {
                result = Intent.makeMainActivity(target);
            } else {
                result = new Intent().setComponent(target);
            }
            return result;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + parentName + "' in manifest");
            return null;
        }
    }

    public static Intent getParentActivityIntent(Context context, Class<?> sourceActivityClass) throws PackageManager.NameNotFoundException {
        String parentActivity = getParentActivityName(context, new ComponentName(context, sourceActivityClass));
        if (parentActivity == null) {
            return null;
        }
        ComponentName target = new ComponentName(context, parentActivity);
        if (getParentActivityName(context, target) == null) {
            return Intent.makeMainActivity(target);
        }
        return new Intent().setComponent(target);
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivity = getParentActivityName(context, componentName);
        if (parentActivity == null) {
            return null;
        }
        ComponentName target = new ComponentName(componentName.getPackageName(), parentActivity);
        if (getParentActivityName(context, target) == null) {
            return Intent.makeMainActivity(target);
        }
        return new Intent().setComponent(target);
    }

    @Nullable
    public static String getParentActivityName(Activity sourceActivity) {
        try {
            return getParentActivityName(sourceActivity, sourceActivity.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Nullable
    public static String getParentActivityName(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String result;
        ActivityInfo info = context.getPackageManager().getActivityInfo(componentName, 128);
        if (Build.VERSION.SDK_INT >= 16 && (result = info.parentActivityName) != null) {
            return result;
        }
        if (info.metaData == null) {
            return null;
        }
        String parentActivity = info.metaData.getString(PARENT_ACTIVITY);
        if (parentActivity == null) {
            return null;
        }
        if (parentActivity.charAt(0) == '.') {
            parentActivity = context.getPackageName() + parentActivity;
        }
        return parentActivity;
    }

    private NavUtils() {
    }
}

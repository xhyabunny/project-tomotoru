package com.cyberstep.sanriocharacters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.FrameLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ls.sakana.SakanaActivity;

public class SanrioActivity extends SakanaActivity {
    private static final String CONTENT_PROVIDER_AUTHORITY = "com.cyberstep.sanriocharacters.fileprovider";
    private static final int REQUEST_CODE_PERMISSIONS = 1;

    static {
        System.loadLibrary("smartar");
    }

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().addFlags(1024);
        super.onCreate(savedInstanceState);
    }

    private boolean checkARPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (PermissionChecker.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            return false;
        }
        if (PermissionChecker.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        if (PermissionChecker.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (PermissionChecker.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        if (PermissionChecker.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    private void confirmARPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionList = new ArrayList<>();
            if (PermissionChecker.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                permissionList.add("android.permission.CAMERA");
            }
            if (PermissionChecker.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                permissionList.add("android.permission.READ_EXTERNAL_STORAGE");
            }
            if (PermissionChecker.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                permissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (permissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, (String[]) permissionList.toArray(new String[0]), 1);
            }
        }
    }

    private void confirmStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionList = new ArrayList<>();
            if (PermissionChecker.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                permissionList.add("android.permission.READ_EXTERNAL_STORAGE");
            }
            if (PermissionChecker.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                permissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
            if (permissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, (String[]) permissionList.toArray(new String[0]), 1);
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int width = this.mainpanel.getWidth();
        int height = this.mainpanel.getHeight();
        hideNavigationBar();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void hideNavigationBar() {
        View decor = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 18) {
            decor.setSystemUiVisibility(4102);
        } else if (Build.VERSION.SDK_INT > 15) {
            decor.setSystemUiVisibility(6);
        } else if (Build.VERSION.SDK_INT > 13) {
            decor.setSystemUiVisibility(2);
        }
    }

    /* access modifiers changed from: protected */
    public void createMainPanel() {
        hideNavigationBar();
        getWindowManager().getDefaultDisplay().getRealSize(new Point());
        FrameLayout fl = new FrameLayout(getApplicationContext());
        setContentView(fl);
        fl.addView(this.view, new FrameLayout.LayoutParams(-1, -1));
        this.mainpanel = fl;
    }

    public static String getExternalStrageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static void scanPhotoGallery(String filename, Context context) {
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(filename))));
    }

    public static void startExternalIntent(String filename, String postStr, Context context) {
        Uri contentUri = FileProvider.getUriForFile(context, CONTENT_PROVIDER_AUTHORITY, new File(filename));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(filename.endsWith("mp4") ? "video/*" : "image/*");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!resInfo.isEmpty()) {
            HashMap<String, Intent> shareIntentMap = new HashMap<>();
            for (ResolveInfo info : resInfo) {
                Intent shareIntent = (Intent) intent.clone();
                shareIntent.putExtra("android.intent.extra.TEXT", postStr);
                shareIntent.putExtra("android.intent.extra.STREAM", contentUri);
                shareIntent.addFlags(3);
                shareIntent.setPackage(info.activityInfo.packageName);
                if (shareIntentMap.get(info.activityInfo.packageName) == null) {
                    shareIntentMap.put(info.activityInfo.packageName, shareIntent);
                }
            }
            List<Intent> shareIntentList = new ArrayList<>(shareIntentMap.values());
            Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), "select");
            chooserIntent.addFlags(268435456);
            chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) shareIntentList.toArray(new Parcelable[0]));
            context.startActivity(chooserIntent);
        }
    }

    public static boolean sarCheckARPermission(Context cont) {
        return ((SanrioActivity) cont).checkARPermission();
    }

    public static boolean sarCheckStoragePermission(Context cont) {
        return ((SanrioActivity) cont).checkStoragePermission();
    }

    public static void sarConfirmARPermission(Context cont) {
        ((SanrioActivity) cont).confirmARPermission();
    }

    public static void sarConfirmStoragePermission(Context cont) {
        ((SanrioActivity) cont).confirmStoragePermission();
    }
}

package ls.sakana;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.appsflyer.share.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import ls.sakana.iap.IabHelper;
import ls.sakana.iap.IabResult;
import ls.sakana.iap.Inventory;
import ls.sakana.iap.Purchase;
import ls.sakana.iap.SkuDetails;

public class SakanaView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    public static final int ACCEL_CHANGED = 1400;
    public static final int ACTION_EVENT = 4;
    public static final int ACTION_PERFORMED = 400;
    public static final int ACTIVATED = 2;
    public static final int ALT_GRAPH_MASK = 32;
    public static final int ALT_MASK = 8;
    public static final int APPACTIVE_ACTIVE = 1;
    public static final int APPACTIVE_BACKGROUND = 3;
    public static final int APPACTIVE_INACTIVE = 2;
    public static final int APPACTIVE_NOTLAUNCH = 0;
    public static final int APPACTIVE_SUSPENDED = 4;
    public static final int APP_EVENT = 0;
    public static final int BUTTON1_MASK = 64;
    public static final int BUTTON2_MASK = 128;
    public static final int BUTTON3_MASK = 256;
    public static final int BUTTON4_MASK = 512;
    public static final int CTRL_MASK = 2;
    public static final int DEACTIVATED = 3;
    public static final int DRAGSOURCE_EVENT = 8;
    public static final int DROPTARGET_EVENT = 9;
    public static final int FOCUS_GAINED = 700;
    public static final int FOCUS_LOST = 701;
    public static final int GYRO_CHANGED = 1401;
    public static final int IAP_CONSUME_FAIL = 1109;
    public static final int IAP_CONSUME_SUCCESS = 1108;
    public static final int IAP_INFO_FAIL = 1103;
    public static final int IAP_INFO_SUCCESS = 1102;
    public static final int IAP_INIT_FAIL = 1101;
    public static final int IAP_INIT_SUCCESS = 1100;
    public static final int IAP_OWN_FAIL = 1105;
    public static final int IAP_OWN_SUCCESS = 1104;
    public static final int IAP_PURCHASE_FAIL = 1107;
    public static final int IAP_PURCHASE_SUCCESS = 1106;
    public static final int IMTEXT_CHANGED = 300;
    public static final int IM_EVENT = 3;
    public static final int ITEM_EVENT = 5;
    public static final int ITEM_SELECTED = 500;
    public static final int KEY_EVENT = 2;
    public static final int KEY_PRESSED = 201;
    public static final int KEY_RELEASED = 203;
    public static final int KEY_REPEATED = 202;
    public static final int KEY_TYPED = 200;
    public static final int KWT_EVENT = 7;
    public static final int META_MASK = 4;
    public static final int MIDI_CLOSE = 2001;
    public static final int MIDI_DATA = 2002;
    public static final int MIDI_OPEN = 2000;
    public static final int MODAL_HMOUSE_WHEEL_MOVED = 116;
    public static final int MODAL_MOUSE_CLICKED = 110;
    public static final int MODAL_MOUSE_DRAGGED = 114;
    public static final int MODAL_MOUSE_MOVED = 113;
    public static final int MODAL_MOUSE_OFFSET = 10;
    public static final int MODAL_MOUSE_PRESSED = 111;
    public static final int MODAL_MOUSE_RELEASED = 112;
    public static final int MODAL_MOUSE_WHEEL_MOVED = 115;
    public static final int MOUSE_CANCELLED = 109;
    public static final int MOUSE_CLICKED = 100;
    public static final int MOUSE_DRAGGED = 104;
    public static final int MOUSE_ENTERED = 107;
    public static final int MOUSE_EVENT = 1;
    public static final int MOUSE_EXITED = 108;
    public static final int MOUSE_HWHEEL_MOVED = 106;
    public static final int MOUSE_MOVED = 103;
    public static final int MOUSE_PRESSED = 101;
    public static final int MOUSE_RELEASED = 102;
    public static final int MOUSE_WHEEL_MOVED = 105;
    public static final int QUIT_REQUESTED = 1;
    public static final int REPAINT = 5;
    public static final int RESIZED = 4;
    public static final int SHIFT_MASK = 1;
    public static final int SKSTATE_END = 4;
    public static final int SKSTATE_ENDING = 3;
    public static final int SKSTATE_ERROR = -1;
    public static final int SKSTATE_INIT = 0;
    public static final int SKSTATE_RUNNING = 2;
    public static final int SKSTATE_STARTING = 1;
    private static final String SK_TAG = "SK";
    public static final int SOURCE_DRAG_ENTER = 800;
    public static final int SOURCE_DRAG_EXIT = 801;
    public static final int SOURCE_DRAG_OVER = 802;
    public static final int SOURCE_DROP = 803;
    public static final int SOURCE_DROP_ACTION_CHANGED = 804;
    public static final int TARGET_DRAG_ENTER = 900;
    public static final int TARGET_DRAG_EXIT = 901;
    public static final int TARGET_DRAG_OVER = 902;
    public static final int TARGET_DROP = 903;
    public static final int TARGET_DROP_ACTION_CHANGED = 904;
    public static final int TEXTINPUT_CANCELED = 1001;
    public static final int TEXTINPUT_DONE = 1000;
    public static final int TEXT_CHANGED = 600;
    public static final int TEXT_EVENT = 6;
    private static boolean firstinitialized = false;
    Sensor accel;
    /* access modifiers changed from: private */
    public String[] appargs;
    EditText et;
    Sensor gyro;
    Handler handler;
    /* access modifiers changed from: private */
    public IabHelper iab;
    /* access modifiers changed from: private */
    public HashMap<String, ArrayList<Purchase>> iabmap = new HashMap<>();
    private int iapstate = -1;
    /* access modifiers changed from: private */
    public ArrayList<SakanaViewListener> listeners = new ArrayList<>();
    SensorManager sensorman;
    /* access modifiers changed from: private */
    public int sk;
    SKThread skthread;
    private HashMap<Integer, TouchInfo> touchmap = new HashMap<>();

    /* access modifiers changed from: private */
    public static native void skcalleduithread(int i, long j);

    /* access modifiers changed from: private */
    public static native void skchangeAppActiveState(int i, int i2);

    /* access modifiers changed from: private */
    public static native boolean skchecksxr(int i, String str, int i2);

    /* access modifiers changed from: private */
    public static native void skdisableGL(int i);

    /* access modifiers changed from: private */
    public static native void skenableGL(int i);

    private static native int skexecute(int i, String str);

    private static native void skinit(String str, String str2, String str3, String str4, String str5);

    /* access modifiers changed from: private */
    public static native void skkill(int i);

    /* access modifiers changed from: private */
    public static native int skloop(int i);

    /* access modifiers changed from: private */
    public static native boolean skmain(int i, SakanaView sakanaView, String[] strArr);

    /* access modifiers changed from: private */
    public static native int skmakewindow(int i, int i2, int i3, float f, float f2);

    /* access modifiers changed from: private */
    public static native int sknew();

    private static native void skpushAppEvent(int i, int i2);

    private static native void skpushKeyEvent(int i, int i2, int i3, int i4, int i5, int i6);

    private static native void skpushMouseEvent(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private static native void skpushSkuStringArrayEvent(int i, int i2, String str);

    /* access modifiers changed from: private */
    public static native void skpushStringEvent(int i, int i2, String str);

    /* access modifiers changed from: private */
    public static native void skregfunc(int i, String str, SakanaFunction sakanaFunction);

    /* access modifiers changed from: private */
    public static native void skresized(int i, int i2, int i3, float f, float f2);

    /* access modifiers changed from: package-private */
    public void copyasset(String path, String name, String dir, byte[] buf) throws IOException {
        AssetManager man = getContext().getAssets();
        new File(dir).mkdirs();
        BufferedInputStream is = new BufferedInputStream(man.open(path));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(dir + Constants.URL_PATH_DELIMITER + name));
        while (true) {
            int n = is.read(buf);
            if (-1 != n) {
                os.write(buf, 0, n);
            } else {
                os.close();
                is.close();
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void copyassets(String assetpath, String outdirpath) throws IOException {
        byte[] buf = new byte[262144];
        String[] fileList = getContext().getAssets().list(assetpath);
        int l = 0;
        while (l < fileList.length) {
            String path = assetpath + Constants.URL_PATH_DELIMITER + fileList[l];
            String name = path.substring(path.lastIndexOf(Constants.URL_PATH_DELIMITER) + 1);
            System.out.println("copyasset : " + path + " -> " + outdirpath);
            try {
                copyasset(path, name, outdirpath, buf);
                l++;
            } catch (IOException ioe) {
                System.err.println("!copyasset failure : " + path + " -> " + outdirpath);
                throw ioe;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getPackageVersion() throws IOException {
        return Integer.parseInt(new BufferedReader(new InputStreamReader(getContext().getAssets().open("sxr/version"))).readLine());
    }

    static boolean deleteFiles(File[] list) {
        for (File f : list) {
            if ((f.isDirectory() && !deleteFiles(f.listFiles())) || !f.delete()) {
                return false;
            }
        }
        return true;
    }

    public SakanaView(Context context) {
        super(context);
        synchronized (SakanaView.class) {
            if (!firstinitialized) {
                File datadir = context.getFilesDir();
                File docdir = Environment.getDataDirectory();
                File cachedir = context.getCacheDir();
                String osver = Build.VERSION.RELEASE;
                Locale loc = Locale.getDefault();
                String lang = loc.getLanguage();
                String co = loc.getCountry();
                if (lang.equals("zh")) {
                    if (co.equals("CN")) {
                        lang = "cn";
                    } else {
                        lang = "tw";
                    }
                }
                ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                System.loadLibrary("sakanagl");
                skinit(datadir.getPath(), docdir.getPath(), cachedir.getPath(), osver, lang);
            }
        }
        this.handler = new Handler();
        getHolder().addCallback(this);
        this.skthread = new SKThread();
    }

    public synchronized void addSakanaViewListener(SakanaViewListener l) {
        if (!this.listeners.contains(l)) {
            this.listeners.add(l);
        }
    }

    public synchronized void removeSakanaViewListener(SakanaViewListener l) {
        int i = this.listeners.indexOf(l);
        if (i >= 0) {
            this.listeners.remove(i);
        }
    }

    public void startSakanaGL(String[] args) {
        this.appargs = args;
        this.skthread.startAndWait();
    }

    public void stopSakanaGL() {
        if (this.iab != null) {
            this.iab.dispose();
            this.iab = null;
        }
        this.skthread.exitAndWait();
    }

    public void registerSakanaFunction(String name, SakanaFunction func) {
        this.skthread.registerSakanaFunction(name, func);
    }

    public void quitRequested() {
        synchronized (this.skthread) {
            if (this.sk != 0) {
                skpushAppEvent(this.sk, 1);
            }
        }
    }

    public void onPause() {
        SakanaViewListener[] a;
        _skchangeAppActiveState(2);
        synchronized (this) {
            a = (SakanaViewListener[]) this.listeners.toArray(new SakanaViewListener[this.listeners.size()]);
        }
        for (SakanaViewListener skOnPause : a) {
            skOnPause.skOnPause(this);
        }
    }

    public void onStop() {
        _skchangeAppActiveState(3);
    }

    public void onResume() {
        SakanaViewListener[] a;
        _skchangeAppActiveState(1);
        synchronized (this.skthread) {
            if (this.sk != 0) {
                skpushAppEvent(this.sk, 5);
            }
        }
        synchronized (this) {
            a = (SakanaViewListener[]) this.listeners.toArray(new SakanaViewListener[this.listeners.size()]);
        }
        for (SakanaViewListener skOnResume : a) {
            skOnResume.skOnResume(this);
        }
    }

    private void _skchangeAppActiveState(final int st) {
        try {
            this.skthread.invokeAndWait(new Runnable() {
                public void run() {
                    if (SakanaView.this.sk != 0) {
                        int access$000 = SakanaView.this.sk;
                        SKThread sKThread = SakanaView.this.skthread;
                        int i = st;
                        sKThread.appactivestate = i;
                        SakanaView.skchangeAppActiveState(access$000, i);
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println("skchangeAppActiveState failure");
            ex.printStackTrace();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        synchronized (this.skthread) {
            if (this.sk != 0) {
                skpushAppEvent(this.sk, 5);
            }
        }
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("SAKANA onActivityResult(" + requestCode + "," + resultCode + "," + data + ")");
        if (this.iab != null && this.iab.handleActivityResult(requestCode, resultCode, data)) {
            return true;
        }
        return false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.skthread.surfaceChanged(holder, format, width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.skthread.surfaceCreated(holder);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.skthread.surfaceDestroyed(holder);
    }

    /* access modifiers changed from: package-private */
    public TouchInfo getTouchInfo(int pid) {
        Integer i = new Integer(pid & 255);
        TouchInfo ti = this.touchmap.get(i);
        if (ti != null) {
            return ti;
        }
        TouchInfo ti2 = new TouchInfo();
        this.touchmap.put(i, ti2);
        return ti2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return super.onTouchEvent(r19);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r19) {
        /*
            r18 = this;
            int r9 = r19.getAction()
            int r10 = r19.getPointerCount()
            r2 = 65280(0xff00, float:9.1477E-41)
            r2 = r2 & r9
            int r12 = r2 >> 8
            r0 = r18
            ls.sakana.SakanaView$SKThread r2 = r0.skthread
            if (r2 != 0) goto L_0x0019
            boolean r2 = super.onTouchEvent(r19)
        L_0x0018:
            return r2
        L_0x0019:
            r0 = r18
            ls.sakana.SakanaView$SKThread r14 = r0.skthread
            monitor-enter(r14)
            r0 = r18
            int r2 = r0.sk     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x002d
            boolean r2 = super.onTouchEvent(r19)     // Catch:{ all -> 0x002a }
            monitor-exit(r14)     // Catch:{ all -> 0x002a }
            goto L_0x0018
        L_0x002a:
            r2 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x002a }
            throw r2
        L_0x002d:
            r0 = r18
            ls.sakana.SakanaView$SKThread r2 = r0.skthread     // Catch:{ all -> 0x002a }
            int r2 = r2.skstate     // Catch:{ all -> 0x002a }
            r3 = 2
            if (r2 == r3) goto L_0x003c
            boolean r2 = super.onTouchEvent(r19)     // Catch:{ all -> 0x002a }
            monitor-exit(r14)     // Catch:{ all -> 0x002a }
            goto L_0x0018
        L_0x003c:
            r2 = r9 & 255(0xff, float:3.57E-43)
            switch(r2) {
                case 0: goto L_0x0047;
                case 1: goto L_0x0087;
                case 2: goto L_0x00e2;
                case 3: goto L_0x0041;
                case 4: goto L_0x0041;
                case 5: goto L_0x0047;
                case 6: goto L_0x0087;
                default: goto L_0x0041;
            }     // Catch:{ all -> 0x002a }
        L_0x0041:
            monitor-exit(r14)     // Catch:{ all -> 0x002a }
            boolean r2 = super.onTouchEvent(r19)
            goto L_0x0018
        L_0x0047:
            r0 = r18
            android.widget.EditText r2 = r0.et     // Catch:{ all -> 0x002a }
            if (r2 == 0) goto L_0x0050
            r18.endTextEdit()     // Catch:{ all -> 0x002a }
        L_0x0050:
            r0 = r19
            float r2 = r0.getX(r12)     // Catch:{ all -> 0x002a }
            int r5 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            float r2 = r0.getY(r12)     // Catch:{ all -> 0x002a }
            int r6 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            int r4 = r0.getPointerId(r12)     // Catch:{ all -> 0x002a }
            r0 = r18
            ls.sakana.SakanaView$TouchInfo r13 = r0.getTouchInfo(r4)     // Catch:{ all -> 0x002a }
            long r2 = r19.getEventTime()     // Catch:{ all -> 0x002a }
            r13.presstime = r2     // Catch:{ all -> 0x002a }
            r13.pressx = r5     // Catch:{ all -> 0x002a }
            r13.x = r5     // Catch:{ all -> 0x002a }
            r13.pressy = r6     // Catch:{ all -> 0x002a }
            r13.y = r6     // Catch:{ all -> 0x002a }
            r0 = r18
            int r2 = r0.sk     // Catch:{ all -> 0x002a }
            r3 = 101(0x65, float:1.42E-43)
            r7 = 64
            r8 = 1
            skpushMouseEvent(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x002a }
            r2 = 1
            monitor-exit(r14)     // Catch:{ all -> 0x002a }
            goto L_0x0018
        L_0x0087:
            r0 = r19
            float r2 = r0.getX(r12)     // Catch:{ all -> 0x002a }
            int r5 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            float r2 = r0.getY(r12)     // Catch:{ all -> 0x002a }
            int r6 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            int r4 = r0.getPointerId(r12)     // Catch:{ all -> 0x002a }
            r0 = r18
            ls.sakana.SakanaView$TouchInfo r13 = r0.getTouchInfo(r4)     // Catch:{ all -> 0x002a }
            long r2 = r19.getEventTime()     // Catch:{ all -> 0x002a }
            long r0 = r13.presstime     // Catch:{ all -> 0x002a }
            r16 = r0
            long r2 = r2 - r16
            r16 = 300(0x12c, double:1.48E-321)
            int r2 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r2 >= 0) goto L_0x00d4
            int r2 = r13.pressx     // Catch:{ all -> 0x002a }
            int r2 = r5 - r2
            int r3 = r13.pressx     // Catch:{ all -> 0x002a }
            int r3 = r5 - r3
            int r2 = r2 * r3
            int r3 = r13.pressy     // Catch:{ all -> 0x002a }
            int r3 = r6 - r3
            int r7 = r13.pressy     // Catch:{ all -> 0x002a }
            int r7 = r6 - r7
            int r3 = r3 * r7
            int r2 = r2 + r3
            r3 = 144(0x90, float:2.02E-43)
            if (r2 >= r3) goto L_0x00d4
            r0 = r18
            int r2 = r0.sk     // Catch:{ all -> 0x002a }
            r3 = 100
            r7 = 64
            r8 = 1
            skpushMouseEvent(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x002a }
        L_0x00d4:
            r0 = r18
            int r2 = r0.sk     // Catch:{ all -> 0x002a }
            r3 = 102(0x66, float:1.43E-43)
            r7 = 64
            r8 = 1
            skpushMouseEvent(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x002a }
            goto L_0x0041
        L_0x00e2:
            r11 = 0
        L_0x00e3:
            if (r11 >= r10) goto L_0x0041
            r0 = r19
            float r2 = r0.getX(r11)     // Catch:{ all -> 0x002a }
            int r5 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            float r2 = r0.getY(r11)     // Catch:{ all -> 0x002a }
            int r6 = (int) r2     // Catch:{ all -> 0x002a }
            r0 = r19
            int r4 = r0.getPointerId(r11)     // Catch:{ all -> 0x002a }
            r0 = r18
            ls.sakana.SakanaView$TouchInfo r13 = r0.getTouchInfo(r4)     // Catch:{ all -> 0x002a }
            int r2 = r13.x     // Catch:{ all -> 0x002a }
            if (r5 != r2) goto L_0x0107
            int r2 = r13.y     // Catch:{ all -> 0x002a }
            if (r6 == r2) goto L_0x0117
        L_0x0107:
            r13.x = r5     // Catch:{ all -> 0x002a }
            r13.y = r6     // Catch:{ all -> 0x002a }
            r0 = r18
            int r2 = r0.sk     // Catch:{ all -> 0x002a }
            r3 = 104(0x68, float:1.46E-43)
            r7 = 64
            r8 = 1
            skpushMouseEvent(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x002a }
        L_0x0117:
            int r11 = r11 + 1
            goto L_0x00e3
        */
        throw new UnsupportedOperationException("Method not decompiled: ls.sakana.SakanaView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean skSwapBuffers() {
        try {
            return this.skthread.ctx.swap();
        } catch (Exception ex) {
            System.out.println("skSwapBuffers caused an Exception : " + ex);
            return false;
        }
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    private String getAdvertisingID() {
        try {
            return Settings.Secure.getString(getContext().getContentResolver(), "android_id");
        } catch (Exception ex) {
            ex.printStackTrace();
            return getUUID();
        }
    }

    private String getUserName(int option) {
        Account[] accounts = AccountManager.get(getContext()).getAccounts();
        for (int l = 0; l < accounts.length; l++) {
            System.out.println("account[" + l + "] : " + accounts[0].name + " " + accounts[0].type);
        }
        if (accounts.length > 0) {
            return accounts[0].name;
        }
        return "unknown-username";
    }

    private void initializeIAP(String pubkey, boolean debug) {
        if (this.iab != null) {
            skpushStringEvent(this.sk, IAP_INIT_SUCCESS, "IAP already initialized");
            return;
        }
        this.iab = new IabHelper((Activity) getContext(), pubkey);
        if (debug) {
            this.iab.enableDebugLogging(true);
        }
        this.iab.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    System.out.println("onIabSetupFinished failure : " + result.getMessage());
                    if (SakanaView.this.iab != null) {
                        SakanaView.this.iab.dispose();
                        IabHelper unused = SakanaView.this.iab = null;
                    }
                    SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_INIT_FAIL, result.getMessage());
                } else if (SakanaView.this.iab == null) {
                    SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_INIT_FAIL, "IAP already disposed");
                } else {
                    SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_INIT_SUCCESS, result.getMessage());
                }
            }
        });
    }

    private void queryIAPItemsInfo(String idstr) {
        if (this.iab == null) {
            skpushStringEvent(this.sk, IAP_INFO_FAIL, "IAP is not initialized / avalilable");
            return;
        }
        final ArrayList<String> moreSkus = new ArrayList<>();
        System.out.println("queryIAPItemsInfo : " + idstr);
        String[] idlist = idstr.split(",", 0);
        for (String trim : idlist) {
            moreSkus.add(trim.trim());
        }
        final IabHelper.QueryInventoryFinishedListener listener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if (result.isFailure()) {
                    synchronized (SakanaView.this.skthread) {
                        SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_INFO_FAIL, result.getMessage());
                    }
                    return;
                }
                synchronized (SakanaView.this.skthread) {
                    System.out.println("queryIAPItemsInfo success. num=" + inv.getSkuDetailsList().length);
                    SakanaView.skpushSkuDetailsArrayEvent(SakanaView.this.sk, SakanaView.IAP_INFO_SUCCESS, inv.getSkuDetailsList());
                }
            }
        };
        this.handler.post(new Runnable() {
            public void run() {
                SakanaView.this.iab.querySkuDetailsAsync(moreSkus, listener);
            }
        });
    }

    private void queryIAPOwnItems() {
        if (this.iab == null) {
            skpushStringEvent(this.sk, IAP_OWN_FAIL, "IAP is not initialized / avalilable");
            return;
        }
        final IabHelper.QueryInventoryFinishedListener listener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if (result.isFailure()) {
                    synchronized (SakanaView.this.skthread) {
                        SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_OWN_FAIL, result.getMessage());
                    }
                    return;
                }
                StringBuffer sb = new StringBuffer();
                sb.append("[");
                synchronized (SakanaView.this.iabmap) {
                    String[] pa = inv.getPurchasedIDList();
                    for (int l = 0; l < pa.length; l++) {
                        String sku = pa[l];
                        Purchase purchase = inv.getPurchase(sku);
                        if (purchase != null) {
                            ArrayList<Purchase> a = (ArrayList) SakanaView.this.iabmap.get(sku);
                            if (a == null) {
                                a = new ArrayList<>();
                                SakanaView.this.iabmap.put(sku, a);
                            }
                            a.add(purchase);
                            String receiptdata = "{\"itemid\":\"" + sku + "\",\"orderid\":\"" + purchase.getOrderId() + "\",\"signature\":\"" + purchase.getSignature() + "\",\"receipt\":\"" + Base64.encodeToString(purchase.getOriginalJson().getBytes(), 2) + "\"}";
                            if (l > 0) {
                                sb.append(",");
                            }
                            sb.append(receiptdata);
                        }
                    }
                }
                sb.append("]");
                synchronized (SakanaView.this.skthread) {
                    System.out.println("queryIAPOwnItems success. num=" + inv.getSkuDetailsList().length);
                    SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_OWN_SUCCESS, sb.toString());
                }
            }
        };
        this.handler.post(new Runnable() {
            public void run() {
                SakanaView.this.iab.queryInventoryAsync(listener);
            }
        });
    }

    private void requestIAP(final String itemid) {
        System.out.println("requestIAP : " + itemid);
        if (this.iab == null) {
            skpushStringEvent(this.sk, IAP_PURCHASE_FAIL, "IAP is not initialized / avalilable");
            return;
        }
        final IabHelper.OnIabPurchaseFinishedListener listener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                System.out.println("onIabPurchaseFinished : " + result + ", " + purchase);
                if (result.isFailure()) {
                    synchronized (SakanaView.this.skthread) {
                        SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_PURCHASE_FAIL, result.getMessage());
                    }
                    return;
                }
                String sku = purchase.getSku();
                synchronized (SakanaView.this.iabmap) {
                    ArrayList<Purchase> a = (ArrayList) SakanaView.this.iabmap.get(sku);
                    if (a == null) {
                        a = new ArrayList<>();
                        SakanaView.this.iabmap.put(sku, a);
                    }
                    a.add(purchase);
                }
                String receiptdata = "{\"itemid\":\"" + itemid + "\",\"orderid\":\"" + purchase.getOrderId() + "\",\"signature\":\"" + purchase.getSignature() + "\",\"receipt\":\"" + Base64.encodeToString(purchase.getOriginalJson().getBytes(), 2) + "\"}";
                synchronized (SakanaView.this.skthread) {
                    SakanaView.skpushStringEvent(SakanaView.this.sk, SakanaView.IAP_PURCHASE_SUCCESS, receiptdata);
                }
            }
        };
        this.handler.post(new Runnable() {
            public void run() {
                SakanaView.this.iab.launchPurchaseFlow((Activity) SakanaView.this.getContext(), itemid, 131423, listener, "AJDJAWIJDWJ");
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: ls.sakana.iap.Purchase} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void consumeIAP(final java.lang.String r11) {
        /*
            r10 = this;
            r7 = 1109(0x455, float:1.554E-42)
            ls.sakana.iap.IabHelper r5 = r10.iab
            if (r5 != 0) goto L_0x000e
            int r5 = r10.sk
            java.lang.String r6 = "IAP is not initialized / avalilable"
            skpushStringEvent(r5, r7, r6)
        L_0x000d:
            return
        L_0x000e:
            ls.sakana.SakanaView$9 r3 = new ls.sakana.SakanaView$9
            r3.<init>(r11)
            r4 = 0
            java.util.HashMap<java.lang.String, java.util.ArrayList<ls.sakana.iap.Purchase>> r6 = r10.iabmap
            monitor-enter(r6)
            java.util.HashMap<java.lang.String, java.util.ArrayList<ls.sakana.iap.Purchase>> r5 = r10.iabmap     // Catch:{ all -> 0x003f }
            java.lang.Object r2 = r5.get(r11)     // Catch:{ all -> 0x003f }
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ all -> 0x003f }
            if (r2 == 0) goto L_0x0030
            boolean r5 = r2.isEmpty()     // Catch:{ all -> 0x003f }
            if (r5 != 0) goto L_0x0030
            r5 = 0
            java.lang.Object r5 = r2.remove(r5)     // Catch:{ all -> 0x003f }
            r0 = r5
            ls.sakana.iap.Purchase r0 = (ls.sakana.iap.Purchase) r0     // Catch:{ all -> 0x003f }
            r4 = r0
        L_0x0030:
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            if (r4 == 0) goto L_0x0042
            r1 = r4
            android.os.Handler r5 = r10.handler
            ls.sakana.SakanaView$10 r6 = new ls.sakana.SakanaView$10
            r6.<init>(r1, r3)
            r5.post(r6)
            goto L_0x000d
        L_0x003f:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            throw r5
        L_0x0042:
            ls.sakana.SakanaView$SKThread r6 = r10.skthread
            monitor-enter(r6)
            int r5 = r10.sk     // Catch:{ all -> 0x0067 }
            r7 = 1109(0x455, float:1.554E-42)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r8.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r9 = "consumable item ["
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0067 }
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ all -> 0x0067 }
            java.lang.String r9 = "] not found"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0067 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0067 }
            skpushStringEvent(r5, r7, r8)     // Catch:{ all -> 0x0067 }
            monitor-exit(r6)     // Catch:{ all -> 0x0067 }
            goto L_0x000d
        L_0x0067:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0067 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: ls.sakana.SakanaView.consumeIAP(java.lang.String):void");
    }

    private void startTextEdit(final String str, int flags_len) {
        this.handler.post(new Runnable() {
            public void run() {
                if (SakanaView.this.et != null) {
                    ((ViewGroup) SakanaView.this.et.getParent()).removeView(SakanaView.this.et);
                }
                SakanaView.this.et = new EditText(SakanaView.this.getContext());
                SakanaView.this.et.setTranslationY(0.0f);
                SakanaView.this.et.setBackgroundColor(-1);
                ViewGroup g = (ViewGroup) SakanaView.this.getParent();
                g.addView(SakanaView.this.et, new FrameLayout.LayoutParams(g.getWidth(), 80));
                SakanaView.this.et.requestFocus();
                SakanaView.this.et.setInputType(1);
                if (str != null) {
                    SakanaView.this.et.setText(str);
                    SakanaView.this.et.setSelection(0, str.length());
                }
                ((InputMethodManager) SakanaView.this.getContext().getSystemService("input_method")).showSoftInput(SakanaView.this.et, 2);
                SakanaView.this.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == 6) {
                            System.out.println("IME_ACTION_DONE " + v.getText());
                            synchronized (SakanaView.this.skthread) {
                                SakanaView.skpushStringEvent(SakanaView.this.sk, 1000, v.getText().toString());
                            }
                            SakanaView.this.endTextEdit();
                            SakanaView.this.hideNavigationBar();
                            return false;
                        }
                        System.out.println("actionId == " + actionId);
                        return true;
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void hideNavigationBar() {
        View decor = ((Activity) getContext()).getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 18) {
            decor.setSystemUiVisibility(4102);
        } else if (Build.VERSION.SDK_INT > 15) {
            decor.setSystemUiVisibility(6);
        } else if (Build.VERSION.SDK_INT > 13) {
            decor.setSystemUiVisibility(2);
        }
    }

    /* access modifiers changed from: private */
    public void endTextEdit() {
        this.handler.post(new Runnable() {
            public void run() {
                if (SakanaView.this.et != null) {
                    ((ViewGroup) SakanaView.this.et.getParent()).removeView(SakanaView.this.et);
                    SakanaView.this.et = null;
                }
            }
        });
    }

    private int startAcceleroMeter() {
        if (this.sensorman == null) {
            this.sensorman = (SensorManager) getContext().getSystemService("sensor");
        }
        if (this.accel == null) {
            this.accel = this.sensorman.getDefaultSensor(1);
        }
        this.sensorman.registerListener(this, this.accel, 2);
        return 0;
    }

    private int stopAcceleroMeter() {
        if (this.accel == null) {
            return 0;
        }
        this.sensorman.unregisterListener(this, this.accel);
        return 0;
    }

    private int startGyroScope() {
        if (this.sensorman == null) {
            this.sensorman = (SensorManager) getContext().getSystemService("sensor");
        }
        if (this.gyro == null) {
            this.gyro = this.sensorman.getDefaultSensor(4);
        }
        this.sensorman.registerListener(this, this.gyro, 2);
        return 0;
    }

    private int stopGyroScope() {
        if (this.gyro == null) {
            return 0;
        }
        this.sensorman.unregisterListener(this, this.gyro);
        return 0;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent e) {
        switch (e.sensor.getType()) {
            case 1:
            case 4:
                int eid = e.sensor.getType() == 1 ? ACCEL_CHANGED : GYRO_CHANGED;
                synchronized (this.skthread) {
                    if (this.sk != 0) {
                        skpushStringEvent(this.sk, eid, new StringBuffer().append("[").append(e.values[0]).append(", ").append(e.values[1]).append(", ").append(e.values[2]).append("]").toString());
                        return;
                    }
                    return;
                }
            default:
                return;
        }
    }

    public SakanaWebView createWebView() {
        return new SakanaWebView(this);
    }

    public SakanaMoviePlayer createMoviePlayer(int tex) {
        return new SakanaMoviePlayer(this, tex);
    }

    private void callInUIThread(final long ptr) {
        this.handler.post(new Runnable() {
            public void run() {
                synchronized (SakanaView.this.skthread) {
                    SakanaView.skcalleduithread(SakanaView.this.sk, ptr);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void skpushSkuDetailsArrayEvent(int sk2, int eid, SkuDetails[] skus) {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        for (int l = 0; l < skus.length; l++) {
            String desc = NotificationCompat.CATEGORY_ERROR;
            SkuDetails s = skus[l];
            try {
                desc = URLEncoder.encode(s.getDescription(), "UTF-8");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sb.append("{\"itemid\":\"");
            sb.append(s.getSku());
            sb.append("\",\"desc\":\"");
            sb.append(desc);
            sb.append("\",\"type\":\"");
            sb.append(s.getType());
            sb.append("\",\"pricetxt\":\"");
            sb.append(s.getPrice());
            sb.append("\",\"priceamt\":");
            sb.append(getPriceAmount(s.getPrice()));
            if (l < skus.length - 1) {
                sb.append("},\n");
            } else {
                sb.append("}\n");
            }
        }
        sb.append("]\n");
        String s2 = sb.toString();
        System.out.println("sku_details = " + s2);
        skpushSkuStringArrayEvent(sk2, eid, s2);
    }

    private static int getPriceAmount(String s) {
        int i = 0;
        for (int l = 0; l < s.length(); l++) {
            char c = s.charAt(l);
            if (c >= '0' && c <= '9') {
                i = (i * 10) + (c - '0');
            }
        }
        return i * 100;
    }

    private void checksk() {
        if (this.sk == 0) {
            throw new IllegalStateException("SAKANAGL IS NULL!!!!!!!");
        }
    }

    class SKThread extends Thread {
        int appactivestate = 1;
        SakanaContext ctx = new SakanaContext();
        int loopi;
        Runnable proc;
        HashMap<String, SakanaFunction> regfunc = new HashMap<>();
        int skstate = 0;
        final int[] sleeptime = {0, 0, 40, 50, 100};

        SKThread() {
            super("SKThread");
        }

        /* access modifiers changed from: package-private */
        public synchronized int getSKState() {
            return this.skstate;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setSKState(int s) {
            if (s != this.skstate) {
                System.out.println("skstate change from:" + this.skstate + " to:" + s);
                this.skstate = s;
            }
            notifyAll();
        }

        /* access modifiers changed from: package-private */
        public synchronized void startAndWait() {
            setSKState(1);
            super.start();
            while (this.skstate == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void exitAndWait() {
            if (!(this.skstate == 4 || this.skstate == -1)) {
                setSKState(3);
                while (this.skstate == 3) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void registerSakanaFunction(final String name, final SakanaFunction func) {
            if (this.skstate != 2) {
                this.regfunc.put(name, func);
            } else {
                invokeAndWait(new Runnable() {
                    public void run() {
                        SKThread.this.regfunc.put(name, func);
                        SakanaView.skregfunc(SakanaView.this.sk, name, func);
                    }
                });
            }
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
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public void run() {
            /*
                r20 = this;
                r0 = r20
                ls.sakana.SakanaContext r15 = r0.ctx     // Catch:{ Exception -> 0x010d }
                r15.createContext()     // Catch:{ Exception -> 0x010d }
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d }
                r16.<init>()     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = "EGL Context created. es version="
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaContext r0 = r0.ctx     // Catch:{ Exception -> 0x010d }
                r17 = r0
                r0 = r17
                int r0 = r0.esver     // Catch:{ Exception -> 0x010d }
                r17 = r0
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = r16.toString()     // Catch:{ Exception -> 0x010d }
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r16 = ls.sakana.SakanaView.sknew()     // Catch:{ Exception -> 0x010d }
                int unused = r15.sk = r16     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r13 = r15.getPackageVersion()     // Catch:{ Exception -> 0x010d }
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d }
                r16.<init>()     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = "sxr package check. version="
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                r0 = r16
                java.lang.StringBuilder r16 = r0.append(r13)     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = r16.toString()     // Catch:{ Exception -> 0x010d }
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d }
                r15.<init>()     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                r16 = r0
                android.content.Context r16 = r16.getContext()     // Catch:{ Exception -> 0x010d }
                java.io.File r16 = r16.getCacheDir()     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = r16.getPath()     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "/main.sxr"
                java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Exception -> 0x010d }
                java.lang.String r10 = r15.toString()     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r15 = r15.sk     // Catch:{ Exception -> 0x010d }
                boolean r15 = ls.sakana.SakanaView.skchecksxr(r15, r10, r13)     // Catch:{ Exception -> 0x010d }
                if (r15 != 0) goto L_0x0181
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "Copying sxr packages to cache dir."
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                android.content.Context r15 = r15.getContext()     // Catch:{ Exception -> 0x010d }
                java.io.File r15 = r15.getCacheDir()     // Catch:{ Exception -> 0x010d }
                java.io.File[] r15 = r15.listFiles()     // Catch:{ Exception -> 0x010d }
                ls.sakana.SakanaView.deleteFiles(r15)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "sxr"
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                r17 = r0
                android.content.Context r17 = r17.getContext()     // Catch:{ Exception -> 0x010d }
                java.io.File r17 = r17.getCacheDir()     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = r17.getPath()     // Catch:{ Exception -> 0x010d }
                r15.copyassets(r16, r17)     // Catch:{ Exception -> 0x010d }
            L_0x00c0:
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                java.lang.String[] r15 = r15.appargs     // Catch:{ Exception -> 0x010d }
                if (r15 != 0) goto L_0x018a
                r15 = 2
                java.lang.String[] r2 = new java.lang.String[r15]     // Catch:{ Exception -> 0x010d }
                r15 = 0
                java.lang.String r16 = "-main"
                r2[r15] = r16     // Catch:{ Exception -> 0x010d }
                r15 = 1
                r2[r15] = r10     // Catch:{ Exception -> 0x010d }
            L_0x00d5:
                monitor-enter(r20)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                java.util.HashMap<java.lang.String, ls.sakana.SakanaFunction> r15 = r0.regfunc     // Catch:{ all -> 0x010a }
                java.util.Set r15 = r15.entrySet()     // Catch:{ all -> 0x010a }
                java.util.Iterator r8 = r15.iterator()     // Catch:{ all -> 0x010a }
            L_0x00e2:
                boolean r15 = r8.hasNext()     // Catch:{ all -> 0x010a }
                if (r15 == 0) goto L_0x01c5
                java.lang.Object r6 = r8.next()     // Catch:{ all -> 0x010a }
                java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ all -> 0x010a }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x010a }
                int r17 = r15.sk     // Catch:{ all -> 0x010a }
                java.lang.Object r15 = r6.getKey()     // Catch:{ all -> 0x010a }
                java.lang.String r15 = (java.lang.String) r15     // Catch:{ all -> 0x010a }
                java.lang.Object r16 = r6.getValue()     // Catch:{ all -> 0x010a }
                ls.sakana.SakanaFunction r16 = (ls.sakana.SakanaFunction) r16     // Catch:{ all -> 0x010a }
                r0 = r17
                r1 = r16
                ls.sakana.SakanaView.skregfunc(r0, r15, r1)     // Catch:{ all -> 0x010a }
                goto L_0x00e2
            L_0x010a:
                r15 = move-exception
                monitor-exit(r20)     // Catch:{ all -> 0x010a }
                throw r15     // Catch:{ Exception -> 0x010d }
            L_0x010d:
                r5 = move-exception
                r5.printStackTrace()
                java.io.PrintStream r15 = java.lang.System.out
                java.lang.String r16 = "WARNING : SakanaGL main thread abnormally finished..."
                r15.println(r16)
                r15 = -1
                r0 = r20
                r0.setSKState(r15)
            L_0x011e:
                r0 = r20
                ls.sakana.SakanaContext r15 = r0.ctx
                r15.destroySurface()
                r0 = r20
                ls.sakana.SakanaContext r15 = r0.ctx
                r15.finish()
                monitor-enter(r20)
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x0317 }
                int r15 = r15.sk     // Catch:{ all -> 0x0317 }
                ls.sakana.SakanaView.skkill(r15)     // Catch:{ all -> 0x0317 }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x0317 }
                r16 = 0
                int unused = r15.sk = r16     // Catch:{ all -> 0x0317 }
                monitor-exit(r20)     // Catch:{ all -> 0x0317 }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this
                r16 = r0
                monitor-enter(r16)
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x0329 }
                java.util.ArrayList r15 = r15.listeners     // Catch:{ all -> 0x0329 }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x0329 }
                r17 = r0
                java.util.ArrayList r17 = r17.listeners     // Catch:{ all -> 0x0329 }
                int r17 = r17.size()     // Catch:{ all -> 0x0329 }
                r0 = r17
                ls.sakana.SakanaViewListener[] r0 = new ls.sakana.SakanaViewListener[r0]     // Catch:{ all -> 0x0329 }
                r17 = r0
                r0 = r17
                java.lang.Object[] r3 = r15.toArray(r0)     // Catch:{ all -> 0x0329 }
                ls.sakana.SakanaViewListener[] r3 = (ls.sakana.SakanaViewListener[]) r3     // Catch:{ all -> 0x0329 }
                r9 = 0
            L_0x016e:
                int r15 = r3.length     // Catch:{ all -> 0x0329 }
                if (r9 >= r15) goto L_0x031a
                r15 = r3[r9]     // Catch:{ all -> 0x0329 }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ all -> 0x0329 }
                r17 = r0
                r0 = r17
                r15.skFinished(r0)     // Catch:{ all -> 0x0329 }
                int r9 = r9 + 1
                goto L_0x016e
            L_0x0181:
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "sxr package check ok."
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                goto L_0x00c0
            L_0x018a:
                java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x010d }
                r4.<init>()     // Catch:{ Exception -> 0x010d }
                java.lang.String r15 = "-main"
                r4.add(r15)     // Catch:{ Exception -> 0x010d }
                r4.add(r10)     // Catch:{ Exception -> 0x010d }
                r9 = 0
            L_0x0198:
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                java.lang.String[] r15 = r15.appargs     // Catch:{ Exception -> 0x010d }
                int r15 = r15.length     // Catch:{ Exception -> 0x010d }
                if (r9 >= r15) goto L_0x01b3
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                java.lang.String[] r15 = r15.appargs     // Catch:{ Exception -> 0x010d }
                r15 = r15[r9]     // Catch:{ Exception -> 0x010d }
                r4.add(r15)     // Catch:{ Exception -> 0x010d }
                int r9 = r9 + 1
                goto L_0x0198
            L_0x01b3:
                int r15 = r4.size()     // Catch:{ Exception -> 0x010d }
                java.lang.String[] r15 = new java.lang.String[r15]     // Catch:{ Exception -> 0x010d }
                java.lang.Object[] r15 = r4.toArray(r15)     // Catch:{ Exception -> 0x010d }
                java.lang.String[] r15 = (java.lang.String[]) r15     // Catch:{ Exception -> 0x010d }
                r0 = r15
                java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ Exception -> 0x010d }
                r2 = r0
                goto L_0x00d5
            L_0x01c5:
                monitor-exit(r20)     // Catch:{ all -> 0x010a }
                r15 = 2
                r0 = r20
                r0.setSKState(r15)     // Catch:{ Exception -> 0x010d }
            L_0x01cc:
                int r15 = r20.getSKState()     // Catch:{ Exception -> 0x010d }
                r16 = 2
                r0 = r16
                if (r15 != r0) goto L_0x01f5
                monitor-enter(r20)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                java.lang.Runnable r15 = r0.proc     // Catch:{ all -> 0x0280 }
                if (r15 == 0) goto L_0x01ec
                r0 = r20
                java.lang.Runnable r15 = r0.proc     // Catch:{ all -> 0x0280 }
                r15.run()     // Catch:{ all -> 0x0280 }
                r15 = 0
                r0 = r20
                r0.proc = r15     // Catch:{ all -> 0x0280 }
                r20.notifyAll()     // Catch:{ all -> 0x0280 }
            L_0x01ec:
                monitor-exit(r20)     // Catch:{ all -> 0x0280 }
                r0 = r20
                ls.sakana.SakanaContext r15 = r0.ctx     // Catch:{ Exception -> 0x010d }
                javax.microedition.khronos.egl.EGLSurface r15 = r15.surface     // Catch:{ Exception -> 0x010d }
                if (r15 == 0) goto L_0x0283
            L_0x01f5:
                android.util.DisplayMetrics r11 = new android.util.DisplayMetrics     // Catch:{ Exception -> 0x010d }
                r11.<init>()     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                android.content.Context r15 = r15.getContext()     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "window"
                java.lang.Object r14 = r15.getSystemService(r16)     // Catch:{ Exception -> 0x010d }
                android.view.WindowManager r14 = (android.view.WindowManager) r14     // Catch:{ Exception -> 0x010d }
                android.view.Display r15 = r14.getDefaultDisplay()     // Catch:{ Exception -> 0x010d }
                r15.getRealMetrics(r11)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r15 = r15.sk     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                r16 = r0
                int r16 = r16.getWidth()     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                r17 = r0
                int r17 = r17.getHeight()     // Catch:{ Exception -> 0x010d }
                float r0 = r11.xdpi     // Catch:{ Exception -> 0x010d }
                r18 = r0
                float r0 = r11.ydpi     // Catch:{ Exception -> 0x010d }
                r19 = r0
                int unused = ls.sakana.SakanaView.skmakewindow(r15, r16, r17, r18, r19)     // Catch:{ Exception -> 0x010d }
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d }
                r16.<init>()     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = "sakana arguments : "
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                int r0 = r2.length     // Catch:{ Exception -> 0x010d }
                r17 = r0
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = r16.toString()     // Catch:{ Exception -> 0x010d }
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                r9 = 0
            L_0x0254:
                int r15 = r2.length     // Catch:{ Exception -> 0x010d }
                if (r9 >= r15) goto L_0x028a
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010d }
                r16.<init>()     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = "["
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                r0 = r16
                java.lang.StringBuilder r16 = r0.append(r9)     // Catch:{ Exception -> 0x010d }
                java.lang.String r17 = "] : "
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                r17 = r2[r9]     // Catch:{ Exception -> 0x010d }
                java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = r16.toString()     // Catch:{ Exception -> 0x010d }
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                int r9 = r9 + 1
                goto L_0x0254
            L_0x0280:
                r15 = move-exception
                monitor-exit(r20)     // Catch:{ all -> 0x0280 }
                throw r15     // Catch:{ Exception -> 0x010d }
            L_0x0283:
                r16 = 10
                java.lang.Thread.sleep(r16)     // Catch:{ Exception -> 0x010d }
                goto L_0x01cc
            L_0x028a:
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r15 = r15.sk     // Catch:{ Exception -> 0x010d }
                r0 = r20
                ls.sakana.SakanaView r0 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                r16 = r0
                r0 = r16
                boolean unused = ls.sakana.SakanaView.skmain(r15, r0, r2)     // Catch:{ Exception -> 0x010d }
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "sakana loop start!"
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
            L_0x02a4:
                int r15 = r20.getSKState()     // Catch:{ Exception -> 0x010d }
                r16 = 2
                r0 = r16
                if (r15 != r0) goto L_0x02dc
                monitor-enter(r20)     // Catch:{ Exception -> 0x010d }
                r0 = r20
                java.lang.Runnable r15 = r0.proc     // Catch:{ all -> 0x02eb }
                if (r15 == 0) goto L_0x02c4
                r0 = r20
                java.lang.Runnable r15 = r0.proc     // Catch:{ all -> 0x02eb }
                r15.run()     // Catch:{ all -> 0x02eb }
                r15 = 0
                r0 = r20
                r0.proc = r15     // Catch:{ all -> 0x02eb }
                r20.notifyAll()     // Catch:{ all -> 0x02eb }
            L_0x02c4:
                monitor-exit(r20)     // Catch:{ all -> 0x02eb }
                r0 = r20
                ls.sakana.SakanaView r15 = ls.sakana.SakanaView.this     // Catch:{ Exception -> 0x010d }
                int r15 = r15.sk     // Catch:{ Exception -> 0x010d }
                int r7 = ls.sakana.SakanaView.skloop(r15)     // Catch:{ Exception -> 0x010d }
                r15 = -2147483648(0xffffffff80000000, float:-0.0)
                if (r7 != r15) goto L_0x02ee
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "sakana loop done."
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
            L_0x02dc:
                java.io.PrintStream r15 = java.lang.System.out     // Catch:{ Exception -> 0x010d }
                java.lang.String r16 = "SakanaGL main thread normally finished."
                r15.println(r16)     // Catch:{ Exception -> 0x010d }
                r15 = 3
                r0 = r20
                r0.setSKState(r15)     // Catch:{ Exception -> 0x010d }
                goto L_0x011e
            L_0x02eb:
                r15 = move-exception
                monitor-exit(r20)     // Catch:{ all -> 0x02eb }
                throw r15     // Catch:{ Exception -> 0x010d }
            L_0x02ee:
                r0 = r20
                int r15 = r0.appactivestate     // Catch:{ Exception -> 0x010d }
                r16 = 1
                r0 = r16
                if (r15 != r0) goto L_0x0306
                r15 = r7 & 1
                if (r15 == 0) goto L_0x0306
                r12 = 2
            L_0x02fd:
                if (r12 <= 0) goto L_0x0313
                long r0 = (long) r12     // Catch:{ Exception -> 0x010d }
                r16 = r0
                java.lang.Thread.sleep(r16)     // Catch:{ Exception -> 0x010d }
                goto L_0x02a4
            L_0x0306:
                r0 = r20
                int[] r15 = r0.sleeptime     // Catch:{ Exception -> 0x010d }
                r0 = r20
                int r0 = r0.appactivestate     // Catch:{ Exception -> 0x010d }
                r16 = r0
                r12 = r15[r16]     // Catch:{ Exception -> 0x010d }
                goto L_0x02fd
            L_0x0313:
                java.lang.Thread.yield()     // Catch:{ Exception -> 0x010d }
                goto L_0x02a4
            L_0x0317:
                r15 = move-exception
                monitor-exit(r20)     // Catch:{ all -> 0x0317 }
                throw r15
            L_0x031a:
                monitor-exit(r16)     // Catch:{ all -> 0x0329 }
                r15 = 4
                r0 = r20
                r0.setSKState(r15)
                java.io.PrintStream r15 = java.lang.System.out
                java.lang.String r16 = "SakanaGL thread terminated."
                r15.println(r16)
                return
            L_0x0329:
                r15 = move-exception
                monitor-exit(r16)     // Catch:{ all -> 0x0329 }
                throw r15
            */
            throw new UnsupportedOperationException("Method not decompiled: ls.sakana.SakanaView.SKThread.run():void");
        }

        /* access modifiers changed from: package-private */
        public synchronized void invokeAndWait(Runnable p) {
            if (this.skstate != 2) {
                throw new IllegalStateException("SakanaGL is not running. state=" + this.skstate);
            } else if (Thread.currentThread() == this) {
                p.run();
            } else {
                this.proc = p;
                do {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (this.proc == p);
            }
            return;
        }

        /* access modifiers changed from: package-private */
        public synchronized void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            System.out.println("ctx.surfaceChanged. " + getSKState() + "  (" + width + "," + height + ") v(" + SakanaView.this.getWidth() + "," + SakanaView.this.getHeight() + ") : " + format);
            final int w = width;
            final int h = height;
            invokeAndWait(new Runnable() {
                public void run() {
                    if (SakanaView.this.sk != 0) {
                        DisplayMetrics met = new DisplayMetrics();
                        ((WindowManager) SakanaView.this.getContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(met);
                        SakanaView.skresized(SakanaView.this.sk, w, h, met.xdpi, met.ydpi);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public synchronized void surfaceCreated(SurfaceHolder holder) {
            System.out.println("ctx.createSurface. " + getSKState() + "  " + SakanaView.this.getWidth() + " , " + SakanaView.this.getHeight());
            if (this.skstate == 2) {
                final SurfaceHolder h = holder;
                invokeAndWait(new Runnable() {
                    public void run() {
                        if (SakanaView.this.sk != 0) {
                            SKThread.this.ctx.createSurface(SakanaView.this, h);
                            SakanaView.skenableGL(SakanaView.this.sk);
                        }
                    }
                });
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void surfaceDestroyed(SurfaceHolder holder) {
            System.out.println("ctx.surfaceDestroyed. skstate=" + getSKState());
            if (this.skstate == 2) {
                invokeAndWait(new Runnable() {
                    public void run() {
                        if (SakanaView.this.sk != 0) {
                            SakanaView.skdisableGL(SakanaView.this.sk);
                        }
                        SKThread.this.ctx.destroySurface();
                    }
                });
            }
        }
    }

    class TouchInfo {
        int clicks;
        long presstime;
        int pressx;
        int pressy;
        int x;
        int y;

        TouchInfo() {
        }
    }
}

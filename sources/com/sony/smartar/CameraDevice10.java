package com.sony.smartar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import com.sony.smartar.CameraDeviceInterface;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class CameraDevice10 implements CameraDeviceInterface {
    private static boolean H = false;
    /* access modifiers changed from: private */
    public static final Handler I = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    CameraDevice10 cameraDevice10 = (CameraDevice10) message.obj;
                    if (!cameraDevice10.A) {
                        Camera.Parameters parameters = cameraDevice10.n.getParameters();
                        f.c(parameters, true);
                        cameraDevice10.n.setParameters(parameters);
                        synchronized (b.b) {
                            if (b.a != 0) {
                                CameraDevice10.onAutoExposure(b.a, true);
                            }
                        }
                        boolean unused = cameraDevice10.D = false;
                        return;
                    }
                    return;
                case 2:
                    CameraDevice10 cameraDevice102 = (CameraDevice10) message.obj;
                    if (!cameraDevice102.A) {
                        Camera.Parameters parameters2 = cameraDevice102.n.getParameters();
                        f.b(parameters2, true);
                        cameraDevice102.n.setParameters(parameters2);
                        synchronized (b.b) {
                            if (b.a != 0) {
                                CameraDevice10.onAutoWhiteBalance(b.a, true);
                            }
                        }
                        boolean unused2 = cameraDevice102.E = false;
                        return;
                    }
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean A = false;
    /* access modifiers changed from: private */
    public boolean B = false;
    /* access modifiers changed from: private */
    public boolean C = false;
    /* access modifiers changed from: private */
    public boolean D = false;
    /* access modifiers changed from: private */
    public boolean E = false;
    /* access modifiers changed from: private */
    public boolean F = false;
    /* access modifiers changed from: private */
    public boolean G = false;
    private final SurfaceHolder.Callback J = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            boolean unused = CameraDevice10.this.z = true;
            CameraDevice10.this.d();
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            boolean unused = CameraDevice10.this.z = false;
            CameraDevice10.this.e();
        }
    };
    private final Camera.PreviewCallback K = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] bArr, Camera camera) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (CameraDevice10.this.y) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < 2) {
                        if (bArr == CameraDevice10.this.s.get(i2)) {
                            synchronized (b.b) {
                                if (b.a != 0) {
                                    CameraDevice10.onVideoImage(b.a, bArr, CameraDevice10.this.u, CameraDevice10.this.v, CameraDevice10.this.t, uptimeMillis);
                                }
                            }
                            CameraDevice10.this.n.addCallbackBuffer(bArr);
                        }
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
                while (true) {
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final Camera.ShutterCallback L = new Camera.ShutterCallback() {
        public void onShutter() {
            if (!CameraDevice10.this.A) {
                synchronized (b.b) {
                    if (b.a != 0) {
                        CameraDevice10.onShutter(b.a);
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final Camera.PictureCallback M = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] bArr, Camera camera) {
            if (!CameraDevice10.this.A) {
                Camera.Parameters parameters = CameraDevice10.this.n.getParameters();
                if (CameraDevice10.this.k && Build.VERSION.SDK_INT >= 14) {
                    f.a(parameters, true);
                    CameraDevice10.this.n.setParameters(parameters);
                }
                CameraDevice10.this.d();
                Camera.Size pictureSize = parameters.getPictureSize();
                int[] iArr = new int[1];
                Utility.a(false, (List<Integer>) Collections.singletonList(Integer.valueOf(parameters.getPictureFormat())), iArr, 1);
                long uptimeMillis = SystemClock.uptimeMillis();
                synchronized (b.b) {
                    if (b.a != 0) {
                        CameraDevice10.onStillImage(b.a, bArr, pictureSize.width, pictureSize.height, iArr[0], uptimeMillis);
                    }
                }
                boolean unused = CameraDevice10.this.B = false;
            }
        }
    };
    private final Camera.AutoFocusCallback N = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean z, Camera camera) {
            if (!CameraDevice10.this.A) {
                synchronized (b.b) {
                    if (b.a != 0) {
                        CameraDevice10.onAutoFocus(b.a, z);
                    }
                }
                String focusMode = camera.getParameters().getFocusMode();
                if (focusMode.compareTo("continuous-video") == 0 || focusMode.compareTo("continuous-picture") == 0) {
                    camera.cancelAutoFocus();
                }
                boolean unused = CameraDevice10.this.C = false;
            }
        }
    };
    private final Camera.ErrorCallback O = new Camera.ErrorCallback() {
        public void onError(int i, Camera camera) {
            synchronized (b.b) {
                if (b.a != 0) {
                    CameraDevice10.onError(b.a, i);
                }
            }
        }
    };
    private final int a;
    private final int b;
    private String c;
    private String d;
    private String e;
    private String f;
    private int g;
    private final boolean h;
    private final boolean i;
    /* access modifiers changed from: private */
    public final boolean j;
    /* access modifiers changed from: private */
    public final boolean k;
    private final boolean l;
    /* access modifiers changed from: private */
    public Context m;
    /* access modifiers changed from: private */
    public Camera n;
    private int o;
    /* access modifiers changed from: private */
    public SurfaceView p = null;
    private SurfaceTexture q = null;
    private int r = -1;
    /* access modifiers changed from: private */
    public ArrayList<byte[]> s = new ArrayList<>(2);
    /* access modifiers changed from: private */
    public int t = 1;
    /* access modifiers changed from: private */
    public int u = 640;
    /* access modifiers changed from: private */
    public int v = 480;
    /* access modifiers changed from: private */
    public SurfaceView w = null;
    private boolean x = false;
    /* access modifiers changed from: private */
    public boolean y = false;
    /* access modifiers changed from: private */
    public boolean z = false;

    /* access modifiers changed from: private */
    public static native void onAutoExposure(long j2, boolean z2);

    /* access modifiers changed from: private */
    public static native void onAutoFocus(long j2, boolean z2);

    /* access modifiers changed from: private */
    public static native void onAutoWhiteBalance(long j2, boolean z2);

    /* access modifiers changed from: private */
    public static native void onError(long j2, int i2);

    /* access modifiers changed from: private */
    public static native void onShutter(long j2);

    /* access modifiers changed from: private */
    public static native void onStillImage(long j2, byte[] bArr, int i2, int i3, int i4, long j3);

    /* access modifiers changed from: private */
    public static native void onVideoImage(long j2, byte[] bArr, int i2, int i3, int i4, long j3);

    public CameraDevice10(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        boolean z7 = true;
        int i2 = 480;
        synchronized (c.a) {
            synchronized (b.b) {
                b.a = 0;
            }
            this.a = z2 ? 640 : 480;
            this.b = !z2 ? 320 : i2;
            this.l = z3;
            this.h = z4;
            this.i = z5;
            this.j = z6;
            if (!j.a || !this.h || Build.VERSION.SDK_INT < 14 || (Build.VERSION.SDK_INT >= 17 && (Build.VERSION.SDK_INT != 17 || this.i))) {
                z7 = false;
            }
            this.k = z7;
        }
    }

    public int open(Context context, long j2, int i2, Object obj, boolean z2) {
        int i3;
        Context context2;
        Class<?> cls;
        boolean z3 = true;
        synchronized (c.a) {
            if (obj != null) {
                try {
                    if (!(obj instanceof Camera)) {
                        return -2138308603;
                    }
                } catch (RuntimeException e2) {
                    return -2138308594;
                }
            }
            this.m = context;
            synchronized (b.b) {
                b.a = j2;
            }
            if (Build.VERSION.SDK_INT >= 9) {
                i3 = f.a();
            } else {
                i3 = 1;
            }
            if (i2 < 0 || i2 >= i3) {
                this.o = getDefaultCameraId(false);
            } else {
                this.o = i2;
            }
            H = false;
            if (obj != null) {
                this.n = (Camera) obj;
            } else {
                if (i3 > 1) {
                    Object b2 = f.b();
                    f.a(this.o, b2);
                    if (f.a(b2) == 1) {
                        z3 = false;
                    }
                    a(getDefaultCameraId(z3), (Camera) null);
                }
                if (z2) {
                    this.n = b(this.o);
                } else if (Build.VERSION.SDK_INT >= 9) {
                    this.n = f.a(this.o);
                } else {
                    this.n = Camera.open();
                }
                if (this.n == null) {
                    return -2138308594;
                }
            }
            try {
                a(this.o, this.n);
                try {
                    context2 = context.createPackageContext("com.sonyericsson.android.camera", 3);
                } catch (PackageManager.NameNotFoundException e3) {
                    context2 = null;
                }
                if (context2 != null) {
                    try {
                        cls = i.a("com.sonyericsson.android.camera.device.CameraExtensionValues", false, context2.getClassLoader());
                    } catch (h e4) {
                        cls = null;
                    }
                } else {
                    cls = null;
                }
                if (cls != null) {
                    this.c = (String) a(cls, "KEY_EX_VIDEO_MODE");
                    this.d = (String) a(cls, "EX_ON");
                    this.e = (String) a(cls, "KEY_EX_FOCUS_AREA");
                    this.f = (String) a(cls, "EX_FOCUS_AREA_CENTER");
                } else {
                    this.c = null;
                    this.d = null;
                    this.e = null;
                    this.f = null;
                }
                this.g = 0;
                c();
                return 0;
            } catch (RuntimeException e5) {
                this.n.release();
                return -2138308594;
            }
        }
    }

    public static int getDefaultCameraId(boolean z2) {
        try {
            if (Build.VERSION.SDK_INT >= 9) {
                int i2 = z2 ? 1 : 0;
                Object b2 = f.b();
                int a2 = f.a();
                for (int i3 = 0; i3 < a2; i3++) {
                    f.a(i3, b2);
                    if (f.a(b2) == i2) {
                        return i3;
                    }
                }
            }
            return -1;
        } catch (RuntimeException e2) {
            return -1;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void release() {
        /*
            r6 = this;
            java.lang.Object r1 = com.sony.smartar.c.a
            monitor-enter(r1)
            boolean r0 = r6.A     // Catch:{ RuntimeException -> 0x003b }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
        L_0x0008:
            return
        L_0x0009:
            java.lang.Object r2 = com.sony.smartar.b.b     // Catch:{ RuntimeException -> 0x003b }
            monitor-enter(r2)     // Catch:{ RuntimeException -> 0x003b }
            android.os.Handler r0 = I     // Catch:{ all -> 0x0038 }
            r3 = 0
            r0.removeCallbacksAndMessages(r3)     // Catch:{ all -> 0x0038 }
            r4 = 0
            com.sony.smartar.b.a = r4     // Catch:{ all -> 0x0038 }
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            r0 = 1
            r6.A = r0     // Catch:{ RuntimeException -> 0x003b }
            android.view.SurfaceView r0 = r6.p     // Catch:{ RuntimeException -> 0x003b }
            if (r0 == 0) goto L_0x0029
            android.view.SurfaceView r0 = r6.p     // Catch:{ RuntimeException -> 0x003b }
            android.view.SurfaceHolder r0 = r0.getHolder()     // Catch:{ RuntimeException -> 0x003b }
            android.view.SurfaceHolder$Callback r2 = r6.J     // Catch:{ RuntimeException -> 0x003b }
            r0.removeCallback(r2)     // Catch:{ RuntimeException -> 0x003b }
        L_0x0029:
            android.hardware.Camera r0 = r6.n     // Catch:{ RuntimeException -> 0x003b }
            r0.release()     // Catch:{ RuntimeException -> 0x003b }
            java.util.ArrayList<byte[]> r0 = r6.s     // Catch:{ RuntimeException -> 0x003b }
            r0.clear()     // Catch:{ RuntimeException -> 0x003b }
        L_0x0033:
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x0008
        L_0x0035:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            throw r0
        L_0x0038:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0038 }
            throw r0     // Catch:{ RuntimeException -> 0x003b }
        L_0x003b:
            r0 = move-exception
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.release():void");
    }

    public int setNativeVideoOutput(SurfaceView surfaceView) {
        String str;
        boolean z2 = true;
        synchronized (c.a) {
            try {
                if (this.p == surfaceView) {
                    return 0;
                }
                e();
                if (this.p != null) {
                    this.p.getHolder().removeCallback(this.J);
                }
                this.p = surfaceView;
                if (this.p != null) {
                    this.p.getHolder().addCallback(this.J);
                    if (Build.VERSION.SDK_INT >= 11) {
                        this.z = this.p.getHolder().getSurface().isValid();
                    } else {
                        try {
                            if (Build.VERSION.SDK_INT >= 9) {
                                str = "mNativeSurface";
                            } else {
                                str = "mSurface";
                            }
                            Field declaredField = Surface.class.getDeclaredField(str);
                            declaredField.setAccessible(true);
                            if (declaredField.getInt(this.p.getHolder().getSurface()) == 0) {
                                z2 = false;
                            }
                            this.z = z2;
                        } catch (IllegalAccessException e2) {
                            return -2138308603;
                        } catch (NoSuchFieldException e3) {
                            return -2138308603;
                        }
                    }
                } else {
                    this.z = false;
                }
                d();
                return 0;
            } catch (RuntimeException e4) {
                return -2138308594;
            }
        }
    }

    public int setVideoImageSize(int i2, int i3) {
        int i4 = 0;
        synchronized (c.a) {
            try {
                Camera.Parameters parameters = this.n.getParameters();
                Camera.Size previewSize = parameters.getPreviewSize();
                if (previewSize.width != i2 || previewSize.height != i3) {
                    e();
                    parameters.setPreviewSize(i2, i3);
                    this.n.setParameters(parameters);
                    d();
                }
            } catch (RuntimeException e2) {
                i4 = -2138308594;
            }
        }
        return i4;
    }

    public int setVideoImageFormat(int i2) {
        int i3 = 0;
        synchronized (c.a) {
            try {
                int a2 = Utility.a(false, i2);
                if (a2 == -1) {
                    i3 = -2138308603;
                } else {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (parameters.getPreviewFormat() != a2) {
                        e();
                        parameters.setPreviewFormat(a2);
                        this.n.setParameters(parameters);
                        d();
                    }
                }
            } catch (RuntimeException e2) {
                i3 = -2138308594;
            }
        }
        return i3;
    }

    public int setVideoImageFpsRange(int i2, int i3) {
        int i4;
        synchronized (c.a) {
            try {
                e();
                Camera.Parameters parameters = this.n.getParameters();
                if (Build.VERSION.SDK_INT >= 9) {
                    f.a(parameters, i2, i3);
                } else {
                    a(parameters, i3 / 1000);
                }
                this.n.setParameters(parameters);
                d();
                i4 = 0;
            } catch (RuntimeException e2) {
                i4 = -2138308594;
            }
        }
        return i4;
    }

    public int setStillImageSize(int i2, int i3) {
        int i4 = 0;
        synchronized (c.a) {
            try {
                Camera.Parameters parameters = this.n.getParameters();
                Camera.Size pictureSize = parameters.getPictureSize();
                if (pictureSize.width != i2 || pictureSize.height != i3) {
                    parameters.setPictureSize(i2, i3);
                    this.n.setParameters(parameters);
                }
            } catch (RuntimeException e2) {
                i4 = -2138308594;
            }
        }
        return i4;
    }

    public int setStillImageFormat(int i2) {
        int i3 = 0;
        synchronized (c.a) {
            try {
                int a2 = Utility.a(false, i2);
                if (a2 == -1) {
                    i3 = -2138308603;
                } else {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (parameters.getPictureFormat() != a2) {
                        parameters.setPictureFormat(a2);
                        this.n.setParameters(parameters);
                    }
                }
            } catch (RuntimeException e2) {
                i3 = -2138308594;
            }
        }
        return i3;
    }

    public int start() {
        int i2;
        synchronized (c.a) {
            try {
                if (j.b && Build.VERSION.SDK_INT < 11) {
                    final Object obj = new Object();
                    AnonymousClass1 r0 = new Runnable() {
                        private int c = 0;

                        public void run() {
                            if (CameraDevice10.this.w == null && CameraDevice10.this.p == null) {
                                GLSurfaceView gLSurfaceView = (GLSurfaceView) j.a(((Activity) CameraDevice10.this.m).getWindow().getDecorView(), GLSurfaceView.class);
                                if (gLSurfaceView == null) {
                                    int i = this.c;
                                    this.c = i + 1;
                                    if (i < 20) {
                                        CameraDevice10.I.postDelayed(this, 100);
                                        return;
                                    }
                                    return;
                                }
                                ViewGroup viewGroup = (ViewGroup) gLSurfaceView.getParent();
                                SurfaceView unused = CameraDevice10.this.w = new SurfaceView(CameraDevice10.this.m);
                                CameraDevice10.this.w.setBackgroundColor(0);
                                if (Build.VERSION.SDK_INT < 11) {
                                    CameraDevice10.b(CameraDevice10.this.w.getHolder());
                                }
                                viewGroup.addView(CameraDevice10.this.w, 480, 320);
                                int unused2 = CameraDevice10.this.a(CameraDevice10.this.w);
                                gLSurfaceView.setZOrderMediaOverlay(true);
                                d.a("add dummy view");
                            }
                            synchronized (obj) {
                                obj.notifyAll();
                            }
                        }
                    };
                    if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                        r0.run();
                    } else {
                        I.post(r0);
                        synchronized (obj) {
                            try {
                                obj.wait(2000);
                            } catch (InterruptedException e2) {
                            }
                        }
                    }
                }
                this.x = true;
                d();
                i2 = 0;
            } catch (RuntimeException e3) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    public int stop() {
        int i2 = 0;
        synchronized (c.a) {
            try {
                this.x = false;
                e();
                if (j.b && Build.VERSION.SDK_INT < 11) {
                    AnonymousClass4 r2 = new Runnable() {
                        public void run() {
                            if (CameraDevice10.this.w != null) {
                                int unused = CameraDevice10.this.a((SurfaceView) null);
                                ((ViewGroup) CameraDevice10.this.w.getParent()).removeView(CameraDevice10.this.w);
                                SurfaceView unused2 = CameraDevice10.this.w = null;
                                d.a("remove dummy view");
                            }
                        }
                    };
                    if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                        r2.run();
                    } else {
                        I.post(r2);
                    }
                }
            } catch (RuntimeException e2) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    public int captureStillImage() {
        synchronized (c.a) {
            try {
                if (this.B) {
                    return -2138308594;
                }
                this.B = true;
                e();
                if (this.k && Build.VERSION.SDK_INT >= 14) {
                    Camera.Parameters parameters = this.n.getParameters();
                    f.a(parameters, false);
                    this.n.setParameters(parameters);
                }
                if (this.j && Build.VERSION.SDK_INT >= 16) {
                    b();
                }
                f();
                this.n.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                    public void onPreviewFrame(byte[] bArr, Camera camera) {
                        if (!CameraDevice10.this.A) {
                            if (CameraDevice10.this.j) {
                                CameraDevice10.I.postDelayed(new Runnable() {
                                    public void run() {
                                        if (!CameraDevice10.this.A) {
                                            if (CameraDevice10.this.F) {
                                                boolean unused = CameraDevice10.this.G = true;
                                            } else {
                                                CameraDevice10.this.n.takePicture(CameraDevice10.this.L, (Camera.PictureCallback) null, (Camera.PictureCallback) null, CameraDevice10.this.M);
                                            }
                                        }
                                    }
                                }, 300);
                            } else {
                                CameraDevice10.this.n.takePicture(CameraDevice10.this.L, (Camera.PictureCallback) null, (Camera.PictureCallback) null, CameraDevice10.this.M);
                            }
                        }
                    }
                });
                this.n.startPreview();
                return 0;
            } catch (RuntimeException e2) {
                return -2138308594;
            }
        }
    }

    public int runAutoFocus() {
        int i2 = -2138308594;
        synchronized (c.a) {
            try {
                if (!this.C) {
                    this.C = true;
                    this.n.autoFocus(this.N);
                    i2 = 0;
                }
            } catch (RuntimeException e2) {
            }
        }
        return i2;
    }

    public int runAutoExposure() {
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT < 14) {
                    d.b("unsupported API on this device");
                    return -2138308603;
                } else if (this.D) {
                    return -2138308594;
                } else {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (!f.f(parameters)) {
                        return -2138308603;
                    }
                    this.D = true;
                    f.c(parameters, false);
                    this.n.setParameters(parameters);
                    I.sendMessageDelayed(I.obtainMessage(1, this), 3000);
                    return 0;
                }
            } catch (RuntimeException e2) {
                return -2138308594;
            }
        }
    }

    public int runAutoWhiteBalance() {
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT < 14) {
                    d.b("unsupported API on this device");
                    return -2138308603;
                } else if (this.E) {
                    return -2138308594;
                } else {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (!f.e(parameters)) {
                        return -2138308603;
                    }
                    this.E = true;
                    f.b(parameters, false);
                    this.n.setParameters(parameters);
                    I.sendMessageDelayed(I.obtainMessage(2, this), 3000);
                    return 0;
                }
            } catch (RuntimeException e2) {
                return -2138308594;
            }
        }
    }

    public int setFocusMode(int i2) {
        String str;
        int i3 = 0;
        synchronized (c.a) {
            try {
                if (this.C) {
                    this.C = false;
                    this.n.cancelAutoFocus();
                }
                switch (i2) {
                    case 0:
                        str = "auto";
                        break;
                    case 1:
                        str = "continuous-picture";
                        break;
                    case 2:
                        str = "continuous-video";
                        break;
                    case 3:
                        str = "edof";
                        break;
                    case 4:
                        str = "fixed";
                        break;
                    case 5:
                        str = "infinity";
                        break;
                    case 6:
                        str = "macro";
                        break;
                    default:
                        d.b("unexpected value: " + i2);
                        i3 = -2138308603;
                        break;
                }
                Camera.Parameters parameters = this.n.getParameters();
                parameters.setFocusMode(str);
                this.n.setParameters(parameters);
            } catch (RuntimeException e2) {
                i3 = -2138308594;
            }
        }
        return i3;
    }

    public int setFocusAreas(float[] fArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT < 14) {
                    d.b("unsupported API on this device");
                    i3 = -2138308603;
                } else {
                    List<Object> a2 = a(fArr, i2);
                    Camera.Parameters parameters = this.n.getParameters();
                    f.a(parameters, a2);
                    this.n.setParameters(parameters);
                    i3 = 0;
                }
            } catch (RuntimeException e2) {
                i3 = -2138308594;
            }
        }
        return i3;
    }

    public int setExposureMode(int i2) {
        boolean z2;
        synchronized (c.a) {
            switch (i2) {
                case 0:
                    if (Build.VERSION.SDK_INT >= 14) {
                        z2 = true;
                        break;
                    } else {
                        d.b("unsupported mode: " + i2);
                        return -2138308603;
                    }
                case 1:
                    z2 = false;
                    break;
                default:
                    try {
                        d.b("unexpected value: " + i2);
                        return -2138308603;
                    } catch (RuntimeException e2) {
                        return -2138308594;
                    }
            }
            Camera.Parameters parameters = this.n.getParameters();
            if (Build.VERSION.SDK_INT >= 14) {
                I.removeMessages(1);
                this.D = false;
                f.c(parameters, z2);
            }
            this.n.setParameters(parameters);
            return 0;
        }
    }

    public int setExposureAreas(float[] fArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT < 14) {
                    d.b("unsupported API on this device");
                    i3 = -2138308603;
                } else {
                    List<Object> a2 = a(fArr, i2);
                    Camera.Parameters parameters = this.n.getParameters();
                    f.b(parameters, a2);
                    this.n.setParameters(parameters);
                    i3 = 0;
                }
            } catch (RuntimeException e2) {
                i3 = -2138308594;
            }
        }
        return i3;
    }

    public int setFlashMode(int i2) {
        int i3;
        String str;
        synchronized (c.a) {
            switch (i2) {
                case 0:
                    str = "auto";
                    break;
                case 1:
                    str = "off";
                    break;
                case 2:
                    str = "on";
                    break;
                case 3:
                    str = "red-eye";
                    break;
                case 4:
                    str = "torch";
                    break;
                default:
                    try {
                        d.b("unexpected value: " + i2);
                        i3 = -2138308603;
                        break;
                    } catch (RuntimeException e2) {
                        i3 = -2138308594;
                        break;
                    }
            }
            Camera.Parameters parameters = this.n.getParameters();
            parameters.setFlashMode(str);
            this.n.setParameters(parameters);
            i3 = 0;
        }
        return i3;
    }

    public int setWhiteBalanceMode(int i2) {
        String str;
        boolean z2;
        synchronized (c.a) {
            switch (i2) {
                case 0:
                    str = "auto";
                    z2 = false;
                    break;
                case 1:
                    str = "cloudy-daylight";
                    z2 = false;
                    break;
                case 2:
                    str = "daylight";
                    z2 = false;
                    break;
                case 3:
                    str = "fluorescent";
                    z2 = false;
                    break;
                case 4:
                    str = "incandescent";
                    z2 = false;
                    break;
                case 5:
                    str = "shade";
                    z2 = false;
                    break;
                case 6:
                    str = "twilight";
                    z2 = false;
                    break;
                case 7:
                    str = "warm-fluorescent";
                    z2 = false;
                    break;
                case 8:
                    if (Build.VERSION.SDK_INT >= 14) {
                        str = null;
                        z2 = true;
                        break;
                    } else {
                        d.b("unsupported mode: " + i2);
                        return -2138308603;
                    }
                default:
                    try {
                        d.b("unexpected value: " + i2);
                        return -2138308603;
                    } catch (RuntimeException e2) {
                        return -2138308594;
                    }
            }
            Camera.Parameters parameters = this.n.getParameters();
            if (str != null) {
                parameters.setWhiteBalance(str);
            }
            if (Build.VERSION.SDK_INT >= 14) {
                I.removeMessages(2);
                this.E = false;
                f.b(parameters, z2);
            }
            this.n.setParameters(parameters);
            return 0;
        }
    }

    public int setSceneMode(int i2) {
        int i3;
        String str;
        synchronized (c.a) {
            switch (i2) {
                case 0:
                    str = "action";
                    break;
                case 1:
                    str = "auto";
                    break;
                case 2:
                    str = "barcode";
                    break;
                case 3:
                    str = "beach";
                    break;
                case 4:
                    str = "candlelight";
                    break;
                case 5:
                    str = "fireworks";
                    break;
                case 6:
                    str = "landscape";
                    break;
                case 7:
                    str = "night";
                    break;
                case 8:
                    str = "night-portrait";
                    break;
                case 9:
                    str = "party";
                    break;
                case 10:
                    str = "portrait";
                    break;
                case 11:
                    str = "snow";
                    break;
                case 12:
                    str = "sports";
                    break;
                case 13:
                    str = "steadyphoto";
                    break;
                case 14:
                    str = "sunset";
                    break;
                case 15:
                    str = "theatre";
                    break;
                default:
                    try {
                        d.b("unexpected value: " + i2);
                        i3 = -2138308603;
                        break;
                    } catch (RuntimeException e2) {
                        i3 = -2138308594;
                        break;
                    }
            }
            Camera.Parameters parameters = this.n.getParameters();
            parameters.setSceneMode(str);
            this.n.setParameters(parameters);
            i3 = 0;
        }
        return i3;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSupportedVideoImageSize(int[] r9, int r10) {
        /*
            r8 = this;
            r1 = 0
            java.lang.Object r4 = com.sony.smartar.c.a
            monitor-enter(r4)
            android.hardware.Camera r0 = r8.n     // Catch:{ RuntimeException -> 0x0040 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ RuntimeException -> 0x0040 }
            java.util.List r2 = r0.getSupportedPreviewSizes()     // Catch:{ RuntimeException -> 0x0040 }
            boolean r0 = com.sony.smartar.j.d     // Catch:{ RuntimeException -> 0x0040 }
            if (r0 == 0) goto L_0x0047
            if (r2 == 0) goto L_0x0047
            int r5 = r2.size()     // Catch:{ RuntimeException -> 0x0040 }
            r3 = r1
        L_0x0019:
            if (r3 >= r5) goto L_0x0047
            java.lang.Object r0 = r2.get(r3)     // Catch:{ RuntimeException -> 0x0040 }
            android.hardware.Camera$Size r0 = (android.hardware.Camera.Size) r0     // Catch:{ RuntimeException -> 0x0040 }
            int r6 = r0.width     // Catch:{ RuntimeException -> 0x0040 }
            r7 = 320(0x140, float:4.48E-43)
            if (r6 != r7) goto L_0x003c
            int r0 = r0.height     // Catch:{ RuntimeException -> 0x0040 }
            r6 = 240(0xf0, float:3.36E-43)
            if (r0 != r6) goto L_0x003c
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x0040 }
            r0.<init>(r2)     // Catch:{ RuntimeException -> 0x0040 }
            r0.remove(r3)     // Catch:{ RuntimeException -> 0x0040 }
        L_0x0035:
            r2 = 0
            int r0 = com.sony.smartar.Utility.a((boolean) r2, (java.lang.Object) r0, (int[]) r9, (int) r10)     // Catch:{ RuntimeException -> 0x0040 }
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
        L_0x003b:
            return r0
        L_0x003c:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0019
        L_0x0040:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            r0 = r1
            goto L_0x003b
        L_0x0044:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0044 }
            throw r0
        L_0x0047:
            r0 = r2
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.getSupportedVideoImageSize(int[], int):int");
    }

    public int getSupportedVideoImageFormat(int[] iArr, int i2) {
        int i3 = 0;
        synchronized (c.a) {
            try {
                i3 = Utility.a(false, this.n.getParameters().getSupportedPreviewFormats(), iArr, i2);
            } catch (RuntimeException e2) {
            }
        }
        return i3;
    }

    public int getSupportedVideoImageFpsRange(int[] iArr, int i2) {
        synchronized (c.a) {
            try {
                Camera.Parameters parameters = this.n.getParameters();
                if (Build.VERSION.SDK_INT >= 9) {
                    List<int[]> a2 = f.a(parameters);
                    if (a2 == null) {
                        return 0;
                    }
                    int min = Math.min(a2.size(), i2);
                    for (int i3 = 0; i3 < min; i3++) {
                        int[] iArr2 = a2.get(i3);
                        iArr[(i3 * 2) + 0] = iArr2[0];
                        iArr[(i3 * 2) + 1] = iArr2[1];
                    }
                    return min;
                }
                List<Integer> b2 = b(parameters);
                if (b2 == null) {
                    return 0;
                }
                int min2 = Math.min(b2.size(), i2);
                for (int i4 = 0; i4 < min2; i4++) {
                    int intValue = b2.get(i4).intValue();
                    iArr[(i4 * 2) + 0] = 1000;
                    iArr[(i4 * 2) + 1] = intValue * 1000;
                }
                return min2;
            } catch (RuntimeException e2) {
                return 0;
            }
        }
    }

    public int getSupportedStillImageSize(int[] iArr, int i2) {
        int i3 = 0;
        synchronized (c.a) {
            try {
                i3 = Utility.a(false, (Object) this.n.getParameters().getSupportedPictureSizes(), iArr, i2);
            } catch (RuntimeException e2) {
            }
        }
        return i3;
    }

    public int getSupportedStillImageFormat(int[] iArr, int i2) {
        int i3 = 0;
        synchronized (c.a) {
            try {
                i3 = Utility.a(false, this.n.getParameters().getSupportedPictureFormats(), iArr, i2);
            } catch (RuntimeException e2) {
            }
        }
        return i3;
    }

    public int getSupportedFocusMode(int[] iArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                List<String> supportedFocusModes = this.n.getParameters().getSupportedFocusModes();
                if (supportedFocusModes == null) {
                    return 0;
                }
                int size = supportedFocusModes.size();
                int i4 = 0;
                int i5 = 0;
                while (i4 < size && i5 < i2) {
                    String str = supportedFocusModes.get(i4);
                    if ("auto".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 0;
                    } else if ("continuous-picture".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 1;
                    } else if ("continuous-video".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 2;
                    } else if ("edof".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 3;
                    } else if ("fixed".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 4;
                    } else if ("infinity".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 5;
                    } else if ("macro".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 6;
                    } else {
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                }
                return i5;
            } catch (RuntimeException e2) {
                return 0;
            }
        }
    }

    @TargetApi(14)
    public int getMaxNumFocusAreas() {
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT >= 14) {
                    Camera.Parameters parameters = this.n.getParameters();
                    List<Camera.Area> focusAreas = parameters.getFocusAreas();
                    i2 = Math.min(focusAreas == null ? 0 : focusAreas.size(), f.b(parameters));
                }
            } catch (RuntimeException e2) {
            }
        }
        return i2;
    }

    public int getSupportedFlashMode(int[] iArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                List<String> supportedFlashModes = this.n.getParameters().getSupportedFlashModes();
                if (supportedFlashModes == null) {
                    return 0;
                }
                int size = supportedFlashModes.size();
                int i4 = 0;
                int i5 = 0;
                while (i4 < size && i5 < i2) {
                    String str = supportedFlashModes.get(i4);
                    if ("auto".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 0;
                    } else if ("off".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 1;
                    } else if ("on".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 2;
                    } else if ("red-eye".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 3;
                    } else if ("torch".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 4;
                    } else {
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                }
                return i5;
            } catch (RuntimeException e2) {
                return 0;
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSupportedExposureMode(int[] r7, int r8) {
        /*
            r6 = this;
            r1 = 1
            r2 = 0
            java.lang.Object r3 = com.sony.smartar.c.a
            monitor-enter(r3)
            android.hardware.Camera r0 = r6.n     // Catch:{ RuntimeException -> 0x0026 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ RuntimeException -> 0x0026 }
            r4 = 0
            if (r2 >= r8) goto L_0x002f
            r5 = 1
            r7[r4] = r5     // Catch:{ RuntimeException -> 0x0026 }
        L_0x0011:
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch:{ RuntimeException -> 0x0026 }
            r5 = 14
            if (r4 < r5) goto L_0x002d
            if (r1 >= r8) goto L_0x002d
            boolean r0 = com.sony.smartar.f.g(r0)     // Catch:{ RuntimeException -> 0x0026 }
            if (r0 == 0) goto L_0x002d
            int r0 = r1 + 1
            r4 = 0
            r7[r1] = r4     // Catch:{ RuntimeException -> 0x0026 }
        L_0x0024:
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
        L_0x0025:
            return r0
        L_0x0026:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            r0 = r2
            goto L_0x0025
        L_0x002a:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            throw r0
        L_0x002d:
            r0 = r1
            goto L_0x0024
        L_0x002f:
            r1 = r2
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.getSupportedExposureMode(int[], int):int");
    }

    @TargetApi(14)
    public int getMaxNumExposureAreas() {
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT >= 14) {
                    Camera.Parameters parameters = this.n.getParameters();
                    List<Camera.Area> meteringAreas = parameters.getMeteringAreas();
                    i2 = Math.min(meteringAreas == null ? 0 : meteringAreas.size(), f.d(parameters));
                }
            } catch (RuntimeException e2) {
            }
        }
        return i2;
    }

    public int getSupportedWhiteBalanceMode(int[] iArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                Camera.Parameters parameters = this.n.getParameters();
                List<String> supportedWhiteBalance = parameters.getSupportedWhiteBalance();
                if (supportedWhiteBalance == null) {
                    return 0;
                }
                int size = supportedWhiteBalance.size();
                int i4 = 0;
                int i5 = 0;
                while (i4 < size && i5 < i2) {
                    String str = supportedWhiteBalance.get(i4);
                    if ("auto".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 0;
                    } else if ("cloudy-daylight".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 1;
                    } else if ("daylight".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 2;
                    } else if ("fluorescent".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 3;
                    } else if ("incandescent".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 4;
                    } else if ("shade".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 5;
                    } else if ("twilight".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 6;
                    } else if ("warm-fluorescent".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 7;
                    } else {
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                }
                if (Build.VERSION.SDK_INT >= 14 && i5 < i2 && f.h(parameters)) {
                    int i6 = i5 + 1;
                    iArr[i5] = 8;
                    i5 = i6;
                }
                return i5;
            } catch (RuntimeException e2) {
                return 0;
            }
        }
    }

    public int getSupportedSceneMode(int[] iArr, int i2) {
        int i3;
        synchronized (c.a) {
            try {
                List<String> supportedSceneModes = this.n.getParameters().getSupportedSceneModes();
                if (supportedSceneModes == null) {
                    return 0;
                }
                if (j.c) {
                    return 0;
                }
                int size = supportedSceneModes.size();
                int i4 = 0;
                int i5 = 0;
                while (i4 < size && i5 < i2) {
                    String str = supportedSceneModes.get(i4);
                    if ("action".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 0;
                    } else if ("auto".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 1;
                    } else if ("barcode".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 2;
                    } else if ("beach".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 3;
                    } else if ("candlelight".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 4;
                    } else if ("fireworks".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 5;
                    } else if ("landscape".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 6;
                    } else if ("night".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 7;
                    } else if ("night-portrait".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 8;
                    } else if ("party".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 9;
                    } else if ("portrait".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 10;
                    } else if ("snow".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 11;
                    } else if ("sports".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 12;
                    } else if ("steadyphoto".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 13;
                    } else if ("sunset".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 14;
                    } else if ("theatre".equals(str)) {
                        i3 = i5 + 1;
                        iArr[i5] = 15;
                    } else {
                        i3 = i5;
                    }
                    i4++;
                    i5 = i3;
                }
                return i5;
            } catch (RuntimeException e2) {
                return 0;
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float[] getDeviceInfo() {
        /*
            r6 = this;
            java.lang.Object r2 = com.sony.smartar.c.a
            monitor-enter(r2)
            android.hardware.Camera r0 = r6.n     // Catch:{ RuntimeException -> 0x0099 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ RuntimeException -> 0x0099 }
            android.hardware.Camera$Size r3 = r0.getPreviewSize()     // Catch:{ RuntimeException -> 0x0099 }
            android.hardware.Camera$Size r0 = r0.getPictureSize()     // Catch:{ RuntimeException -> 0x0099 }
            r1 = 15
            float[] r1 = new float[r1]     // Catch:{ RuntimeException -> 0x0099 }
            r4 = 0
            int r5 = r6.o     // Catch:{ RuntimeException -> 0x0099 }
            float r5 = (float) r5     // Catch:{ RuntimeException -> 0x0099 }
            r1[r4] = r5     // Catch:{ RuntimeException -> 0x0099 }
            r4 = 1
            int r5 = r3.width     // Catch:{ RuntimeException -> 0x0099 }
            float r5 = (float) r5     // Catch:{ RuntimeException -> 0x0099 }
            r1[r4] = r5     // Catch:{ RuntimeException -> 0x0099 }
            r4 = 2
            int r3 = r3.height     // Catch:{ RuntimeException -> 0x0099 }
            float r3 = (float) r3     // Catch:{ RuntimeException -> 0x0099 }
            r1[r4] = r3     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 3
            int r4 = r0.width     // Catch:{ RuntimeException -> 0x0099 }
            float r4 = (float) r4     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 4
            int r0 = r0.height     // Catch:{ RuntimeException -> 0x0099 }
            float r0 = (float) r0     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r0     // Catch:{ RuntimeException -> 0x0099 }
            r0 = 0
            int r0 = getDefaultCameraId(r0)     // Catch:{ RuntimeException -> 0x0099 }
            android.util.SparseArray r3 = sCameraModelInfos     // Catch:{ RuntimeException -> 0x0099 }
            java.lang.Object r0 = r3.get(r0)     // Catch:{ RuntimeException -> 0x0099 }
            com.sony.smartar.CameraDeviceInterface$a r0 = (com.sony.smartar.CameraDeviceInterface.a) r0     // Catch:{ RuntimeException -> 0x0099 }
            if (r0 == 0) goto L_0x0057
            r3 = 5
            r4 = 1065353216(0x3f800000, float:1.0)
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 6
            float r4 = r0.a     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 7
            float r4 = r0.b     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 8
            float r0 = r0.c     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r0     // Catch:{ RuntimeException -> 0x0099 }
        L_0x0057:
            r0 = 1
            int r0 = getDefaultCameraId(r0)     // Catch:{ RuntimeException -> 0x0099 }
            android.util.SparseArray r3 = sCameraModelInfos     // Catch:{ RuntimeException -> 0x0099 }
            java.lang.Object r0 = r3.get(r0)     // Catch:{ RuntimeException -> 0x0099 }
            com.sony.smartar.CameraDeviceInterface$a r0 = (com.sony.smartar.CameraDeviceInterface.a) r0     // Catch:{ RuntimeException -> 0x0099 }
            if (r0 == 0) goto L_0x007e
            r3 = 9
            r4 = 1065353216(0x3f800000, float:1.0)
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 10
            float r4 = r0.a     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 11
            float r4 = r0.b     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 12
            float r0 = r0.c     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r0     // Catch:{ RuntimeException -> 0x0099 }
        L_0x007e:
            android.graphics.Point r0 = new android.graphics.Point     // Catch:{ RuntimeException -> 0x0099 }
            r0.<init>()     // Catch:{ RuntimeException -> 0x0099 }
            android.content.Context r3 = r6.m     // Catch:{ RuntimeException -> 0x0099 }
            com.sony.smartar.Utility.a((android.content.Context) r3, (android.graphics.Point) r0)     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 13
            int r4 = r0.x     // Catch:{ RuntimeException -> 0x0099 }
            float r4 = (float) r4     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r4     // Catch:{ RuntimeException -> 0x0099 }
            r3 = 14
            int r0 = r0.y     // Catch:{ RuntimeException -> 0x0099 }
            float r0 = (float) r0     // Catch:{ RuntimeException -> 0x0099 }
            r1[r3] = r0     // Catch:{ RuntimeException -> 0x0099 }
            monitor-exit(r2)     // Catch:{ all -> 0x009d }
            r0 = r1
        L_0x0098:
            return r0
        L_0x0099:
            r0 = move-exception
            r0 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x009d }
            goto L_0x0098
        L_0x009d:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x009d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.getDeviceInfo():float[]");
    }

    public int getFacing() {
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (Build.VERSION.SDK_INT >= 9) {
                    Object b2 = f.b();
                    f.a(this.o, b2);
                    switch (f.a(b2)) {
                        case 0:
                            break;
                        case 1:
                            i2 = 1;
                            break;
                        default:
                            i2 = -2138308603;
                            break;
                    }
                }
            } catch (RuntimeException e2) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return r0;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getRotation() {
        /*
            r5 = this;
            r1 = 90
            r0 = 0
            java.lang.Object r2 = com.sony.smartar.c.a
            monitor-enter(r2)
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ RuntimeException -> 0x0049 }
            r4 = 9
            if (r3 < r4) goto L_0x002e
            java.lang.Object r3 = com.sony.smartar.f.b()     // Catch:{ RuntimeException -> 0x0049 }
            int r4 = r5.o     // Catch:{ RuntimeException -> 0x0049 }
            com.sony.smartar.f.a((int) r4, (java.lang.Object) r3)     // Catch:{ RuntimeException -> 0x0049 }
            int r3 = com.sony.smartar.f.b((java.lang.Object) r3)     // Catch:{ RuntimeException -> 0x0049 }
            switch(r3) {
                case 0: goto L_0x001e;
                case 90: goto L_0x0023;
                case 180: goto L_0x0026;
                case 270: goto L_0x002a;
                default: goto L_0x001c;
            }
        L_0x001c:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
        L_0x001d:
            return r0
        L_0x001e:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x0020:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            throw r0
        L_0x0023:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            r0 = r1
            goto L_0x001d
        L_0x0026:
            r0 = 180(0xb4, float:2.52E-43)
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x002a:
            r0 = 270(0x10e, float:3.78E-43)
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x002e:
            android.content.Context r3 = r5.m     // Catch:{ RuntimeException -> 0x0049 }
            int r3 = com.sony.smartar.d.b((android.content.Context) r3)     // Catch:{ RuntimeException -> 0x0049 }
            android.content.Context r4 = r5.m     // Catch:{ RuntimeException -> 0x0049 }
            boolean r4 = com.sony.smartar.d.a((android.content.Context) r4)     // Catch:{ RuntimeException -> 0x0049 }
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0044;
                case 2: goto L_0x003f;
                case 3: goto L_0x0044;
                default: goto L_0x003d;
            }
        L_0x003d:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x003f:
            if (r4 == 0) goto L_0x0042
            r0 = r1
        L_0x0042:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x0044:
            if (r4 != 0) goto L_0x0047
            r0 = r1
        L_0x0047:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        L_0x0049:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.getRotation():int");
    }

    public Camera getNativeDevice() {
        Camera camera;
        synchronized (c.a) {
            camera = this.n;
        }
        return camera;
    }

    public float getFovY() {
        float f2 = 0.0f;
        synchronized (c.a) {
            try {
                if (this.n != null) {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (parameters != null) {
                        f2 = parameters.getVerticalViewAngle();
                    }
                }
            } catch (RuntimeException e2) {
            }
        }
        return f2;
    }

    public int getStillImageSize(int[] iArr) {
        Camera.Parameters parameters;
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (iArr.length != 2) {
                    i2 = -2138308602;
                } else {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    if (this.n == null || (parameters = this.n.getParameters()) == null) {
                        i2 = -2138308608;
                    } else {
                        Camera.Size pictureSize = parameters.getPictureSize();
                        iArr[0] = pictureSize.width;
                        iArr[1] = pictureSize.height;
                    }
                }
            } catch (RuntimeException e2) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    public float getFocalLength() {
        float f2 = 0.0f;
        synchronized (c.a) {
            try {
                if (this.n != null) {
                    Camera.Parameters parameters = this.n.getParameters();
                    if (parameters != null) {
                        f2 = parameters.getFocalLength();
                    }
                }
            } catch (RuntimeException e2) {
                f2 = -2.13830861E9f;
            }
        }
        return f2;
    }

    public int getVideoImageSize(int[] iArr) {
        Camera.Parameters parameters;
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (iArr.length != 2) {
                    i2 = -2138308602;
                } else {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    if (this.n == null || (parameters = this.n.getParameters()) == null) {
                        i2 = -2138308608;
                    } else {
                        Camera.Size previewSize = parameters.getPreviewSize();
                        iArr[0] = previewSize.width;
                        iArr[1] = previewSize.height;
                    }
                }
            } catch (RuntimeException e2) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    public int getMaxStillImageSize(int[] iArr) {
        int i2 = 0;
        synchronized (c.a) {
            try {
                if (iArr.length != 2) {
                    i2 = -2138308602;
                } else {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    int[] iArr2 = new int[64];
                    int supportedStillImageSize = getSupportedStillImageSize(iArr2, 64);
                    for (int i3 = 0; i3 < supportedStillImageSize; i3 += 2) {
                        if (iArr2[i3] >= iArr[0] && iArr2[i3 + 1] >= iArr[1]) {
                            iArr[0] = iArr2[i3];
                            iArr[1] = iArr2[i3 + 1];
                        }
                    }
                    if (iArr[0] == 0 || iArr[1] == 0) {
                        i2 = -2138308608;
                    }
                }
            } catch (RuntimeException e2) {
                i2 = -2138308594;
            }
        }
        return i2;
    }

    public int[] getCameraAPIFeature() {
        int[] iArr;
        int i2 = 2;
        synchronized (c.a) {
            iArr = new int[2];
            iArr[0] = 1;
            if (!H) {
                i2 = -1;
            }
            iArr[1] = i2;
        }
        return iArr;
    }

    public int getImageSensorRotation() {
        int i2;
        synchronized (c.a) {
            if (getFacing() == 1) {
                i2 = 270;
            } else {
                i2 = 90;
            }
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public int a(SurfaceView surfaceView) {
        String str;
        boolean z2 = true;
        if (this.p == surfaceView) {
            return 0;
        }
        e();
        if (this.p != null) {
            this.p.getHolder().removeCallback(this.J);
        }
        this.p = surfaceView;
        if (this.p != null) {
            this.p.getHolder().addCallback(this.J);
            if (Build.VERSION.SDK_INT >= 11) {
                this.z = this.p.getHolder().getSurface().isValid();
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= 9) {
                        str = "mNativeSurface";
                    } else {
                        str = "mSurface";
                    }
                    Field declaredField = Surface.class.getDeclaredField(str);
                    declaredField.setAccessible(true);
                    if (declaredField.getInt(this.p.getHolder().getSurface()) == 0) {
                        z2 = false;
                    }
                    this.z = z2;
                } catch (IllegalAccessException e2) {
                    return -2138308603;
                } catch (NoSuchFieldException e3) {
                    return -2138308603;
                }
            }
        } else {
            this.z = false;
        }
        d();
        return 0;
    }

    private static List<Object> a(float[] fArr, int i2) {
        Object[] objArr = new Object[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            Rect rect = new Rect((int) (fArr[(i3 * 5) + 0] * 1000.0f), (int) (fArr[(i3 * 5) + 1] * 1000.0f), (int) (fArr[(i3 * 5) + 2] * 1000.0f), (int) (fArr[(i3 * 5) + 3] * 1000.0f));
            int i4 = (int) (fArr[(i3 * 5) + 4] * 1000.0f);
            Object a2 = f.a(rect, i4);
            f.a(a2, rect);
            f.a(a2, i4);
            objArr[i3] = a2;
        }
        return Arrays.asList(objArr);
    }

    @TargetApi(16)
    private void b() {
        this.F = false;
        this.n.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
            public void onAutoFocusMoving(boolean z, Camera camera) {
                if (!CameraDevice10.this.A) {
                    boolean unused = CameraDevice10.this.F = z;
                    if (CameraDevice10.this.G && !z) {
                        boolean unused2 = CameraDevice10.this.G = false;
                        CameraDevice10.this.n.takePicture(CameraDevice10.this.L, (Camera.PictureCallback) null, (Camera.PictureCallback) null, CameraDevice10.this.M);
                    }
                }
            }
        });
    }

    private void c() {
        int a2;
        Camera.Parameters parameters = this.n.getParameters();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
        List<Integer> b2 = b(parameters);
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        List<String> supportedSceneModes = parameters.getSupportedSceneModes();
        List<String> supportedWhiteBalance = parameters.getSupportedWhiteBalance();
        if (!(supportedPreviewFormats == null || supportedPreviewFormats.indexOf(17) == -1)) {
            parameters.setPreviewFormat(17);
        }
        if (!(supportedWhiteBalance == null || supportedWhiteBalance.indexOf("auto") == -1)) {
            parameters.setWhiteBalance("auto");
        }
        if (!(supportedSceneModes == null || supportedSceneModes.indexOf("sports") == -1 || j.c)) {
            parameters.setSceneMode("sports");
        }
        if (supportedPreviewSizes != null) {
            Camera.Size size = (Camera.Size) Utility.a(false, (Object) supportedPreviewSizes, this.a, this.b);
            parameters.setPreviewSize(size.width, size.height);
        }
        if (!(b2 == null || (a2 = a(b2, 30)) == 0)) {
            a(parameters, a2);
        }
        if (supportedFocusModes.indexOf("continuous-video") != -1) {
            parameters.setFocusMode("continuous-video");
        } else if (supportedFocusModes.indexOf("infinity") != -1) {
            parameters.setFocusMode("infinity");
        }
        if (this.k && Build.VERSION.SDK_INT >= 14) {
            f.a(parameters, true);
        }
        if (!(this.c == null || this.d == null)) {
            parameters.set(this.c, this.d);
        }
        if (!(this.e == null || this.f == null)) {
            parameters.set(this.e, this.f);
            if (Build.VERSION.SDK_INT >= 14 && f.b(parameters) > 0) {
                List c2 = f.c(parameters);
                if (c2 == null) {
                    Object a3 = f.a(new Rect(), this.g);
                    c2 = new ArrayList();
                    c2.add(a3);
                } else {
                    Object obj = c2.get(0);
                    f.a(obj, new Rect());
                    f.a(obj, this.g);
                }
                f.a(parameters, (List<Object>) c2);
            }
        }
        this.n.setParameters(parameters);
    }

    /* access modifiers changed from: private */
    public void d() {
        boolean z2;
        List<Integer> b2;
        if (!this.y && this.x) {
            if (this.p == null || this.z) {
                this.y = true;
                Camera.Parameters parameters = this.n.getParameters();
                f();
                Camera.Size previewSize = parameters.getPreviewSize();
                this.u = previewSize.width;
                this.v = previewSize.height;
                int previewFormat = parameters.getPreviewFormat();
                int[] iArr = new int[1];
                Utility.a(false, (List<Integer>) Collections.singletonList(Integer.valueOf(previewFormat)), iArr, 1);
                this.t = iArr[0];
                int a2 = g.a(previewSize.width, previewSize.height, previewFormat) + 1 + 4095;
                int i2 = a2 - (a2 & 4095);
                if (this.s.size() != 2) {
                    z2 = true;
                } else if (this.s.get(0).length == i2) {
                    for (int i3 = 0; i3 < 2; i3++) {
                        this.n.addCallbackBuffer(this.s.get(i3));
                    }
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2) {
                    this.s.clear();
                    for (int i4 = 0; i4 < 2; i4++) {
                        this.s.add(i4, new byte[i2]);
                        this.n.addCallbackBuffer(this.s.get(i4));
                    }
                }
                this.n.setPreviewCallbackWithBuffer(this.K);
                this.n.setErrorCallback(this.O);
                this.n.startPreview();
                if ((!Build.MANUFACTURER.equals("FUJITSU") || !this.l) && (b2 = b(parameters)) != null) {
                    int a3 = a(parameters);
                    int size = b2.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        int intValue = b2.get(i5).intValue();
                        if (intValue != a3) {
                            a(parameters, intValue);
                            this.n.setParameters(parameters);
                            a(parameters, a3);
                            this.n.setParameters(parameters);
                            return;
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.y) {
            this.y = false;
            this.n.stopPreview();
            this.n.setPreviewCallbackWithBuffer((Camera.PreviewCallback) null);
            this.n.setErrorCallback((Camera.ErrorCallback) null);
            g();
        }
    }

    private void f() {
        if (this.p != null) {
            try {
                this.n.setPreviewDisplay(this.p.getHolder());
            } catch (IOException e2) {
            }
        } else if (Build.VERSION.SDK_INT >= 11) {
            this.r = h();
            this.q = f.b(this.r);
            f.a(this.n, (Object) this.q);
        }
    }

    private static int a(List<Integer> list, int i2) {
        int i3;
        int size = list.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < size) {
            int intValue = list.get(i4).intValue();
            int abs = Math.abs(intValue - i2);
            if (i6 == 0 || abs < i5) {
                i3 = intValue;
            } else {
                abs = i5;
                i3 = i6;
            }
            i4++;
            i6 = i3;
            i5 = abs;
        }
        return i6;
    }

    private void g() {
        try {
            this.n.setPreviewDisplay((SurfaceHolder) null);
            if (Build.VERSION.SDK_INT >= 11 && this.q != null) {
                if (Build.VERSION.SDK_INT >= 14) {
                    f.c((Object) this.q);
                }
                this.q = null;
                a(this.r);
                this.r = -1;
            }
        } catch (IOException e2) {
        }
    }

    private static Object a(Class<?> cls, String str) {
        try {
            return i.b(cls, str);
        } catch (h e2) {
            return null;
        }
    }

    private static void a(int i2, Camera camera) {
        if (sCameraModelInfos.get(i2) == null) {
            boolean z2 = camera == null;
            if (z2) {
                if (Build.VERSION.SDK_INT >= 9) {
                    camera = f.a(i2);
                } else {
                    camera = Camera.open();
                }
                if (camera == null) {
                    return;
                }
            }
            Camera.Parameters parameters = camera.getParameters();
            CameraDeviceInterface.a aVar = new CameraDeviceInterface.a();
            aVar.a = parameters.getFocalLength();
            aVar.b = parameters.getHorizontalViewAngle();
            aVar.c = parameters.getVerticalViewAngle();
            sCameraModelInfos.put(i2, aVar);
            if (z2) {
                camera.release();
            }
        }
    }

    private static void a(Camera.Parameters parameters, int i2) {
        parameters.setPreviewFrameRate(i2);
    }

    private static int a(Camera.Parameters parameters) {
        return parameters.getPreviewFrameRate();
    }

    private static List<Integer> b(Camera.Parameters parameters) {
        return parameters.getSupportedPreviewFrameRates();
    }

    /* access modifiers changed from: private */
    public static void b(SurfaceHolder surfaceHolder) {
        surfaceHolder.setType(3);
    }

    @SuppressLint({"all"})
    private static int h() {
        int[] iArr = {0};
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, 10241, 9728.0f);
        GLES20.glTexParameterf(36197, 10240, 9728.0f);
        GLES20.glTexParameterf(36197, 10242, 33071.0f);
        GLES20.glTexParameterf(36197, 10243, 33071.0f);
        GLES20.glBindTexture(36197, 0);
        return iArr[0];
    }

    private static void a(int i2) {
        GLES20.glDeleteTextures(1, new int[]{i2}, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.hardware.Camera b(int r7) {
        /*
            r1 = 0
            r6 = 1
            boolean r0 = i()
            if (r0 == 0) goto L_0x0062
            java.lang.Class<android.hardware.Camera> r0 = android.hardware.Camera.class
            java.lang.String r2 = "openLegacy"
            r3 = 2
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r4 = 0
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r3[r4] = r5     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r4 = 1
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r3[r4] = r5     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            java.lang.reflect.Method r0 = r0.getMethod(r2, r3)     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r2 = 0
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r4 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r3[r4] = r5     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r4 = 1
            int r5 = j()     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            r3[r4] = r5     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            java.lang.Object r0 = r0.invoke(r2, r3)     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
            android.hardware.Camera r0 = (android.hardware.Camera) r0     // Catch:{ NoSuchMethodException -> 0x0046, IllegalAccessException -> 0x004c, IllegalArgumentException -> 0x0052, InvocationTargetException -> 0x0058, Exception -> 0x005e }
        L_0x0039:
            if (r0 != 0) goto L_0x0069
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 9
            if (r0 < r1) goto L_0x0064
            android.hardware.Camera r0 = com.sony.smartar.f.a((int) r7)
        L_0x0045:
            return r0
        L_0x0046:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto L_0x0039
        L_0x004c:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto L_0x0039
        L_0x0052:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto L_0x0039
        L_0x0058:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r1
            goto L_0x0039
        L_0x005e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0062:
            r0 = r1
            goto L_0x0039
        L_0x0064:
            android.hardware.Camera r0 = android.hardware.Camera.open()
            goto L_0x0045
        L_0x0069:
            H = r6
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice10.b(int):android.hardware.Camera");
    }

    private static boolean i() {
        return a.b;
    }

    private static int j() {
        return a.a;
    }

    private static class a {
        /* access modifiers changed from: private */
        public static final int a = d();
        /* access modifiers changed from: private */
        public static final boolean b = c();

        private static boolean c() {
            try {
                for (Method name : Camera.class.getMethods()) {
                    if (name.getName().equals("openLegacy") && d() != -1) {
                        return true;
                    }
                }
                return false;
            } catch (RuntimeException e) {
                return false;
            }
        }

        private static int d() {
            int i = -1;
            try {
                Field[] fields = Camera.class.getFields();
                int length = fields.length;
                int i2 = 0;
                while (i2 < length) {
                    Field field = fields[i2];
                    if (field.getName().equals("CAMERA_HAL_API_VERSION_1_0")) {
                        try {
                            i = field.getInt((Object) null);
                            break;
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                        }
                    } else {
                        i2++;
                    }
                }
            } catch (RuntimeException e2) {
            }
            return i;
        }
    }
}

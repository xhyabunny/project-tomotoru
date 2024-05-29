package com.sony.smartar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaActionSound;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Size;
import android.util.SizeF;
import android.view.SurfaceView;
import com.sony.smartar.CameraDeviceInterface;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TargetApi(21)
public final class CameraDevice20 implements CameraDeviceInterface {
    private static final Size h = new Size(640, 480);
    private Handler A = null;
    /* access modifiers changed from: private */
    public Handler B = null;
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
    /* access modifiers changed from: private */
    public boolean H = false;
    private boolean I = false;
    /* access modifiers changed from: private */
    public boolean J = false;
    /* access modifiers changed from: private */
    public int K = 0;
    /* access modifiers changed from: private */
    public b L = new b();
    /* access modifiers changed from: private */
    public d M = null;
    /* access modifiers changed from: private */
    public c N = null;
    /* access modifiers changed from: private */
    public final CameraCaptureSession.CaptureCallback a = new CameraCaptureSession.CaptureCallback() {
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            if (!CameraDevice20.this.D) {
                synchronized (b.b) {
                    if (b.a != 0) {
                        CameraDevice20.this.B.post(new Runnable() {
                            public void run() {
                                synchronized (b.b) {
                                    if (b.a != 0) {
                                        CameraDevice20.onShutter(b.a);
                                    }
                                }
                            }
                        });
                    }
                }
                CameraDevice20.this.p.play(0);
            }
        }
    };
    /* access modifiers changed from: private */
    public final CameraCaptureSession.CaptureCallback b = new CameraCaptureSession.CaptureCallback() {
        private void a() {
            try {
                CaptureRequest.Builder createCaptureRequest = CameraDevice20.this.i.createCaptureRequest(2);
                createCaptureRequest.addTarget(CameraDevice20.this.s.getSurface());
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, (Integer) CameraDevice20.this.o.get(CaptureRequest.CONTROL_AE_MODE));
                createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) CameraDevice20.this.o.get(CaptureRequest.CONTROL_AF_MODE));
                createCaptureRequest.set(CaptureRequest.CONTROL_AWB_MODE, (Integer) CameraDevice20.this.o.get(CaptureRequest.CONTROL_AWB_MODE));
                createCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, (Integer) CameraDevice20.this.o.get(CaptureRequest.JPEG_ORIENTATION));
                CameraDevice20.this.h();
                CameraDevice20.this.n.capture(createCaptureRequest.build(), CameraDevice20.this.a, CameraDevice20.this.y);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        private void a(CaptureResult captureResult) {
            final boolean z;
            Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_MODE);
            if (num == null || num.intValue() == 5 || num.intValue() == 0) {
                boolean unused = CameraDevice20.this.F = false;
                z = false;
            } else {
                Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num2 != null) {
                    switch (num2.intValue()) {
                        case 4:
                            boolean unused2 = CameraDevice20.this.F = false;
                            z = true;
                            break;
                        case 5:
                            boolean unused3 = CameraDevice20.this.F = false;
                            z = false;
                            break;
                    }
                }
                z = true;
            }
            if (!CameraDevice20.this.F) {
                synchronized (b.b) {
                    if (b.a != 0) {
                        CameraDevice20.this.B.post(new Runnable() {
                            public void run() {
                                synchronized (b.b) {
                                    if (b.a != 0) {
                                        CameraDevice20.onAutoFocus(b.a, z);
                                    }
                                }
                            }
                        });
                    }
                }
                CameraDevice20.this.o.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                Integer num3 = (Integer) CameraDevice20.this.o.get(CaptureRequest.CONTROL_AF_MODE);
                if (num3 != null) {
                    CameraDevice20.this.o.set(CaptureRequest.CONTROL_MODE, 1);
                    CameraDevice20.this.o.set(CaptureRequest.CONTROL_AF_MODE, num3);
                }
                try {
                    CameraDevice20.this.n.capture(CameraDevice20.this.o.build(), CameraDevice20.this.b, CameraDevice20.this.y);
                } catch (CameraAccessException e) {
                }
            }
        }

        private void b(CaptureResult captureResult) {
            if (CameraDevice20.this.L.b(a.PreviewRun)) {
                CaptureResult unused = CameraDevice20.this.m = captureResult;
                if (!CameraDevice20.this.D) {
                    if (CameraDevice20.this.F) {
                        a(captureResult);
                    }
                    if (CameraDevice20.this.E && !CameraDevice20.this.C && !CameraDevice20.this.F) {
                        Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                        if (num == null || (!(num.intValue() == 3 || num.intValue() == 1) || CameraDevice20.this.J || CameraDevice20.this.K >= 10)) {
                            int unused2 = CameraDevice20.this.K = 0;
                            boolean unused3 = CameraDevice20.this.C = true;
                            a();
                            return;
                        }
                        CameraDevice20.r(CameraDevice20.this);
                    }
                }
            }
        }

        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            if (CameraDevice20.this.L.b(a.PreviewStarting)) {
                CameraDevice20.this.L.a(a.PreviewRun);
            }
        }

        public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
            b(captureResult);
        }

        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            b(totalCaptureResult);
        }
    };
    private final CameraCaptureSession.StateCallback c = new CameraCaptureSession.StateCallback() {
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            if (CameraDevice20.this.i != null) {
                CameraCaptureSession unused = CameraDevice20.this.n = cameraCaptureSession;
                try {
                    CaptureRequest.Builder unused2 = CameraDevice20.this.o = CameraDevice20.this.i.createCaptureRequest(1);
                    CameraDevice20.this.o.addTarget(CameraDevice20.this.t.getSurface());
                    CameraDevice20.this.o.set(CaptureRequest.CONTROL_MODE, 1);
                    CameraDevice20.this.o.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    CameraDevice20.this.o.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(CameraDevice20.this.d()));
                    CameraDevice20.this.L.a(a.SessionReady);
                } catch (CameraAccessException e) {
                    CameraDevice20.this.a(4);
                }
            }
        }

        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraDevice20.this.a(4);
        }
    };
    private final CameraDevice.StateCallback d = new CameraDevice.StateCallback() {
        public void onOpened(CameraDevice cameraDevice) {
            CameraDevice unused = CameraDevice20.this.i = cameraDevice;
            CameraDevice20.this.L.a(a.CameraOpened);
            if (!CameraDevice20.this.e()) {
                CameraDevice20.this.a(4);
                synchronized (c.a) {
                    CameraDevice20.this.a();
                }
            }
        }

        public void onError(CameraDevice cameraDevice, int i) {
            CameraDevice20.this.a(1);
            synchronized (c.a) {
                CameraDevice20.this.a();
            }
        }

        public void onDisconnected(CameraDevice cameraDevice) {
            CameraDevice20.this.a(1);
            synchronized (c.a) {
                CameraDevice20.this.a();
            }
        }

        public void onClosed(CameraDevice cameraDevice) {
            CameraDevice20.this.L.a(a.CameraClosed);
        }
    };
    private final ImageReader.OnImageAvailableListener e = new ImageReader.OnImageAvailableListener() {
        public void onImageAvailable(ImageReader imageReader) {
            Image acquireLatestImage = imageReader.acquireLatestImage();
            if (acquireLatestImage != null) {
                if (!CameraDevice20.this.D && CameraDevice20.this.E) {
                    if (CameraDevice20.this.J) {
                        int unused = CameraDevice20.this.c();
                    }
                    Image.Plane[] planes = acquireLatestImage.getPlanes();
                    if (planes.length == 1 && acquireLatestImage.getFormat() == 256) {
                        synchronized (c.b) {
                            CameraDevice20.this.N.b();
                            CameraDevice20.this.N.a(0).put(planes[0].getBuffer());
                            CameraDevice20.this.N.a(0, planes[0].getPixelStride());
                            CameraDevice20.this.N.b(0, planes[0].getRowStride());
                        }
                        synchronized (b.b) {
                            if (b.a != 0) {
                                CameraDevice20.this.B.post(new Runnable() {
                                    public void run() {
                                        synchronized (c.b) {
                                            if (!CameraDevice20.this.D) {
                                                synchronized (b.b) {
                                                    if (b.a != 0) {
                                                        CameraDevice20.onStillImage(b.a, CameraDevice20.this.r.getWidth(), CameraDevice20.this.r.getHeight(), CameraDevice20.this.v, SystemClock.uptimeMillis(), CameraDevice20.this.N.a(0), CameraDevice20.this.N.a(0).limit());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                    boolean unused2 = CameraDevice20.this.C = false;
                    boolean unused3 = CameraDevice20.this.E = false;
                    if (CameraDevice20.this.J) {
                        boolean unused4 = CameraDevice20.this.J = false;
                    } else {
                        CameraDevice20.this.L.a(a.PreviewStarting);
                        CameraDevice20.this.g();
                    }
                }
                acquireLatestImage.close();
            }
        }
    };
    private final ImageReader.OnImageAvailableListener f = new ImageReader.OnImageAvailableListener() {
        public void onImageAvailable(ImageReader imageReader) {
            Image acquireLatestImage = imageReader.acquireLatestImage();
            if (acquireLatestImage != null) {
                if (!CameraDevice20.this.D && !CameraDevice20.this.J && !CameraDevice20.this.E && CameraDevice20.this.L.b(a.PreviewRun)) {
                    Image.Plane[] planes = acquireLatestImage.getPlanes();
                    if (planes.length == 3 && acquireLatestImage.getFormat() == 35) {
                        synchronized (c.b) {
                            CameraDevice20.this.M.a(planes);
                        }
                        CameraDevice20.this.B.post(CameraDevice20.this.g);
                    }
                }
                acquireLatestImage.close();
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable g = new Runnable() {
        public void run() {
            c a2;
            synchronized (c.b) {
                if (!CameraDevice20.this.D && !CameraDevice20.this.J && !CameraDevice20.this.E && CameraDevice20.this.L.b(a.PreviewRun) && (a2 = CameraDevice20.this.M.a()) != null) {
                    synchronized (b.b) {
                        if (b.a != 0) {
                            CameraDevice20.onVideoImage(b.a, CameraDevice20.this.q.getWidth(), CameraDevice20.this.q.getHeight(), CameraDevice20.this.u, SystemClock.uptimeMillis(), a2.a(0), a2.a(0).limit(), a2.c(0), a2.b(0), a2.a(1), a2.a(1).limit(), a2.c(1), a2.b(1), a2.a(2), a2.a(2).limit(), a2.c(2), a2.b(2));
                        }
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public CameraDevice i = null;
    private String j = null;
    private int k = -1;
    private CameraManager l = null;
    /* access modifiers changed from: private */
    public CaptureResult m = null;
    /* access modifiers changed from: private */
    public CameraCaptureSession n = null;
    /* access modifiers changed from: private */
    public CaptureRequest.Builder o = null;
    /* access modifiers changed from: private */
    public MediaActionSound p = null;
    /* access modifiers changed from: private */
    public Size q = h;
    /* access modifiers changed from: private */
    public Size r = h;
    /* access modifiers changed from: private */
    public ImageReader s = null;
    /* access modifiers changed from: private */
    public ImageReader t = null;
    /* access modifiers changed from: private */
    public int u = 1;
    /* access modifiers changed from: private */
    public int v = 4;
    private Context w = null;
    private HandlerThread x = null;
    /* access modifiers changed from: private */
    public Handler y = null;
    private HandlerThread z = null;

    private enum a {
        CameraClosed,
        CameraOpening,
        CameraOpened,
        SessionCreating,
        SessionReady,
        PreviewStarting,
        PreviewRun,
        PreviewStopping
    }

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
    public static native void onStillImage(long j2, int i2, int i3, int i4, long j3, ByteBuffer byteBuffer, int i5);

    /* access modifiers changed from: private */
    public static native void onVideoImage(long j2, int i2, int i3, int i4, long j3, ByteBuffer byteBuffer, int i5, int i6, int i7, ByteBuffer byteBuffer2, int i8, int i9, int i10, ByteBuffer byteBuffer3, int i11, int i12, int i13);

    static /* synthetic */ int r(CameraDevice20 cameraDevice20) {
        int i2 = cameraDevice20.K;
        cameraDevice20.K = i2 + 1;
        return i2;
    }

    private class b {
        private a b;

        private b() {
            this.b = a.CameraClosed;
        }

        public synchronized void a(a aVar) {
            synchronized (a.b) {
                this.b = aVar;
                notifyAll();
            }
        }

        public boolean b(a aVar) {
            boolean z;
            synchronized (a.b) {
                z = this.b == aVar;
            }
            return z;
        }
    }

    private class c {
        private ByteBuffer[] b = new ByteBuffer[this.e];
        private int[] c = new int[this.e];
        private int[] d = new int[this.e];
        private int e;

        public c(int i, int i2, int i3, int i4) {
            this.e = i3;
            for (int i5 = 0; i5 < this.e; i5++) {
                if ((i4 == 2 || i4 == 1) && i5 > 0) {
                    this.b[i5] = ByteBuffer.allocateDirect(((i * i2) * 3) / 4);
                } else {
                    this.b[i5] = ByteBuffer.allocateDirect(((i * i2) * 3) / 2);
                }
                this.c[i5] = 0;
                this.d[i5] = 0;
            }
        }

        public void a() {
            for (int i = 0; i < this.e; i++) {
                this.b[i].clear();
                this.b[i] = null;
                this.c[i] = 0;
                this.d[i] = 0;
            }
        }

        public void b() {
            for (int i = 0; i < this.e; i++) {
                this.b[i].clear();
                this.c[i] = 0;
                this.d[i] = 0;
            }
        }

        public ByteBuffer a(int i) {
            if (i < 0 || i >= this.e) {
                return null;
            }
            return this.b[i];
        }

        public int b(int i) {
            if (i < 0 || i >= this.e) {
                return 0;
            }
            return this.c[i];
        }

        public void a(int i, int i2) {
            if (i >= 0 && i < this.e) {
                this.c[i] = i2;
            }
        }

        public int c(int i) {
            if (i < 0 || i >= this.e) {
                return 0;
            }
            return this.d[i];
        }

        public void b(int i, int i2) {
            if (i >= 0 && i < this.e) {
                this.d[i] = i2;
            }
        }

        public int c() {
            return this.e;
        }
    }

    private class d {
        private int b = 1;
        private long c = -1;
        private long d = 0;
        private c[] e = null;

        public d(int i, int i2, int i3, int i4, int i5) {
            this.b = Math.min(i, 4);
            this.e = new c[this.b];
            int i6 = 0;
            while (true) {
                int i7 = i6;
                if (i7 < this.b) {
                    this.e[i7] = new c(i2, i3, i4, i5);
                    i6 = i7 + 1;
                } else {
                    return;
                }
            }
        }

        public void a(Image.Plane[] planeArr) {
            int i = (int) (this.d % ((long) this.b));
            synchronized (a.c[i]) {
                c cVar = this.e[i];
                cVar.b();
                for (int i2 = 0; i2 < Math.min(cVar.c(), planeArr.length); i2++) {
                    cVar.a(i2).put(planeArr[i2].getBuffer());
                    cVar.a(i2, planeArr[i2].getPixelStride());
                    cVar.b(i2, planeArr[i2].getRowStride());
                }
                this.d++;
                if (this.c < 0) {
                    this.c = 0;
                }
            }
        }

        public c a() {
            c cVar;
            int i = (int) (this.c % ((long) this.b));
            synchronized (a.c[i]) {
                if (this.c < 0 || this.c >= this.d) {
                    cVar = null;
                } else {
                    cVar = this.e[i];
                    this.c++;
                }
            }
            return cVar;
        }

        public void b() {
            for (int i = 0; i < this.b; i++) {
                this.e[i].a();
                this.e[i] = null;
            }
            this.c = -1;
            this.d = 0;
        }
    }

    public CameraDevice20() {
        synchronized (c.a) {
            synchronized (b.b) {
                b.a = 0;
            }
            a.a();
            this.p = new MediaActionSound();
            this.p.load(0);
            this.z = new HandlerThread("Cd2APIHandlerThread");
            this.z.start();
            this.A = new Handler(this.z.getLooper());
            this.B = new Handler(Looper.getMainLooper());
            this.x = new HandlerThread("Cd2HandlerThread");
            this.x.start();
            this.y = new Handler(this.x.getLooper()) {
                public void handleMessage(Message message) {
                    final boolean z;
                    switch (message.what) {
                        case 1:
                            CameraDevice20 cameraDevice20 = (CameraDevice20) message.obj;
                            if (cameraDevice20.D) {
                                return;
                            }
                            if (CameraDevice20.this.m == null) {
                                CameraDevice20.this.y.sendMessage(message);
                                return;
                            }
                            Integer num = (Integer) CameraDevice20.this.m.get(CaptureResult.CONTROL_AE_MODE);
                            if (num == null || num.intValue() == 0) {
                                boolean unused = CameraDevice20.this.G = false;
                                synchronized (b.b) {
                                    if (b.a != 0) {
                                        CameraDevice20.this.B.post(new Runnable() {
                                            public void run() {
                                                synchronized (b.b) {
                                                    if (b.a != 0) {
                                                        CameraDevice20.onAutoExposure(b.a, false);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                return;
                            } else if (CameraDevice20.this.G) {
                                Integer num2 = (Integer) CameraDevice20.this.m.get(CaptureResult.CONTROL_AE_STATE);
                                if (num2.intValue() == 2 || num2.intValue() == 4) {
                                    if (num2.intValue() == 4) {
                                        z = false;
                                    } else {
                                        z = true;
                                    }
                                    boolean unused2 = CameraDevice20.this.G = false;
                                    synchronized (b.b) {
                                        if (b.a != 0) {
                                            CameraDevice20.this.B.post(new Runnable() {
                                                public void run() {
                                                    synchronized (b.b) {
                                                        if (b.a != 0) {
                                                            CameraDevice20.onAutoExposure(b.a, z);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    if (CameraDevice20.this.o != null) {
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_MODE, 1);
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_AE_MODE, 1);
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_AE_LOCK, true);
                                        CameraDevice20.this.y.post(new Runnable() {
                                            public void run() {
                                                int unused = CameraDevice20.this.i();
                                            }
                                        });
                                        return;
                                    }
                                    return;
                                }
                                CameraDevice20.this.y.sendMessageDelayed(CameraDevice20.this.y.obtainMessage(1, cameraDevice20), 100);
                                return;
                            } else {
                                return;
                            }
                        case 2:
                            CameraDevice20 cameraDevice202 = (CameraDevice20) message.obj;
                            if (cameraDevice202.D) {
                                return;
                            }
                            if (CameraDevice20.this.m == null) {
                                CameraDevice20.this.y.sendMessage(message);
                                return;
                            }
                            Integer num3 = (Integer) CameraDevice20.this.m.get(CaptureResult.CONTROL_AWB_MODE);
                            if (num3 == null || num3.intValue() != 1) {
                                boolean unused3 = CameraDevice20.this.H = false;
                                synchronized (b.b) {
                                    if (b.a != 0) {
                                        CameraDevice20.this.B.post(new Runnable() {
                                            public void run() {
                                                synchronized (b.b) {
                                                    if (b.a != 0) {
                                                        CameraDevice20.onAutoWhiteBalance(b.a, false);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                return;
                            } else if (!CameraDevice20.this.H) {
                                return;
                            } else {
                                if (((Integer) CameraDevice20.this.m.get(CaptureResult.CONTROL_AWB_STATE)).intValue() == 2) {
                                    boolean unused4 = CameraDevice20.this.H = false;
                                    synchronized (b.b) {
                                        if (b.a != 0) {
                                            CameraDevice20.this.B.post(new Runnable() {
                                                public void run() {
                                                    synchronized (b.b) {
                                                        if (b.a != 0) {
                                                            CameraDevice20.onAutoWhiteBalance(b.a, true);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    if (CameraDevice20.this.o != null) {
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_MODE, 1);
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_AWB_MODE, 1);
                                        CameraDevice20.this.o.set(CaptureRequest.CONTROL_AWB_LOCK, true);
                                        CameraDevice20.this.y.post(new Runnable() {
                                            public void run() {
                                                int unused = CameraDevice20.this.i();
                                            }
                                        });
                                        return;
                                    }
                                    return;
                                }
                                CameraDevice20.this.y.sendMessageDelayed(CameraDevice20.this.y.obtainMessage(2, cameraDevice202), 100);
                                return;
                            }
                        default:
                            super.handleMessage(message);
                            return;
                    }
                }
            };
        }
    }

    public int open(Context context, long j2, int i2, Object obj, boolean z2) {
        int i3;
        int i4;
        String str;
        String str2;
        String str3;
        synchronized (c.a) {
            this.L.a(a.CameraOpening);
            if ((obj == null || (obj instanceof CameraDevice)) && context != null) {
                this.w = context;
                synchronized (b.b) {
                    b.a = j2;
                }
                this.l = (CameraManager) this.w.getSystemService("camera");
                if (this.l == null) {
                    i3 = -2138308594;
                } else {
                    String[] defaultCameraId2withString = getDefaultCameraId2withString(this.w, false);
                    int parseInt = Integer.parseInt(defaultCameraId2withString[0]);
                    if (i2 < 0) {
                        i2 = parseInt;
                    }
                    String str4 = defaultCameraId2withString[1];
                    int i5 = 0;
                    try {
                        String[] cameraIdList = this.l.getCameraIdList();
                        int length = cameraIdList.length;
                        int i6 = 0;
                        while (true) {
                            if (i6 >= length) {
                                i4 = i5;
                                str = str4;
                                break;
                            }
                            String str5 = cameraIdList[i6];
                            Integer num = (Integer) this.l.getCameraCharacteristics(str5).get(CameraCharacteristics.LENS_FACING);
                            if (num == null || num.intValue() != 0) {
                                if (num != null) {
                                    if (num.intValue() == 1) {
                                        i4 = i5 + 1;
                                        if (parseInt == 0) {
                                            str3 = str5;
                                        } else {
                                            str3 = str4;
                                        }
                                        if (i2 == 0) {
                                            this.j = str5;
                                            this.k = i2;
                                            break;
                                        }
                                    }
                                }
                                if (num != null) {
                                    if (num.intValue() == 2) {
                                        i4 = i5 + 1;
                                        if (parseInt == i4) {
                                            str2 = str5;
                                        } else {
                                            str2 = str4;
                                        }
                                        if (i2 == i4) {
                                            this.j = str5;
                                            this.k = i2;
                                            break;
                                        }
                                    }
                                }
                                i4 = i5;
                                str = str4;
                            } else {
                                i4 = i5 + 1;
                                if (parseInt == 1) {
                                    str = str5;
                                } else {
                                    str = str4;
                                }
                                if (i2 == 1) {
                                    this.j = str5;
                                    this.k = i2;
                                    break;
                                }
                            }
                            i6++;
                            str4 = str;
                            i5 = i4;
                        }
                        if (i4 <= i2) {
                            this.j = str;
                        }
                        if (this.j == null) {
                            this.L.a(a.CameraClosed);
                            i3 = -2138308594;
                        } else {
                            if (obj == null) {
                                try {
                                    this.l.openCamera(this.j, this.d, this.y);
                                    if (q() != 0) {
                                        this.L.a(a.CameraClosed);
                                        i3 = -2138308594;
                                    }
                                } catch (CameraAccessException e2) {
                                    this.L.a(a.CameraClosed);
                                    i3 = -2138308594;
                                }
                            } else {
                                this.i = (CameraDevice) obj;
                                this.L.a(a.CameraOpened);
                                if (!e()) {
                                    this.L.a(a.CameraClosed);
                                    i3 = -2138308594;
                                }
                            }
                            i3 = 0;
                        }
                    } catch (CameraAccessException e3) {
                        this.L.a(a.CameraClosed);
                        i3 = -2138308594;
                    }
                }
            } else {
                this.L.a(a.CameraClosed);
                i3 = -2138308603;
            }
        }
        return i3;
    }

    public static String[] getDefaultCameraId2withString(Context context, boolean z2) {
        String[] strArr = new String[2];
        CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
        if (cameraManager == null) {
            strArr[0] = String.valueOf(-1);
            strArr[1] = null;
            return strArr;
        }
        try {
            int i2 = 0;
            for (String str : cameraManager.getCameraIdList()) {
                Integer num = (Integer) cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING);
                if (num == null || num.intValue() != 0) {
                    if (num != null && num.intValue() == 1 && !z2) {
                        strArr[0] = String.valueOf(i2);
                        strArr[1] = str;
                        return strArr;
                    }
                } else if (z2) {
                    strArr[0] = String.valueOf(i2);
                    strArr[1] = str;
                    return strArr;
                }
                i2++;
            }
            strArr[0] = String.valueOf(-1);
            strArr[1] = null;
            return strArr;
        } catch (CameraAccessException e2) {
            strArr[0] = String.valueOf(-1);
            strArr[1] = null;
            return strArr;
        }
    }

    public int getImageSensorRotation() {
        int d2;
        synchronized (c.a) {
            d2 = d();
        }
        return d2;
    }

    public static int getDefaultCameraId2(Context context, boolean z2) {
        return Integer.parseInt(getDefaultCameraId2withString(context, z2)[0]);
    }

    public void release() {
        synchronized (c.a) {
            synchronized (b.b) {
                this.B.removeCallbacksAndMessages((Object) null);
                b.a = 0;
            }
            a();
        }
    }

    public int setNativeVideoOutput(SurfaceView surfaceView) {
        synchronized (c.a) {
        }
        return 0;
    }

    public int setVideoImageSize(int i2, int i3) {
        int i4 = 0;
        synchronized (c.a) {
            if (this.q.getWidth() != i2 || this.q.getHeight() != i3) {
                i4 = c();
                if (i4 == 0) {
                    this.q = null;
                    this.q = new Size(i2, i3);
                    k();
                    i4 = q();
                    if (i4 == 0) {
                        if (!a(true, false, false)) {
                            i4 = -2138308594;
                        } else {
                            i4 = b();
                        }
                    }
                }
            }
        }
        return i4;
    }

    public int setVideoImageFormat(int i2) {
        synchronized (c.a) {
            if (this.u == i2) {
                return 0;
            }
            if (i2 != 1) {
                return -2138308603;
            }
            if (Utility.a(true, i2) == -1) {
                return -2138308603;
            }
            int c2 = c();
            if (c2 != 0) {
                return c2;
            }
            this.u = i2;
            int q2 = q();
            if (q2 != 0) {
                return q2;
            }
            if (!a(true, false, false)) {
                return -2138308594;
            }
            int b2 = b();
            return b2;
        }
    }

    public int setVideoImageFpsRange(int i2, int i3) {
        synchronized (c.a) {
        }
        return -2138308603;
    }

    public int setStillImageSize(int i2, int i3) {
        int i4 = 0;
        synchronized (c.a) {
            if (this.r.getWidth() != i2 || this.r.getHeight() != i3) {
                i4 = c();
                if (i4 == 0) {
                    this.r = null;
                    this.r = new Size(i2, i3);
                    n();
                    i4 = q();
                    if (i4 == 0) {
                        if (!a(false, true, false)) {
                            i4 = -2138308594;
                        } else {
                            i4 = b();
                        }
                    }
                }
            }
        }
        return i4;
    }

    public int setStillImageFormat(int i2) {
        synchronized (c.a) {
            if (this.v == i2) {
                return 0;
            }
            if (i2 != 4) {
                return -2138308603;
            }
            if (Utility.a(true, i2) == -1) {
                return -2138308603;
            }
            int c2 = c();
            if (c2 != 0) {
                return c2;
            }
            this.v = i2;
            int q2 = q();
            if (q2 != 0) {
                return q2;
            }
            if (!a(false, true, false)) {
                return -2138308594;
            }
            int b2 = b();
            return b2;
        }
    }

    public int start() {
        int b2;
        synchronized (c.a) {
            b2 = b();
        }
        return b2;
    }

    public int stop() {
        int c2;
        synchronized (c.a) {
            c2 = c();
        }
        return c2;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public int captureStillImage() {
        /*
            r12 = this;
            r0 = -2138308594(0xffffffff808c000e, float:-1.2856989E-38)
            java.lang.Object r4 = com.sony.smartar.c.a
            monitor-enter(r4)
            boolean r1 = r12.E     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x000c
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.Object r1 = com.sony.smartar.c.b     // Catch:{ all -> 0x003d }
            monitor-enter(r1)     // Catch:{ all -> 0x003d }
            android.os.Handler r2 = r12.B     // Catch:{ all -> 0x0040 }
            java.lang.Runnable r3 = r12.g     // Catch:{ all -> 0x0040 }
            r2.removeCallbacks(r3)     // Catch:{ all -> 0x0040 }
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            com.sony.smartar.CameraDevice20$b r1 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r2 = com.sony.smartar.CameraDevice20.a.PreviewStopping     // Catch:{ all -> 0x003d }
            boolean r1 = r1.b(r2)     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x0070
            r2 = 2500(0x9c4, double:1.235E-320)
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x003d }
        L_0x0027:
            com.sony.smartar.CameraDevice20$b r1 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r5 = com.sony.smartar.CameraDevice20.a.SessionReady     // Catch:{ all -> 0x003d }
            boolean r1 = r1.b(r5)     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x0043
            r0 = 1
            r12.E = r0     // Catch:{ all -> 0x003d }
            r0 = 1
            r12.J = r0     // Catch:{ all -> 0x003d }
            r12.b()     // Catch:{ all -> 0x003d }
        L_0x003a:
            r0 = 0
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            goto L_0x000b
        L_0x003d:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            throw r0
        L_0x0040:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            throw r0     // Catch:{ all -> 0x003d }
        L_0x0043:
            com.sony.smartar.CameraDevice20$b r1 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r5 = com.sony.smartar.CameraDevice20.a.PreviewStopping     // Catch:{ all -> 0x003d }
            boolean r1 = r1.b(r5)     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x005f
            long r8 = java.lang.System.nanoTime()     // Catch:{ all -> 0x003d }
            long r8 = r8 - r6
            r10 = 1000000(0xf4240, double:4.940656E-318)
            long r8 = r8 / r10
            long r2 = r2 - r8
            r8 = 0
            int r1 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r1 > 0) goto L_0x0061
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            goto L_0x000b
        L_0x005f:
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            goto L_0x000b
        L_0x0061:
            com.sony.smartar.CameraDevice20$b r5 = r12.L     // Catch:{ InterruptedException -> 0x006e }
            monitor-enter(r5)     // Catch:{ InterruptedException -> 0x006e }
            com.sony.smartar.CameraDevice20$b r1 = r12.L     // Catch:{ all -> 0x006b }
            r1.wait(r2)     // Catch:{ all -> 0x006b }
            monitor-exit(r5)     // Catch:{ all -> 0x006b }
            goto L_0x0027
        L_0x006b:
            r1 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006b }
            throw r1     // Catch:{ InterruptedException -> 0x006e }
        L_0x006e:
            r1 = move-exception
            goto L_0x0027
        L_0x0070:
            com.sony.smartar.CameraDevice20$b r0 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r1 = com.sony.smartar.CameraDevice20.a.SessionReady     // Catch:{ all -> 0x003d }
            boolean r0 = r0.b(r1)     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x0084
            r0 = 1
            r12.E = r0     // Catch:{ all -> 0x003d }
            r0 = 1
            r12.J = r0     // Catch:{ all -> 0x003d }
            r12.b()     // Catch:{ all -> 0x003d }
            goto L_0x003a
        L_0x0084:
            com.sony.smartar.CameraDevice20$b r0 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r1 = com.sony.smartar.CameraDevice20.a.PreviewRun     // Catch:{ all -> 0x003d }
            boolean r0 = r0.b(r1)     // Catch:{ all -> 0x003d }
            if (r0 != 0) goto L_0x0098
            com.sony.smartar.CameraDevice20$b r0 = r12.L     // Catch:{ all -> 0x003d }
            com.sony.smartar.CameraDevice20$a r1 = com.sony.smartar.CameraDevice20.a.PreviewStarting     // Catch:{ all -> 0x003d }
            boolean r0 = r0.b(r1)     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x009c
        L_0x0098:
            r0 = 1
            r12.E = r0     // Catch:{ all -> 0x003d }
            goto L_0x003a
        L_0x009c:
            r0 = -2138308608(0xffffffff808c0000, float:-1.285697E-38)
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.captureStillImage():int");
    }

    public int runAutoFocus() {
        int i2 = -2138308603;
        synchronized (c.a) {
            if (!this.L.b(a.PreviewRun)) {
                i2 = -2138308598;
            } else if (this.F) {
                i2 = -2138308594;
            } else {
                this.F = true;
                if (this.o != null) {
                    try {
                        this.o.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                        this.n.capture(this.o.build(), this.b, this.y);
                        i2 = 0;
                    } catch (CameraAccessException e2) {
                    }
                }
            }
        }
        return i2;
    }

    public int runAutoExposure() {
        synchronized (c.a) {
            if (!this.L.b(a.PreviewRun)) {
                return -2138308598;
            }
            if (this.G) {
                return -2138308594;
            }
            this.G = true;
            if (this.o == null) {
                return -2138308603;
            }
            this.o.set(CaptureRequest.CONTROL_MODE, 1);
            this.o.set(CaptureRequest.CONTROL_AE_MODE, 1);
            this.o.set(CaptureRequest.CONTROL_AE_LOCK, false);
            if (i() != 0) {
                this.G = false;
                return -2138308603;
            }
            this.y.sendMessageDelayed(this.y.obtainMessage(1, this), 3000);
            return 0;
        }
    }

    public int runAutoWhiteBalance() {
        synchronized (c.a) {
            if (!this.L.b(a.PreviewRun)) {
                return -2138308598;
            }
            if (this.H) {
                return -2138308594;
            }
            this.H = true;
            if (this.o == null) {
                return -2138308603;
            }
            this.o.set(CaptureRequest.CONTROL_MODE, 1);
            this.o.set(CaptureRequest.CONTROL_AWB_MODE, 1);
            this.o.set(CaptureRequest.CONTROL_AWB_LOCK, false);
            if (i() != 0) {
                this.H = false;
                return -2138308603;
            }
            this.y.sendMessageDelayed(this.y.obtainMessage(2, this), 3000);
            return 0;
        }
    }

    public int setFocusMode(int i2) {
        int i3;
        int i4 = -2138308603;
        synchronized (c.a) {
            if (this.F) {
                this.F = false;
                if (this.o != null) {
                    this.o.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                    i();
                }
            }
            if (this.n != null) {
                switch (i2) {
                    case 0:
                        i3 = 1;
                        break;
                    case 1:
                        i3 = 4;
                        break;
                    case 2:
                        i3 = 3;
                        break;
                    case 3:
                        i3 = 5;
                        break;
                    case 4:
                        i3 = 0;
                        break;
                    case 5:
                        i3 = 0;
                        this.o.set(CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(0.0f));
                        break;
                    case 6:
                        i3 = 2;
                        break;
                    default:
                        d.b("unexpected value: " + i2);
                        break;
                }
                if (this.o != null) {
                    this.o.set(CaptureRequest.CONTROL_MODE, 1);
                    this.o.set(CaptureRequest.CONTROL_AF_MODE, i3);
                    i4 = i();
                }
            }
        }
        return i4;
    }

    public int setFocusAreas(float[] fArr, int i2) {
        synchronized (c.a) {
            this.o.set(CaptureRequest.CONTROL_AF_REGIONS, a(fArr, i2));
        }
        return 0;
    }

    public int setExposureMode(int i2) {
        boolean z2 = false;
        int i3 = -2138308603;
        synchronized (c.a) {
            if (this.n != null) {
                switch (i2) {
                    case 0:
                        z2 = true;
                        break;
                    case 1:
                        break;
                    default:
                        d.b("unexpected value: " + i2);
                        break;
                }
                if (this.o != null) {
                    this.o.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.valueOf(z2));
                    this.G = false;
                    i3 = i();
                }
            }
        }
        return i3;
    }

    public int setExposureAreas(float[] fArr, int i2) {
        synchronized (c.a) {
            this.o.set(CaptureRequest.CONTROL_AE_REGIONS, a(fArr, i2));
        }
        return 0;
    }

    public int setFlashMode(int i2) {
        int i3;
        synchronized (c.a) {
            if (this.n == null) {
                return -2138308603;
            }
            if (this.o == null) {
                return -2138308603;
            }
            if (((Integer) this.o.get(CaptureRequest.FLASH_MODE)).intValue() == 2) {
                this.o.set(CaptureRequest.CONTROL_AE_MODE, 1);
                this.o.set(CaptureRequest.FLASH_MODE, 0);
                int i4 = i();
                if (i4 != 0) {
                    return i4;
                }
            }
            int i5 = null;
            switch (i2) {
                case 0:
                    i3 = 2;
                    break;
                case 1:
                    i3 = 1;
                    i5 = 0;
                    break;
                case 2:
                    i3 = 3;
                    break;
                case 3:
                    i3 = 4;
                    break;
                case 4:
                    i3 = 1;
                    i5 = 2;
                    break;
                default:
                    d.b("unexpected value: " + i2);
                    return -2138308603;
            }
            this.o.set(CaptureRequest.CONTROL_AE_MODE, i3);
            if (i5 != null) {
                this.o.set(CaptureRequest.FLASH_MODE, i5);
            }
            int i6 = i();
            return i6;
        }
    }

    public int setWhiteBalanceMode(int i2) {
        int i3;
        boolean z2 = false;
        int i4 = -2138308603;
        synchronized (c.a) {
            if (this.n != null) {
                switch (i2) {
                    case 0:
                        i3 = 1;
                        break;
                    case 1:
                        i3 = 6;
                        break;
                    case 2:
                        i3 = 5;
                        break;
                    case 3:
                        i3 = 3;
                        break;
                    case 4:
                        i3 = 2;
                        break;
                    case 5:
                        i3 = 8;
                        break;
                    case 6:
                        i3 = 7;
                        break;
                    case 7:
                        i3 = 4;
                        break;
                    case 8:
                        i3 = 0;
                        z2 = true;
                        break;
                    default:
                        d.b("unexpected value: " + i2);
                        break;
                }
                if (this.o != null) {
                    this.o.set(CaptureRequest.CONTROL_MODE, 1);
                    this.o.set(CaptureRequest.CONTROL_AWB_MODE, i3);
                    this.H = false;
                    this.o.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(z2));
                    i4 = i();
                }
            }
        }
        return i4;
    }

    public int setSceneMode(int i2) {
        int i3;
        int i4 = -2138308603;
        synchronized (c.a) {
            if (this.n != null) {
                switch (i2) {
                    case 0:
                        i3 = 2;
                        break;
                    case 1:
                        i3 = 0;
                        break;
                    case 2:
                        i3 = 16;
                        break;
                    case 3:
                        i3 = 8;
                        break;
                    case 4:
                        i3 = 15;
                        break;
                    case 5:
                        i3 = 12;
                        break;
                    case 6:
                        i3 = 4;
                        break;
                    case 7:
                        i3 = 5;
                        break;
                    case 8:
                        i3 = 6;
                        break;
                    case 9:
                        i3 = 14;
                        break;
                    case 10:
                        i3 = 3;
                        break;
                    case 11:
                        i3 = 9;
                        break;
                    case 12:
                        i3 = 13;
                        break;
                    case 13:
                        i3 = 11;
                        break;
                    case 14:
                        i3 = 10;
                        break;
                    case 15:
                        i3 = 7;
                        break;
                    default:
                        d.b("unexpected value: " + i2);
                        break;
                }
                if (this.o != null) {
                    this.o.set(CaptureRequest.CONTROL_MODE, 1);
                    this.o.set(CaptureRequest.CONTROL_SCENE_MODE, i3);
                    i4 = i();
                }
            }
        }
        return i4;
    }

    public int getSupportedVideoImageSize(int[] iArr, int i2) {
        int a2;
        synchronized (c.a) {
            a2 = a(iArr, i2);
        }
        return a2;
    }

    public int getSupportedVideoImageFormat(int[] iArr, int i2) {
        int a2;
        synchronized (c.a) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(35);
            a2 = Utility.a(true, (List<Integer>) arrayList, iArr, i2);
        }
        return a2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSupportedVideoImageFpsRange(int[] r11, int r12) {
        /*
            r10 = this;
            r2 = 0
            java.lang.Object r5 = com.sony.smartar.c.a
            monitor-enter(r5)
            android.hardware.camera2.CameraManager r0 = r10.l     // Catch:{ all -> 0x006d }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            r1 = r2
        L_0x000a:
            return r1
        L_0x000b:
            android.hardware.camera2.CameraManager r0 = r10.l     // Catch:{ CameraAccessException -> 0x0064 }
            java.lang.String r1 = r10.j     // Catch:{ CameraAccessException -> 0x0064 }
            android.hardware.camera2.CameraCharacteristics r0 = r0.getCameraCharacteristics(r1)     // Catch:{ CameraAccessException -> 0x0064 }
            android.hardware.camera2.CameraCharacteristics$Key r1 = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP     // Catch:{ all -> 0x006d }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x006d }
            android.hardware.camera2.params.StreamConfigurationMap r0 = (android.hardware.camera2.params.StreamConfigurationMap) r0     // Catch:{ all -> 0x006d }
            r1 = 1
            int r3 = r10.u     // Catch:{ all -> 0x006d }
            int r1 = com.sony.smartar.Utility.a((boolean) r1, (int) r3)     // Catch:{ all -> 0x006d }
            android.util.Size[] r6 = r0.getOutputSizes(r1)     // Catch:{ all -> 0x006d }
            r1 = r2
            r4 = r2
        L_0x0028:
            int r3 = r6.length     // Catch:{ all -> 0x006d }
            if (r4 >= r3) goto L_0x006b
            if (r1 >= r12) goto L_0x006b
            r3 = 1399379109(0x5368d4a5, float:1.0E12)
            r7 = 1
            int r8 = r10.u     // Catch:{ all -> 0x006d }
            int r7 = com.sony.smartar.Utility.a((boolean) r7, (int) r8)     // Catch:{ all -> 0x006d }
            r8 = r6[r4]     // Catch:{ all -> 0x006d }
            long r8 = r0.getOutputMinFrameDuration(r7, r8)     // Catch:{ all -> 0x006d }
            float r7 = (float) r8     // Catch:{ all -> 0x006d }
            float r3 = r3 / r7
            int r7 = java.lang.Math.round(r3)     // Catch:{ all -> 0x006d }
            r3 = r2
        L_0x0044:
            if (r3 >= r1) goto L_0x004e
            int r8 = r3 * 2
            int r8 = r8 + 1
            r8 = r11[r8]     // Catch:{ all -> 0x006d }
            if (r8 != r7) goto L_0x0068
        L_0x004e:
            if (r3 != r1) goto L_0x0060
            int r3 = r1 * 2
            int r3 = r3 + 0
            r8 = 1000(0x3e8, float:1.401E-42)
            r11[r3] = r8     // Catch:{ all -> 0x006d }
            int r3 = r1 * 2
            int r3 = r3 + 1
            r11[r3] = r7     // Catch:{ all -> 0x006d }
            int r1 = r1 + 1
        L_0x0060:
            int r3 = r4 + 1
            r4 = r3
            goto L_0x0028
        L_0x0064:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            r1 = r2
            goto L_0x000a
        L_0x0068:
            int r3 = r3 + 1
            goto L_0x0044
        L_0x006b:
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            goto L_0x000a
        L_0x006d:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.getSupportedVideoImageFpsRange(int[], int):int");
    }

    public int getSupportedStillImageSize(int[] iArr, int i2) {
        int i3 = 0;
        synchronized (c.a) {
            if (this.l != null) {
                try {
                    i3 = Utility.a(true, (Object) Arrays.asList(((StreamConfigurationMap) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(Utility.a(true, this.v))), iArr, i2);
                } catch (CameraAccessException e2) {
                }
            }
        }
        return i3;
    }

    public int getSupportedStillImageFormat(int[] iArr, int i2) {
        int a2;
        synchronized (c.a) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(256);
            a2 = Utility.a(true, (List<Integer>) arrayList, iArr, i2);
        }
        return a2;
    }

    public int getSupportedFocusMode(int[] iArr, int i2) {
        int i3;
        int i4 = 0;
        synchronized (c.a) {
            if (this.l != null) {
                try {
                    int[] iArr2 = (int[]) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
                    if (iArr2.length != 0) {
                        int length = iArr2.length;
                        int i5 = 0;
                        while (i5 < length && i4 < i2) {
                            switch (iArr2[i5]) {
                                case 0:
                                    if (this.o != null) {
                                        if (((Float) this.o.get(CaptureRequest.LENS_FOCUS_DISTANCE)).floatValue() != 0.0f) {
                                            i3 = i4 + 1;
                                            iArr[i4] = 4;
                                            break;
                                        } else {
                                            i3 = i4 + 1;
                                            iArr[i4] = 5;
                                            break;
                                        }
                                    } else {
                                        i3 = i4 + 1;
                                        iArr[i4] = 4;
                                        break;
                                    }
                                case 1:
                                    i3 = i4 + 1;
                                    iArr[i4] = 0;
                                    break;
                                case 2:
                                    i3 = i4 + 1;
                                    iArr[i4] = 6;
                                    break;
                                case 3:
                                    i3 = i4 + 1;
                                    iArr[i4] = 2;
                                    break;
                                case 4:
                                    i3 = i4 + 1;
                                    iArr[i4] = 1;
                                    break;
                                case 5:
                                    i3 = i4 + 1;
                                    iArr[i4] = 3;
                                    break;
                                default:
                                    i3 = i4;
                                    break;
                            }
                            i5++;
                            i4 = i3;
                        }
                    }
                } catch (CameraAccessException e2) {
                }
            }
        }
        return i4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMaxNumFocusAreas() {
        /*
            r4 = this;
            r1 = 0
            java.lang.Object r2 = com.sony.smartar.c.a
            monitor-enter(r2)
            android.hardware.camera2.CameraCaptureSession r0 = r4.n     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            r0 = r1
        L_0x000a:
            return r0
        L_0x000b:
            android.hardware.camera2.CaptureRequest$Builder r0 = r4.o     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x0012
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            r0 = r1
            goto L_0x000a
        L_0x0012:
            android.hardware.camera2.CaptureRequest$Builder r0 = r4.o     // Catch:{ all -> 0x0021 }
            android.hardware.camera2.CaptureRequest$Key r3 = android.hardware.camera2.CaptureRequest.CONTROL_AF_REGIONS     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x0021 }
            android.hardware.camera2.params.MeteringRectangle[] r0 = (android.hardware.camera2.params.MeteringRectangle[]) r0     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x0024
            r0 = r1
        L_0x001f:
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            goto L_0x000a
        L_0x0021:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            throw r0
        L_0x0024:
            int r0 = r0.length     // Catch:{ all -> 0x0021 }
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.getMaxNumFocusAreas():int");
    }

    public int getSupportedFlashMode(int[] iArr, int i2) {
        int i3;
        int i4 = 0;
        synchronized (c.a) {
            if (this.l != null) {
                try {
                    int[] iArr2 = (int[]) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
                    if (iArr2.length != 0) {
                        int length = iArr2.length;
                        int i5 = 0;
                        while (i5 < length && i4 < i2) {
                            switch (iArr2[i5]) {
                                case 1:
                                    int i6 = i4 + 1;
                                    iArr[i4] = 1;
                                    i3 = i6 + 1;
                                    iArr[i6] = 4;
                                    break;
                                case 2:
                                    i3 = i4 + 1;
                                    iArr[i4] = 0;
                                    break;
                                case 3:
                                    i3 = i4 + 1;
                                    iArr[i4] = 2;
                                    break;
                                case 4:
                                    i3 = i4 + 1;
                                    iArr[i4] = 3;
                                    break;
                                default:
                                    i3 = i4;
                                    break;
                            }
                            i5++;
                            i4 = i3;
                        }
                    }
                } catch (CameraAccessException e2) {
                }
            }
        }
        return i4;
    }

    public int getSupportedExposureMode(int[] iArr, int i2) {
        int i3;
        int i4 = 1;
        synchronized (c.a) {
            if (0 < i2) {
                iArr[0] = 1;
            } else {
                i4 = 0;
            }
            if (i4 < i2) {
                i3 = i4 + 1;
                iArr[i4] = 0;
            } else {
                i3 = i4;
            }
        }
        return i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMaxNumExposureAreas() {
        /*
            r4 = this;
            r1 = 0
            java.lang.Object r2 = com.sony.smartar.c.a
            monitor-enter(r2)
            android.hardware.camera2.CameraCaptureSession r0 = r4.n     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            r0 = r1
        L_0x000a:
            return r0
        L_0x000b:
            android.hardware.camera2.CaptureRequest$Builder r0 = r4.o     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x0012
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            r0 = r1
            goto L_0x000a
        L_0x0012:
            android.hardware.camera2.CaptureRequest$Builder r0 = r4.o     // Catch:{ all -> 0x0021 }
            android.hardware.camera2.CaptureRequest$Key r3 = android.hardware.camera2.CaptureRequest.CONTROL_AE_REGIONS     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x0021 }
            android.hardware.camera2.params.MeteringRectangle[] r0 = (android.hardware.camera2.params.MeteringRectangle[]) r0     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x0024
            r0 = r1
        L_0x001f:
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            goto L_0x000a
        L_0x0021:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            throw r0
        L_0x0024:
            int r0 = r0.length     // Catch:{ all -> 0x0021 }
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.getMaxNumExposureAreas():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSupportedWhiteBalanceMode(int[] r8, int r9) {
        /*
            r7 = this;
            r1 = 0
            java.lang.Object r4 = com.sony.smartar.c.a
            monitor-enter(r4)
            android.hardware.camera2.CameraManager r0 = r7.l     // Catch:{ all -> 0x003d }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            r0 = r1
        L_0x000a:
            return r0
        L_0x000b:
            android.hardware.camera2.CameraManager r0 = r7.l     // Catch:{ CameraAccessException -> 0x0021 }
            java.lang.String r2 = r7.j     // Catch:{ CameraAccessException -> 0x0021 }
            android.hardware.camera2.CameraCharacteristics r0 = r0.getCameraCharacteristics(r2)     // Catch:{ CameraAccessException -> 0x0021 }
            android.hardware.camera2.CameraCharacteristics$Key r2 = android.hardware.camera2.CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES     // Catch:{ all -> 0x003d }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x003d }
            int[] r0 = (int[]) r0     // Catch:{ all -> 0x003d }
            int r2 = r0.length     // Catch:{ all -> 0x003d }
            if (r2 != 0) goto L_0x0025
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            r0 = r1
            goto L_0x000a
        L_0x0021:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            r0 = r1
            goto L_0x000a
        L_0x0025:
            int r5 = r0.length     // Catch:{ all -> 0x003d }
            r3 = r1
            r2 = r1
        L_0x0028:
            if (r3 >= r5) goto L_0x006a
            if (r2 >= r9) goto L_0x006a
            r1 = r0[r3]     // Catch:{ all -> 0x003d }
            switch(r1) {
                case 1: goto L_0x0037;
                case 2: goto L_0x0052;
                case 3: goto L_0x004c;
                case 4: goto L_0x0064;
                case 5: goto L_0x0046;
                case 6: goto L_0x0040;
                case 7: goto L_0x005e;
                case 8: goto L_0x0058;
                default: goto L_0x0031;
            }     // Catch:{ all -> 0x003d }
        L_0x0031:
            r1 = r2
        L_0x0032:
            int r2 = r3 + 1
            r3 = r2
            r2 = r1
            goto L_0x0028
        L_0x0037:
            int r1 = r2 + 1
            r6 = 0
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x003d:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            throw r0
        L_0x0040:
            int r1 = r2 + 1
            r6 = 1
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x0046:
            int r1 = r2 + 1
            r6 = 2
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x004c:
            int r1 = r2 + 1
            r6 = 3
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x0052:
            int r1 = r2 + 1
            r6 = 4
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x0058:
            int r1 = r2 + 1
            r6 = 5
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x005e:
            int r1 = r2 + 1
            r6 = 6
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x0064:
            int r1 = r2 + 1
            r6 = 7
            r8[r2] = r6     // Catch:{ all -> 0x003d }
            goto L_0x0032
        L_0x006a:
            if (r2 >= r9) goto L_0x0074
            int r0 = r2 + 1
            r1 = 8
            r8[r2] = r1     // Catch:{ all -> 0x003d }
        L_0x0072:
            monitor-exit(r4)     // Catch:{ all -> 0x003d }
            goto L_0x000a
        L_0x0074:
            r0 = r2
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.getSupportedWhiteBalanceMode(int[], int):int");
    }

    public int getSupportedSceneMode(int[] iArr, int i2) {
        int i3;
        int i4 = 0;
        synchronized (c.a) {
            if (this.l != null) {
                try {
                    int[] iArr2 = (int[]) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES);
                    if (iArr2.length != 0) {
                        iArr[0] = 1;
                        int length = iArr2.length;
                        int i5 = 0;
                        i4 = 1;
                        while (i5 < length && i4 < i2) {
                            switch (iArr2[i5]) {
                                case 0:
                                    i3 = i4;
                                    break;
                                case 2:
                                    i3 = i4 + 1;
                                    iArr[i4] = 0;
                                    break;
                                case 3:
                                    i3 = i4 + 1;
                                    iArr[i4] = 10;
                                    break;
                                case 4:
                                    i3 = i4 + 1;
                                    iArr[i4] = 6;
                                    break;
                                case 5:
                                    i3 = i4 + 1;
                                    iArr[i4] = 7;
                                    break;
                                case 6:
                                    i3 = i4 + 1;
                                    iArr[i4] = 8;
                                    break;
                                case 7:
                                    i3 = i4 + 1;
                                    iArr[i4] = 15;
                                    break;
                                case 8:
                                    i3 = i4 + 1;
                                    iArr[i4] = 3;
                                    break;
                                case 9:
                                    i3 = i4 + 1;
                                    iArr[i4] = 11;
                                    break;
                                case 10:
                                    i3 = i4 + 1;
                                    iArr[i4] = 14;
                                    break;
                                case 11:
                                    i3 = i4 + 1;
                                    iArr[i4] = 13;
                                    break;
                                case 12:
                                    i3 = i4 + 1;
                                    iArr[i4] = 5;
                                    break;
                                case 13:
                                    i3 = i4 + 1;
                                    iArr[i4] = 12;
                                    break;
                                case 14:
                                    i3 = i4 + 1;
                                    iArr[i4] = 9;
                                    break;
                                case 15:
                                    i3 = i4 + 1;
                                    iArr[i4] = 4;
                                    break;
                                case 16:
                                    i3 = i4 + 1;
                                    iArr[i4] = 2;
                                    break;
                                default:
                                    i3 = i4;
                                    break;
                            }
                            i5++;
                            i4 = i3;
                        }
                    }
                } catch (CameraAccessException e2) {
                }
            }
        }
        return i4;
    }

    public float[] getDeviceInfo() {
        float[] fArr;
        synchronized (c.a) {
            fArr = new float[15];
            fArr[0] = (float) this.k;
            fArr[1] = (float) this.q.getWidth();
            fArr[2] = (float) this.q.getHeight();
            fArr[3] = (float) this.r.getWidth();
            fArr[4] = (float) this.r.getHeight();
            CameraDeviceInterface.a aVar = (CameraDeviceInterface.a) sCameraModelInfos.get(getDefaultCameraId2(this.w, false));
            if (aVar != null) {
                fArr[5] = 1.0f;
                fArr[6] = aVar.a;
                fArr[7] = aVar.b;
                fArr[8] = aVar.c;
            }
            CameraDeviceInterface.a aVar2 = (CameraDeviceInterface.a) sCameraModelInfos.get(getDefaultCameraId2(this.w, true));
            if (aVar2 != null) {
                fArr[9] = 1.0f;
                fArr[10] = aVar2.a;
                fArr[11] = aVar2.b;
                fArr[12] = aVar2.c;
            }
            Point point = new Point();
            Utility.a(this.w, point);
            fArr[13] = (float) point.x;
            fArr[14] = (float) point.y;
        }
        return fArr;
    }

    public int getFacing() {
        int i2;
        synchronized (c.a) {
            switch (this.k) {
                case 0:
                    i2 = 0;
                    break;
                case 1:
                    i2 = 1;
                    break;
                default:
                    i2 = -2138308603;
                    break;
            }
        }
        return i2;
    }

    public int getRotation() {
        synchronized (c.a) {
            if (this.l == null) {
                return 0;
            }
            try {
                switch (((Integer) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue()) {
                    case 0:
                        return 0;
                    case 90:
                        return 90;
                    case 180:
                        return 180;
                    case 270:
                        return 270;
                    default:
                        return 0;
                }
            } catch (CameraAccessException e2) {
                return 0;
            }
        }
    }

    public Object getNativeDevice() {
        CameraDevice cameraDevice;
        synchronized (c.a) {
            cameraDevice = this.i;
        }
        return cameraDevice;
    }

    public float getFovY() {
        float height;
        synchronized (c.a) {
            height = j().getHeight();
        }
        return height;
    }

    public int getStillImageSize(int[] iArr) {
        int i2 = 0;
        synchronized (c.a) {
            if (iArr.length != 2) {
                i2 = -2138308602;
            } else {
                iArr[0] = this.r.getWidth();
                iArr[1] = this.r.getHeight();
            }
        }
        return i2;
    }

    public float getFocalLength() {
        float f2 = 0.0f;
        synchronized (c.a) {
            if (this.l != null) {
                try {
                    f2 = ((float[]) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS))[0];
                } catch (CameraAccessException e2) {
                }
            }
        }
        return f2;
    }

    public int getVideoImageSize(int[] iArr) {
        int i2 = 0;
        synchronized (c.a) {
            if (iArr.length != 2) {
                i2 = -2138308602;
            } else {
                iArr[0] = this.q.getWidth();
                iArr[1] = this.q.getHeight();
            }
        }
        return i2;
    }

    public int getMaxStillImageSize(int[] iArr) {
        int a2;
        synchronized (c.a) {
            a2 = a(iArr);
        }
        return a2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int[] getCameraAPIFeature() {
        /*
            r5 = this;
            java.lang.Object r2 = com.sony.smartar.c.a
            monitor-enter(r2)
            r0 = 2
            int[] r1 = new int[r0]     // Catch:{ all -> 0x003a }
            r0 = 0
            r3 = 2
            r1[r0] = r3     // Catch:{ all -> 0x003a }
            android.hardware.camera2.CameraManager r0 = r5.l     // Catch:{ all -> 0x003a }
            if (r0 != 0) goto L_0x0015
            r0 = 1
            r3 = 2
            r1[r0] = r3     // Catch:{ all -> 0x003a }
            monitor-exit(r2)     // Catch:{ all -> 0x003a }
            r0 = r1
        L_0x0014:
            return r0
        L_0x0015:
            r0 = 0
            android.hardware.camera2.CameraManager r3 = r5.l     // Catch:{ CameraAccessException -> 0x0034 }
            java.lang.String r4 = r5.j     // Catch:{ CameraAccessException -> 0x0034 }
            android.hardware.camera2.CameraCharacteristics r0 = r3.getCameraCharacteristics(r4)     // Catch:{ CameraAccessException -> 0x0034 }
        L_0x001e:
            android.hardware.camera2.CameraCharacteristics$Key r3 = android.hardware.camera2.CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL     // Catch:{ all -> 0x003a }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x003a }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x003a }
            int r0 = r0.intValue()     // Catch:{ all -> 0x003a }
            switch(r0) {
                case 0: goto L_0x003d;
                case 1: goto L_0x0042;
                case 2: goto L_0x002d;
                case 3: goto L_0x0047;
                default: goto L_0x002d;
            }     // Catch:{ all -> 0x003a }
        L_0x002d:
            r0 = 1
            r3 = 2
            r1[r0] = r3     // Catch:{ all -> 0x003a }
        L_0x0031:
            monitor-exit(r2)     // Catch:{ all -> 0x003a }
            r0 = r1
            goto L_0x0014
        L_0x0034:
            r3 = move-exception
            r3 = 1
            r4 = 2
            r1[r3] = r4     // Catch:{ all -> 0x003a }
            goto L_0x001e
        L_0x003a:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x003a }
            throw r0
        L_0x003d:
            r0 = 1
            r3 = 0
            r1[r0] = r3     // Catch:{ all -> 0x003a }
            goto L_0x0031
        L_0x0042:
            r0 = 1
            r3 = 1
            r1[r0] = r3     // Catch:{ all -> 0x003a }
            goto L_0x0031
        L_0x0047:
            r0 = 1
            r3 = 3
            r1[r0] = r3     // Catch:{ all -> 0x003a }
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.getCameraAPIFeature():int[]");
    }

    /* access modifiers changed from: private */
    public void a() {
        if (!this.D) {
            this.D = true;
            if (this.L.b(a.PreviewRun) || this.L.b(a.PreviewStarting)) {
                c();
            }
            if (this.n != null) {
                this.n.close();
                this.n = null;
            }
            if (this.s != null) {
                this.s.close();
                this.s = null;
            }
            if (this.i != null) {
                this.i.close();
                this.i = null;
            }
            if (this.p != null) {
                this.p.release();
            }
            this.l = null;
            if (this.z != null) {
                if (this.z.quitSafely()) {
                    try {
                        this.z.join(250);
                    } catch (InterruptedException e2) {
                    }
                }
                this.z = null;
            }
            if (this.x != null) {
                if (this.x.quitSafely()) {
                    try {
                        this.x.join(250);
                    } catch (InterruptedException e3) {
                    }
                }
                this.x = null;
            }
            if (!this.L.b(a.CameraClosed)) {
                this.L.a(a.CameraClosed);
            }
            l();
            o();
        }
    }

    /* access modifiers changed from: private */
    public void a(final int i2) {
        synchronized (b.b) {
            if (b.a != 0) {
                this.B.post(new Runnable() {
                    public void run() {
                        synchronized (b.b) {
                            if (b.a != 0) {
                                CameraDevice20.onError(b.a, i2);
                            }
                        }
                    }
                });
            }
        }
    }

    private int a(int[] iArr) {
        if (iArr.length != 2) {
            return -2138308602;
        }
        if (this.l == null) {
            return -2138308608;
        }
        try {
            Size[] outputSizes = ((StreamConfigurationMap) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(Utility.a(true, this.v));
            iArr[0] = 0;
            iArr[1] = 0;
            for (int i2 = 0; i2 < outputSizes.length; i2++) {
                if (iArr[0] <= outputSizes[i2].getWidth() && iArr[1] <= outputSizes[i2].getHeight()) {
                    iArr[0] = outputSizes[i2].getWidth();
                    iArr[1] = outputSizes[i2].getHeight();
                }
            }
            if (iArr[0] == 0 || iArr[1] == 0) {
                return -2138308608;
            }
            return 0;
        } catch (CameraAccessException e2) {
            return -2138308608;
        }
    }

    private int b() {
        if (this.L.b(a.CameraOpening)) {
            return -2138308594;
        }
        if (this.L.b(a.CameraOpened) || this.L.b(a.SessionCreating)) {
            if (q() != 0) {
                return -2138308594;
            }
            this.L.a(a.PreviewStarting);
            g();
        } else if (this.L.b(a.SessionReady) || this.L.b(a.PreviewStopping)) {
            this.L.a(a.PreviewStarting);
            g();
        } else if (this.L.b(a.PreviewRun) || this.L.b(a.PreviewStarting)) {
            return 0;
        } else {
            return -2138308608;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int c() {
        if (this.L.b(a.PreviewRun) || this.L.b(a.PreviewStarting)) {
            this.L.a(a.PreviewStopping);
            h();
            return 0;
        } else if (this.L.b(a.SessionCreating) || this.L.b(a.SessionReady) || this.L.b(a.PreviewStopping) || this.L.b(a.CameraOpening) || this.L.b(a.CameraOpened)) {
            return 0;
        } else {
            return -2138308608;
        }
    }

    /* access modifiers changed from: private */
    public int d() {
        if (this.l == null) {
            return 90;
        }
        try {
            return ((Integer) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e2) {
            return 90;
        }
    }

    private int a(int[] iArr, int i2) {
        Point point;
        Size size;
        if (this.l == null) {
            return 0;
        }
        try {
            Size[] outputSizes = ((StreamConfigurationMap) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(Utility.a(true, this.u));
            Point point2 = new Point();
            Utility.a(this.w, point2);
            if (point2.x < point2.y) {
                point = new Point(point2.y, point2.x);
            } else {
                point = point2;
            }
            Size size2 = new Size(1920, 1080);
            if (point.x < 1920 || point.y < 1080) {
                size = new Size(point.x, point.y);
            } else {
                size = size2;
            }
            ArrayList arrayList = new ArrayList(outputSizes.length);
            for (int i3 = 0; i3 < outputSizes.length; i3++) {
                Size size3 = outputSizes[i3];
                if (size3.getWidth() <= size.getWidth() && size3.getHeight() <= size.getHeight()) {
                    arrayList.add(outputSizes[i3]);
                }
            }
            return Utility.a(true, (Object) arrayList, iArr, i2);
        } catch (CameraAccessException e2) {
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public boolean e() {
        if (!f()) {
            return false;
        }
        int[] iArr = new int[32];
        int a2 = a(iArr, 32);
        ArrayList arrayList = new ArrayList(a2);
        for (int i2 = 0; i2 < a2 * 2; i2 += 2) {
            arrayList.add(new Size(iArr[i2], iArr[i2 + 1]));
        }
        this.q = (Size) Utility.a(true, (Object) arrayList, h.getWidth(), h.getHeight());
        k();
        return a(true, true, true);
    }

    private void a(int i2, boolean z2) {
        if (this.s != null) {
            this.s.close();
            this.s = null;
        }
        if (z2) {
            int[] iArr = new int[2];
            if (a(iArr) == 0) {
                this.r = new Size(iArr[0], iArr[1]);
                n();
            }
        }
        this.s = ImageReader.newInstance(this.r.getWidth(), this.r.getHeight(), i2, 2);
        this.s.setOnImageAvailableListener(this.e, this.A);
    }

    private void b(int i2) {
        if (this.t != null) {
            this.t.close();
            this.t = null;
        }
        this.t = ImageReader.newInstance(this.q.getWidth(), this.q.getHeight(), i2, 2);
        this.t.setOnImageAvailableListener(this.f, this.A);
    }

    private boolean f() {
        if (this.l == null) {
            return false;
        }
        CameraDeviceInterface.a aVar = new CameraDeviceInterface.a();
        try {
            CameraCharacteristics cameraCharacteristics = this.l.getCameraCharacteristics(this.j);
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            aVar.a = fArr[0];
            aVar.b = (float) (Math.atan((double) ((sizeF.getWidth() / 2.0f) / fArr[0])) * 2.0d);
            aVar.c = (float) (Math.atan((double) ((sizeF.getHeight() / 2.0f) / fArr[0])) * 2.0d);
            sCameraModelInfos.put(this.k, aVar);
            return true;
        } catch (CameraAccessException e2) {
            return false;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(boolean r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            r1 = 0
            r0 = 1
            java.lang.Object r2 = com.sony.smartar.a.a
            monitor-enter(r2)
            com.sony.smartar.CameraDevice20$b r3 = r7.L     // Catch:{ all -> 0x0053 }
            com.sony.smartar.CameraDevice20$a r4 = com.sony.smartar.CameraDevice20.a.SessionCreating     // Catch:{ all -> 0x0053 }
            r3.a(r4)     // Catch:{ all -> 0x0053 }
            if (r8 == 0) goto L_0x0018
            r3 = 1
            int r4 = r7.u     // Catch:{ all -> 0x0053 }
            int r3 = com.sony.smartar.Utility.a((boolean) r3, (int) r4)     // Catch:{ all -> 0x0053 }
            r7.b((int) r3)     // Catch:{ all -> 0x0053 }
        L_0x0018:
            if (r9 == 0) goto L_0x0024
            r3 = 1
            int r4 = r7.v     // Catch:{ all -> 0x0053 }
            int r3 = com.sony.smartar.Utility.a((boolean) r3, (int) r4)     // Catch:{ all -> 0x0053 }
            r7.a((int) r3, (boolean) r10)     // Catch:{ all -> 0x0053 }
        L_0x0024:
            android.hardware.camera2.CameraDevice r3 = r7.i     // Catch:{ CameraAccessException -> 0x0048 }
            r4 = 2
            android.view.Surface[] r4 = new android.view.Surface[r4]     // Catch:{ CameraAccessException -> 0x0048 }
            r5 = 0
            android.media.ImageReader r6 = r7.t     // Catch:{ CameraAccessException -> 0x0048 }
            android.view.Surface r6 = r6.getSurface()     // Catch:{ CameraAccessException -> 0x0048 }
            r4[r5] = r6     // Catch:{ CameraAccessException -> 0x0048 }
            r5 = 1
            android.media.ImageReader r6 = r7.s     // Catch:{ CameraAccessException -> 0x0048 }
            android.view.Surface r6 = r6.getSurface()     // Catch:{ CameraAccessException -> 0x0048 }
            r4[r5] = r6     // Catch:{ CameraAccessException -> 0x0048 }
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch:{ CameraAccessException -> 0x0048 }
            android.hardware.camera2.CameraCaptureSession$StateCallback r5 = r7.c     // Catch:{ CameraAccessException -> 0x0048 }
            android.os.Handler r6 = r7.y     // Catch:{ CameraAccessException -> 0x0048 }
            r3.createCaptureSession(r4, r5, r6)     // Catch:{ CameraAccessException -> 0x0048 }
            monitor-exit(r2)     // Catch:{ all -> 0x0053 }
        L_0x0047:
            return r0
        L_0x0048:
            r0 = move-exception
            com.sony.smartar.CameraDevice20$b r0 = r7.L     // Catch:{ all -> 0x0053 }
            com.sony.smartar.CameraDevice20$a r3 = com.sony.smartar.CameraDevice20.a.CameraOpened     // Catch:{ all -> 0x0053 }
            r0.a(r3)     // Catch:{ all -> 0x0053 }
            monitor-exit(r2)     // Catch:{ all -> 0x0053 }
            r0 = r1
            goto L_0x0047
        L_0x0053:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0053 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.CameraDevice20.a(boolean, boolean, boolean):boolean");
    }

    /* access modifiers changed from: private */
    public void g() {
        try {
            this.n.setRepeatingRequest(this.o.build(), this.b, this.y);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.n != null) {
            try {
                this.n.abortCaptures();
                synchronized (this.L) {
                    this.L.a(a.SessionReady);
                }
                this.B.removeCallbacks(this.g);
            } catch (CameraAccessException | IllegalStateException e2) {
            }
        }
    }

    private MeteringRectangle[] a(float[] fArr, int i2) {
        if (this.l == null) {
            return null;
        }
        try {
            Rect rect = (Rect) this.l.getCameraCharacteristics(this.j).get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            MeteringRectangle[] meteringRectangleArr = new MeteringRectangle[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                int width = rect.width();
                int height = rect.height();
                meteringRectangleArr[i3] = new MeteringRectangle(new Rect((int) (((fArr[(i3 * 5) + 0] * ((float) width)) + ((float) width)) / 2.0f), (int) (((fArr[(i3 * 5) + 1] * ((float) height)) + ((float) height)) / 2.0f), (int) ((((float) width) + (fArr[(i3 * 5) + 2] * ((float) width))) / 2.0f), (int) ((((float) height) + (fArr[(i3 * 5) + 3] * ((float) height))) / 2.0f)), (int) (fArr[(i3 * 5) + 4] * 1000.0f));
            }
            return meteringRectangleArr;
        } catch (CameraAccessException e2) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public int i() {
        try {
            if (this.L.b(a.PreviewRun) || this.L.b(a.PreviewStarting)) {
                this.n.setRepeatingRequest(this.o.build(), this.b, this.y);
            }
            return 0;
        } catch (CameraAccessException e2) {
            return -2138308603;
        }
    }

    private SizeF j() {
        Size size;
        if (this.l == null) {
            return new SizeF(0.0f, 0.0f);
        }
        try {
            CameraCharacteristics cameraCharacteristics = this.l.getCameraCharacteristics(this.j);
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            Size size2 = (Size) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
            Rect rect = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            if (((float) this.r.getWidth()) / ((float) this.r.getHeight()) > ((float) rect.width()) / ((float) rect.height())) {
                size = new Size(rect.width(), (int) ((((float) rect.width()) * ((float) this.q.getHeight())) / ((float) this.q.getWidth())));
            } else if (((float) this.r.getWidth()) / ((float) this.r.getHeight()) < ((float) rect.width()) / ((float) rect.height())) {
                size = new Size((int) ((((float) rect.height()) * ((float) this.q.getWidth())) / ((float) this.q.getHeight())), rect.height());
            } else {
                size = new Size(rect.width(), rect.height());
            }
            SizeF sizeF2 = new SizeF((sizeF.getWidth() * ((float) size.getWidth())) / ((float) size2.getWidth()), (sizeF.getHeight() * ((float) size.getHeight())) / ((float) size2.getHeight()));
            return new SizeF(((float) (Math.atan((double) (sizeF2.getWidth() / (fArr[0] * 2.0f))) * 2.0d)) * 57.295776f, ((float) (Math.atan((double) (sizeF2.getHeight() / (fArr[0] * 2.0f))) * 2.0d)) * 57.295776f);
        } catch (CameraAccessException e2) {
            return new SizeF(0.0f, 0.0f);
        }
    }

    private void k() {
        synchronized (c.b) {
            m();
            this.M = new d(2, this.q.getWidth(), this.q.getHeight(), 3, 1);
        }
    }

    private void l() {
        synchronized (c.b) {
            m();
        }
    }

    private void m() {
        if (this.M != null) {
            this.M.b();
            this.M = null;
        }
    }

    private void n() {
        synchronized (c.b) {
            p();
            this.N = new c(this.r.getWidth(), this.r.getHeight(), 1, 4);
        }
    }

    private void o() {
        synchronized (c.b) {
            p();
        }
    }

    private void p() {
        if (this.N != null) {
            this.N.a();
            this.N = null;
        }
    }

    private int q() {
        long j2 = 2500;
        long nanoTime = System.nanoTime();
        while (!this.L.b(a.SessionReady)) {
            if (!this.L.b(a.CameraOpening) && !this.L.b(a.CameraOpened) && !this.L.b(a.SessionCreating)) {
                return -2138308594;
            }
            j2 -= (System.nanoTime() - nanoTime) / 1000000;
            if (j2 <= 0) {
                return -2138308594;
            }
            try {
                synchronized (this.L) {
                    this.L.wait(j2);
                }
            } catch (InterruptedException e2) {
            }
        }
        return 0;
    }
}

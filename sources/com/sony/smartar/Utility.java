package com.sony.smartar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

@TargetApi(21)
class Utility {
    public static final int SDK_INT = Build.VERSION.SDK_INT;

    Utility() {
    }

    public static int a(boolean z, int i) {
        switch (i) {
            case 0:
                return 9;
            case 1:
                if (z) {
                    return 35;
                }
                return 17;
            case 2:
                d.b("unsupported mode: " + i);
                return -1;
            case 3:
                return 1;
            case 4:
                return 256;
            default:
                d.b("unexpected value: " + i);
                return -1;
        }
    }

    public static int a(boolean z, Object obj, int[] iArr, int i) {
        List list;
        List list2;
        int i2;
        if (obj == null) {
            return 0;
        }
        if (z) {
            list = (List) obj;
            list2 = null;
            i2 = Math.min(list.size(), i);
        } else {
            List list3 = (List) obj;
            list2 = list3;
            list = null;
            i2 = Math.min(list3.size(), i);
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (z) {
                Size size = (Size) list.get(i3);
                iArr[(i3 * 2) + 0] = size.getWidth();
                iArr[(i3 * 2) + 1] = size.getHeight();
            } else {
                Camera.Size size2 = (Camera.Size) list2.get(i3);
                iArr[(i3 * 2) + 0] = size2.width;
                iArr[(i3 * 2) + 1] = size2.height;
            }
        }
        return i2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(boolean r7, java.util.List<java.lang.Integer> r8, int[] r9, int r10) {
        /*
            r6 = 1
            r2 = 0
            if (r8 != 0) goto L_0x0006
            r1 = r2
        L_0x0005:
            return r1
        L_0x0006:
            int r4 = r8.size()
            r3 = r2
            r1 = r2
        L_0x000c:
            if (r3 >= r4) goto L_0x0005
            if (r1 >= r10) goto L_0x0005
            java.lang.Object r0 = r8.get(r3)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            switch(r0) {
                case 1: goto L_0x0036;
                case 9: goto L_0x0031;
                case 17: goto L_0x0023;
                case 35: goto L_0x002a;
                case 256: goto L_0x003c;
                default: goto L_0x001d;
            }
        L_0x001d:
            r0 = r1
        L_0x001e:
            int r1 = r3 + 1
            r3 = r1
            r1 = r0
            goto L_0x000c
        L_0x0023:
            if (r7 != 0) goto L_0x001d
            int r0 = r1 + 1
            r9[r1] = r6
            goto L_0x001e
        L_0x002a:
            if (r7 == 0) goto L_0x001d
            int r0 = r1 + 1
            r9[r1] = r6
            goto L_0x001e
        L_0x0031:
            int r0 = r1 + 1
            r9[r1] = r2
            goto L_0x001e
        L_0x0036:
            int r0 = r1 + 1
            r5 = 3
            r9[r1] = r5
            goto L_0x001e
        L_0x003c:
            int r0 = r1 + 1
            r5 = 4
            r9[r1] = r5
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sony.smartar.Utility.a(boolean, java.util.List, int[], int):int");
    }

    public static Object a(boolean z, Object obj, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        Size size;
        int i8;
        float f;
        Size size2 = null;
        float f2 = 0.0f;
        List list = (List) obj;
        int size3 = list.size();
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        while (i9 < size3) {
            Object obj2 = list.get(i9);
            if (z) {
                int width = ((Size) obj2).getWidth();
                i3 = ((Size) obj2).getHeight();
                i4 = width;
            } else {
                int i12 = ((Camera.Size) obj2).width;
                i3 = ((Camera.Size) obj2).height;
                i4 = i12;
            }
            if (j.d && i4 == 320 && i3 == 240) {
                f = f2;
                i8 = i10;
                size = size2;
                i7 = i11;
            } else {
                float abs = (((float) Math.abs(i3 - i2)) / ((float) Math.min(i3, i2))) + (((float) Math.abs(i4 - i)) / ((float) Math.min(i4, i)));
                if (size2 == null) {
                    i5 = i10;
                    i6 = i11;
                } else if (z) {
                    i6 = size2.getWidth();
                    i5 = size2.getHeight();
                } else {
                    i6 = size2.getWidth();
                    i5 = size2.getHeight();
                }
                if (size2 == null || abs < f2 || (Math.abs(abs - f2) < 1.0E-5f && i4 * i3 < i6 * i5)) {
                    i7 = i6;
                    size = (Size) obj2;
                    i8 = i5;
                    f = abs;
                } else {
                    i8 = i5;
                    f = f2;
                    size = size2;
                    i7 = i6;
                }
            }
            i9++;
            i10 = i8;
            i11 = i7;
            size2 = size;
            f2 = f;
        }
        if (size2 != null) {
            return size2;
        }
        throw new RuntimeException("no available preview size: " + list.size());
    }

    public static int isSony() {
        return Build.MANUFACTURER.toUpperCase(Locale.ENGLISH).contains("SONY") ? 1 : 0;
    }

    public static int[] isAndroidCamera2Available(Context context, boolean z) {
        int[] iArr = {0, 1, -1};
        if (SDK_INT < 21) {
            return iArr;
        }
        String[] defaultCameraId2withString = CameraDevice20.getDefaultCameraId2withString(context, z);
        try {
            CameraCharacteristics cameraCharacteristics = ((CameraManager) context.getSystemService("camera")).getCameraCharacteristics(defaultCameraId2withString[1]);
            iArr[1] = 2;
            switch (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue()) {
                case 0:
                    iArr[0] = 1;
                    iArr[2] = 0;
                    break;
                case 1:
                    iArr[0] = 1;
                    iArr[2] = 1;
                    break;
                case 3:
                    iArr[0] = 1;
                    iArr[2] = 3;
                    break;
            }
            return iArr;
        } catch (CameraAccessException e) {
            return iArr;
        } catch (Exception e2) {
            return iArr;
        }
    }

    public static void a(Context context, Point point) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(defaultDisplay, new Object[]{point});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3);
            }
        } else if (Build.VERSION.SDK_INT >= 13) {
            try {
                Method method = Display.class.getMethod("getRawWidth", new Class[0]);
                Method method2 = Display.class.getMethod("getRawHeight", new Class[0]);
                point.x = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
                point.y = ((Integer) method2.invoke(defaultDisplay, new Object[0])).intValue();
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException(e4);
            } catch (IllegalAccessException e5) {
                throw new RuntimeException(e5);
            } catch (InvocationTargetException e6) {
                throw new RuntimeException(e6);
            }
        } else {
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
    }
}

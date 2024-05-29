package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Class fontFamilyClass;
        Constructor fontFamilyCtor;
        Method addFontMethod;
        Method addFromBufferMethod;
        Method freezeMethod;
        Method abortCreationMethod;
        Method createFromFamiliesWithDefaultMethod;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
            fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
            addFontMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
            addFromBufferMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
            freezeMethod = fontFamilyClass.getMethod(FREEZE_METHOD, new Class[0]);
            abortCreationMethod = fontFamilyClass.getMethod(ABORT_CREATION_METHOD, new Class[0]);
            createFromFamiliesWithDefaultMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{Array.newInstance(fontFamilyClass, 1).getClass(), Integer.TYPE, Integer.TYPE});
            createFromFamiliesWithDefaultMethod.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
        }
        sFontFamilyCtor = fontFamilyCtor;
        sFontFamily = fontFamilyClass;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = addFromBufferMethod;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromAssetManager.invoke(family, new Object[]{context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromBuffer.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke((Object) null, new Object[]{familyArray, -1, -1});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean freeze(Object family) {
        try {
            return ((Boolean) sFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean abortCreation(Object family) {
        try {
            return ((Boolean) sAbortCreation.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        int i;
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entry.getEntries()) {
            String fileName = fontFile.getFileName();
            int weight = fontFile.getWeight();
            if (fontFile.isItalic()) {
                i = 1;
            } else {
                i = 0;
            }
            if (!addFontFromAssetManager(context, fontFamily, fileName, 0, weight, i)) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (!freeze(fontFamily)) {
            return null;
        }
        return createFromFamiliesWithDefault(fontFamily);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
        r18 = r14;
        r14 = r13;
        r13 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d5, code lost:
        r13 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r20, @android.support.annotation.Nullable android.os.CancellationSignal r21, @android.support.annotation.NonNull android.support.v4.provider.FontsContractCompat.FontInfo[] r22, int r23) {
        /*
            r19 = this;
            r0 = r22
            int r13 = r0.length
            r14 = 1
            if (r13 >= r14) goto L_0x0008
            r13 = 0
        L_0x0007:
            return r13
        L_0x0008:
            boolean r13 = isFontFamilyPrivateAPIAvailable()
            if (r13 != 0) goto L_0x0073
            r0 = r19
            r1 = r22
            r2 = r23
            android.support.v4.provider.FontsContractCompat$FontInfo r4 = r0.findBestInfo(r1, r2)
            android.content.ContentResolver r10 = r20.getContentResolver()
            android.net.Uri r13 = r4.getUri()     // Catch:{ IOException -> 0x0053 }
            java.lang.String r14 = "r"
            r0 = r21
            android.os.ParcelFileDescriptor r9 = r10.openFileDescriptor(r13, r14, r0)     // Catch:{ IOException -> 0x0053 }
            r14 = 0
            android.graphics.Typeface$Builder r13 = new android.graphics.Typeface$Builder     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            java.io.FileDescriptor r15 = r9.getFileDescriptor()     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            r13.<init>(r15)     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            int r15 = r4.getWeight()     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            android.graphics.Typeface$Builder r13 = r13.setWeight(r15)     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            boolean r15 = r4.isItalic()     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            android.graphics.Typeface$Builder r13 = r13.setItalic(r15)     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            android.graphics.Typeface r13 = r13.build()     // Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
            if (r9 == 0) goto L_0x0007
            if (r14 == 0) goto L_0x0056
            r9.close()     // Catch:{ Throwable -> 0x004e }
            goto L_0x0007
        L_0x004e:
            r15 = move-exception
            r14.addSuppressed(r15)     // Catch:{ IOException -> 0x0053 }
            goto L_0x0007
        L_0x0053:
            r5 = move-exception
            r13 = 0
            goto L_0x0007
        L_0x0056:
            r9.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0007
        L_0x005a:
            r13 = move-exception
            throw r13     // Catch:{ all -> 0x005c }
        L_0x005c:
            r14 = move-exception
            r18 = r14
            r14 = r13
            r13 = r18
        L_0x0062:
            if (r9 == 0) goto L_0x0069
            if (r14 == 0) goto L_0x006f
            r9.close()     // Catch:{ Throwable -> 0x006a }
        L_0x0069:
            throw r13     // Catch:{ IOException -> 0x0053 }
        L_0x006a:
            r15 = move-exception
            r14.addSuppressed(r15)     // Catch:{ IOException -> 0x0053 }
            goto L_0x0069
        L_0x006f:
            r9.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0069
        L_0x0073:
            r0 = r20
            r1 = r22
            r2 = r21
            java.util.Map r12 = android.support.v4.provider.FontsContractCompat.prepareFontData(r0, r1, r2)
            java.lang.Object r8 = newFamily()
            r3 = 0
            r0 = r22
            int r15 = r0.length
            r13 = 0
            r14 = r13
        L_0x0087:
            if (r14 >= r15) goto L_0x00be
            r6 = r22[r14]
            android.net.Uri r13 = r6.getUri()
            java.lang.Object r7 = r12.get(r13)
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            if (r7 != 0) goto L_0x009b
        L_0x0097:
            int r13 = r14 + 1
            r14 = r13
            goto L_0x0087
        L_0x009b:
            int r16 = r6.getTtcIndex()
            int r17 = r6.getWeight()
            boolean r13 = r6.isItalic()
            if (r13 == 0) goto L_0x00ba
            r13 = 1
        L_0x00aa:
            r0 = r16
            r1 = r17
            boolean r11 = addFontFromBuffer(r8, r7, r0, r1, r13)
            if (r11 != 0) goto L_0x00bc
            abortCreation(r8)
            r13 = 0
            goto L_0x0007
        L_0x00ba:
            r13 = 0
            goto L_0x00aa
        L_0x00bc:
            r3 = 1
            goto L_0x0097
        L_0x00be:
            if (r3 != 0) goto L_0x00c6
            abortCreation(r8)
            r13 = 0
            goto L_0x0007
        L_0x00c6:
            boolean r13 = freeze(r8)
            if (r13 != 0) goto L_0x00cf
            r13 = 0
            goto L_0x0007
        L_0x00cf:
            android.graphics.Typeface r13 = createFromFamiliesWithDefault(r8)
            goto L_0x0007
        L_0x00d5:
            r13 = move-exception
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1)) {
            abortCreation(fontFamily);
            return null;
        } else if (!freeze(fontFamily)) {
            return null;
        } else {
            return createFromFamiliesWithDefault(fontFamily);
        }
    }
}

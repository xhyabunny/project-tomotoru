package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

public final class MultiDex {
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final String SECONDARY_FOLDER_NAME = ("code_cache" + File.separator + OLD_SECONDARY_FOLDER_NAME);
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<String> installedApk = new HashSet();

    private MultiDex() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e9, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ea, code lost:
        android.util.Log.w(TAG, "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x010f, code lost:
        android.util.Log.i(TAG, "install done");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void install(android.content.Context r11) {
        /*
            r9 = 20
            r10 = 4
            java.lang.String r7 = "MultiDex"
            java.lang.String r8 = "install"
            android.util.Log.i(r7, r8)
            boolean r7 = IS_VM_MULTIDEX_CAPABLE
            if (r7 == 0) goto L_0x0016
            java.lang.String r7 = "MultiDex"
            java.lang.String r8 = "VM has multidex support, MultiDex support library is disabled."
            android.util.Log.i(r7, r8)
        L_0x0015:
            return
        L_0x0016:
            int r7 = android.os.Build.VERSION.SDK_INT
            if (r7 >= r10) goto L_0x0045
            java.lang.RuntimeException r7 = new java.lang.RuntimeException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Multi dex installation failed. SDK "
            java.lang.StringBuilder r8 = r8.append(r9)
            int r9 = android.os.Build.VERSION.SDK_INT
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = " is unsupported. Min SDK version is "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.String r9 = "."
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x0045:
            android.content.pm.ApplicationInfo r1 = getApplicationInfo(r11)     // Catch:{ Exception -> 0x005d }
            if (r1 == 0) goto L_0x0015
            java.util.Set<java.lang.String> r8 = installedApk     // Catch:{ Exception -> 0x005d }
            monitor-enter(r8)     // Catch:{ Exception -> 0x005d }
            java.lang.String r0 = r1.sourceDir     // Catch:{ all -> 0x005a }
            java.util.Set<java.lang.String> r7 = installedApk     // Catch:{ all -> 0x005a }
            boolean r7 = r7.contains(r0)     // Catch:{ all -> 0x005a }
            if (r7 == 0) goto L_0x0088
            monitor-exit(r8)     // Catch:{ all -> 0x005a }
            goto L_0x0015
        L_0x005a:
            r7 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x005a }
            throw r7     // Catch:{ Exception -> 0x005d }
        L_0x005d:
            r3 = move-exception
            java.lang.String r7 = "MultiDex"
            java.lang.String r8 = "Multidex installation failure"
            android.util.Log.e(r7, r8, r3)
            java.lang.RuntimeException r7 = new java.lang.RuntimeException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Multi dex installation failed ("
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r3.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = ")."
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x0088:
            java.util.Set<java.lang.String> r7 = installedApk     // Catch:{ all -> 0x005a }
            r7.add(r0)     // Catch:{ all -> 0x005a }
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x005a }
            if (r7 <= r9) goto L_0x00d9
            java.lang.String r7 = "MultiDex"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x005a }
            r9.<init>()     // Catch:{ all -> 0x005a }
            java.lang.String r10 = "MultiDex is not guaranteed to work in SDK version "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x005a }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = ": SDK version higher than "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            r10 = 20
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = " should be backed by "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = "runtime with built-in multidex capabilty but it's not the "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = "case here: java.vm.version=\""
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = "java.vm.version"
            java.lang.String r10 = java.lang.System.getProperty(r10)     // Catch:{ all -> 0x005a }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r10 = "\""
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x005a }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x005a }
            android.util.Log.w(r7, r9)     // Catch:{ all -> 0x005a }
        L_0x00d9:
            java.lang.ClassLoader r5 = r11.getClassLoader()     // Catch:{ RuntimeException -> 0x00e9 }
            if (r5 != 0) goto L_0x00f4
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Context class loader is null. Must be running in test mode. Skip patching."
            android.util.Log.e(r7, r9)     // Catch:{ all -> 0x005a }
            monitor-exit(r8)     // Catch:{ all -> 0x005a }
            goto L_0x0015
        L_0x00e9:
            r3 = move-exception
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching."
            android.util.Log.w(r7, r9, r3)     // Catch:{ all -> 0x005a }
            monitor-exit(r8)     // Catch:{ all -> 0x005a }
            goto L_0x0015
        L_0x00f4:
            clearOldDexDir(r11)     // Catch:{ Throwable -> 0x0118 }
        L_0x00f7:
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x005a }
            java.lang.String r7 = r1.dataDir     // Catch:{ all -> 0x005a }
            java.lang.String r9 = SECONDARY_FOLDER_NAME     // Catch:{ all -> 0x005a }
            r2.<init>(r7, r9)     // Catch:{ all -> 0x005a }
            r7 = 0
            java.util.List r4 = android.support.multidex.MultiDexExtractor.load(r11, r1, r2, r7)     // Catch:{ all -> 0x005a }
            boolean r7 = checkValidZipFiles(r4)     // Catch:{ all -> 0x005a }
            if (r7 == 0) goto L_0x0121
            installSecondaryDexes(r5, r2, r4)     // Catch:{ all -> 0x005a }
        L_0x010e:
            monitor-exit(r8)     // Catch:{ all -> 0x005a }
            java.lang.String r7 = "MultiDex"
            java.lang.String r8 = "install done"
            android.util.Log.i(r7, r8)
            goto L_0x0015
        L_0x0118:
            r6 = move-exception
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning."
            android.util.Log.w(r7, r9, r6)     // Catch:{ all -> 0x005a }
            goto L_0x00f7
        L_0x0121:
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Files were not valid zip files.  Forcing a reload."
            android.util.Log.w(r7, r9)     // Catch:{ all -> 0x005a }
            r7 = 1
            java.util.List r4 = android.support.multidex.MultiDexExtractor.load(r11, r1, r2, r7)     // Catch:{ all -> 0x005a }
            boolean r7 = checkValidZipFiles(r4)     // Catch:{ all -> 0x005a }
            if (r7 == 0) goto L_0x0137
            installSecondaryDexes(r5, r2, r4)     // Catch:{ all -> 0x005a }
            goto L_0x010e
        L_0x0137:
            java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ all -> 0x005a }
            java.lang.String r9 = "Zip files were not valid."
            r7.<init>(r9)     // Catch:{ all -> 0x005a }
            throw r7     // Catch:{ all -> 0x005a }
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDex.install(android.content.Context):void");
    }

    private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            if (pm == null || packageName == null) {
                return null;
            }
            return pm.getApplicationInfo(packageName, 128);
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    isMultidexCapable = major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i(TAG, "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
        if (files.isEmpty()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            V19.install(loader, files, dexDir);
        } else if (Build.VERSION.SDK_INT >= 14) {
            V14.install(loader, files, dexDir);
        } else {
            V4.install(loader, files);
        }
    }

    private static boolean checkValidZipFiles(List<File> files) {
        for (File file : files) {
            if (!MultiDexExtractor.verifyZipFile(file)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Method method = cls.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File dexDir = new File(context.getFilesDir(), OLD_SECONDARY_FOLDER_NAME);
        if (dexDir.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
            File[] files = dexDir.listFiles();
            if (files == null) {
                Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
                return;
            }
            for (File oldFile : files) {
                Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
                if (!oldFile.delete()) {
                    Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                } else {
                    Log.i(TAG, "Deleted old file " + oldFile.getPath());
                }
            }
            if (!dexDir.delete()) {
                Log.w(TAG, "Failed to delete secondary dex dir " + dexDir.getPath());
            } else {
                Log.i(TAG, "Deleted old secondary dex dir " + dexDir.getPath());
            }
        }
    }

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator i$ = suppressedExceptions.iterator();
                while (i$.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", i$.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(loader, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(loader);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions2.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(loader, dexElementsSuppressedExceptions);
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V14 {
        private V14() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
        }
    }

    private static final class V4 {
        private V4() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, "path");
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }
}

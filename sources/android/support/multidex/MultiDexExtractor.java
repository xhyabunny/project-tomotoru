package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    private static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private static Method sApplyMethod;

    MultiDexExtractor() {
    }

    static List<File> load(Context context, ApplicationInfo applicationInfo, File dexDir, boolean forceReload) throws IOException {
        List<File> files;
        Log.i(TAG, "MultiDexExtractor.load(" + applicationInfo.sourceDir + ", " + forceReload + ")");
        File sourceApk = new File(applicationInfo.sourceDir);
        long currentCrc = getZipCrc(sourceApk);
        if (forceReload || isModified(context, sourceApk, currentCrc)) {
            Log.i(TAG, "Detected that extraction must be performed.");
            files = performExtractions(sourceApk, dexDir);
            putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
        } else {
            try {
                files = loadExistingExtractions(context, sourceApk, dexDir);
            } catch (IOException ioe) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                files = performExtractions(sourceApk, dexDir);
                putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);
            }
        }
        Log.i(TAG, "load found " + files.size() + " secondary dex files");
        return files;
    }

    private static List<File> loadExistingExtractions(Context context, File sourceApk, File dexDir) throws IOException {
        Log.i(TAG, "loading existing secondary dex files");
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);
        List<File> files = new ArrayList<>(totalDexNumber);
        int secondaryNumber = 2;
        while (secondaryNumber <= totalDexNumber) {
            File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
            if (extractedFile.isFile()) {
                files.add(extractedFile);
                if (!verifyZipFile(extractedFile)) {
                    Log.i(TAG, "Invalid zip file: " + extractedFile);
                    throw new IOException("Invalid ZIP file.");
                }
                secondaryNumber++;
            } else {
                throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
            }
        }
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        return (prefs.getLong(KEY_TIME_STAMP, -1) == getTimeStamp(archive) && prefs.getLong(KEY_CRC, -1) == currentCrc) ? false : true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private static List<File> performExtractions(File sourceApk, File dexDir) throws IOException {
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;
        prepareDexDir(dexDir, extractedFilePrefix);
        List<File> files = new ArrayList<>();
        ZipFile apk = new ZipFile(sourceApk);
        int secondaryNumber = 2;
        try {
            ZipEntry dexFile = apk.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX);
            while (dexFile != null) {
                File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
                files.add(extractedFile);
                Log.i(TAG, "Extraction is needed for file " + extractedFile);
                int numAttempts = 0;
                boolean isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    numAttempts++;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    isExtractionSuccessful = verifyZipFile(extractedFile);
                    Log.i(TAG, "Extraction " + (isExtractionSuccessful ? "success" : "failed") + " - length " + extractedFile.getAbsolutePath() + ": " + extractedFile.length());
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                        }
                    }
                }
                if (!isExtractionSuccessful) {
                    throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + ")");
                }
                secondaryNumber++;
                dexFile = apk.getEntry(DEX_PREFIX + secondaryNumber + DEX_SUFFIX);
            }
            try {
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
            return files;
        } finally {
            try {
                apk.close();
            } catch (IOException e2) {
                Log.w(TAG, "Failed to close resource", e2);
            }
        }
    }

    private static void putStoredApkInfo(Context context, long timeStamp, long crc, int totalDexNumber) {
        SharedPreferences.Editor edit = getMultiDexPreferences(context).edit();
        edit.putLong(KEY_TIME_STAMP, timeStamp);
        edit.putLong(KEY_CRC, crc);
        edit.putInt(KEY_DEX_NUMBER, totalDexNumber);
        apply(edit);
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private static void prepareDexDir(File dexDir, final String extractedFilePrefix) throws IOException {
        mkdirChecked(dexDir.getParentFile());
        mkdirChecked(dexDir);
        File[] files = dexDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith(extractedFilePrefix);
            }
        });
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
    }

    private static void mkdirChecked(File dir) throws IOException {
        dir.mkdir();
        if (!dir.isDirectory()) {
            File parent = dir.getParentFile();
            if (parent == null) {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". Parent file is null.");
            } else {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". parent file is a dir " + parent.isDirectory() + ", a file " + parent.isFile() + ", exists " + parent.exists() + ", readable " + parent.canRead() + ", writable " + parent.canWrite());
            }
            throw new IOException("Failed to create cache directory " + dir.getPath());
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {
        InputStream in = apk.getInputStream(dexFile);
        File tmp = File.createTempFile(extractedFilePrefix, EXTRACTED_SUFFIX, extractTo.getParentFile());
        Log.i(TAG, "Extracting " + tmp.getPath());
        try {
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            try {
                ZipEntry classesDex = new ZipEntry("classes.dex");
                classesDex.setTime(dexFile.getTime());
                out.putNextEntry(classesDex);
                byte[] buffer = new byte[16384];
                for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                    out.write(buffer, 0, length);
                }
                out.closeEntry();
                out.close();
                Log.i(TAG, "Renaming to " + extractTo.getPath());
                if (!tmp.renameTo(extractTo)) {
                    throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
                }
                closeQuietly(in);
                tmp.delete();
            } catch (Throwable th) {
                th = th;
                ZipOutputStream zipOutputStream = out;
                closeQuietly(in);
                tmp.delete();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(in);
            tmp.delete();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0029, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
        android.util.Log.w(TAG, "File " + r6.getAbsolutePath() + " is not a valid zip file.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        android.util.Log.w(TAG, "Failed to close zip file: " + r6.getAbsolutePath());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0029 A[ExcHandler: ZipException (r1v1 'ex' java.util.zip.ZipException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean verifyZipFile(java.io.File r6) {
        /*
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            r2.<init>(r6)     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            r2.close()     // Catch:{ IOException -> 0x000a, ZipException -> 0x0029 }
            r3 = 1
        L_0x0009:
            return r3
        L_0x000a:
            r0 = move-exception
            java.lang.String r3 = "MultiDex"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            r4.<init>()     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            java.lang.String r5 = "Failed to close zip file: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            java.lang.String r5 = r6.getAbsolutePath()     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            java.lang.String r4 = r4.toString()     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
            android.util.Log.w(r3, r4)     // Catch:{ ZipException -> 0x0029, IOException -> 0x004d }
        L_0x0027:
            r3 = 0
            goto L_0x0009
        L_0x0029:
            r1 = move-exception
            java.lang.String r3 = "MultiDex"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "File "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r6.getAbsolutePath()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = " is not a valid zip file."
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4, r1)
            goto L_0x0027
        L_0x004d:
            r1 = move-exception
            java.lang.String r3 = "MultiDex"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Got an IOException trying to open zip file: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r6.getAbsolutePath()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4, r1)
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDexExtractor.verifyZipFile(java.io.File):boolean");
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }

    static {
        try {
            sApplyMethod = SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
        } catch (NoSuchMethodException e) {
            sApplyMethod = null;
        }
    }

    private static void apply(SharedPreferences.Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor, new Object[0]);
                return;
            } catch (IllegalAccessException | InvocationTargetException e) {
            }
        }
        editor.commit();
    }
}

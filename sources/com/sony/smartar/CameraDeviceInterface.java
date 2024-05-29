package com.sony.smartar;

import android.content.Context;
import android.util.SparseArray;
import android.view.SurfaceView;

interface CameraDeviceInterface {
    public static final int ANDROID_CAMERA_API_LEVEL_1 = 1;
    public static final int ANDROID_CAMERA_API_LEVEL_2 = 2;
    public static final int ANDROID_CAMERA_SUPPORTED_HARDWARE_FULL = 1;
    public static final int ANDROID_CAMERA_SUPPORTED_HARDWARE_INVALID = -1;
    public static final int ANDROID_CAMERA_SUPPORTED_HARDWARE_LEGACY = 2;
    public static final int ANDROID_CAMERA_SUPPORTED_HARDWARE_LEVEL_3 = 3;
    public static final int ANDROID_CAMERA_SUPPORTED_HARDWARE_LIMITED = 0;
    public static final int AUTO_ADJUST_DURATION = 3000;
    public static final int AUTO_ADJUST_DURATION_RETRY = 100;
    public static final int EXPOSURE_MODE_CONTINUOUS_AUTO = 1;
    public static final int EXPOSURE_MODE_MANUAL = 0;
    public static final int FLASH_MODE_AUTO = 0;
    public static final int FLASH_MODE_OFF = 1;
    public static final int FLASH_MODE_ON = 2;
    public static final int FLASH_MODE_RED_EYE = 3;
    public static final int FLASH_MODE_TORCH = 4;
    public static final int FOCUS_MODE_CONTINUOUS_AUTO_PICTURE = 1;
    public static final int FOCUS_MODE_CONTINUOUS_AUTO_VIDEO = 2;
    public static final int FOCUS_MODE_EDOF = 3;
    public static final int FOCUS_MODE_FIXED = 4;
    public static final int FOCUS_MODE_INFINITY = 5;
    public static final int FOCUS_MODE_MACRO = 6;
    public static final int FOCUS_MODE_MANUAL = 0;
    public static final int INVALID_CAMERA_ID = -1;
    public static final int MESSAGE_AUTO_EXPOSURE = 1;
    public static final int MESSAGE_AUTO_WHITE_BALANCE = 2;
    public static final int SCENE_MODE_ACTION = 0;
    public static final int SCENE_MODE_AUTO = 1;
    public static final int SCENE_MODE_BARCODE = 2;
    public static final int SCENE_MODE_BEACH = 3;
    public static final int SCENE_MODE_CANDLELIGHT = 4;
    public static final int SCENE_MODE_FIREWORKS = 5;
    public static final int SCENE_MODE_LANDSCAPE = 6;
    public static final int SCENE_MODE_NIGHT = 7;
    public static final int SCENE_MODE_NIGHT_PORTRAIT = 8;
    public static final int SCENE_MODE_PARTY = 9;
    public static final int SCENE_MODE_PORTRAIT = 10;
    public static final int SCENE_MODE_SNOW = 11;
    public static final int SCENE_MODE_SPORTS = 12;
    public static final int SCENE_MODE_STEADYPHOTO = 13;
    public static final int SCENE_MODE_SUNSET = 14;
    public static final int SCENE_MODE_THEATRE = 15;
    public static final int WHITE_BALANCE_MODE_CLOUDY_DAYLIGHT = 1;
    public static final int WHITE_BALANCE_MODE_CONTINUOUS_AUTO = 0;
    public static final int WHITE_BALANCE_MODE_DAYLIGHT = 2;
    public static final int WHITE_BALANCE_MODE_FLUORESCENT = 3;
    public static final int WHITE_BALANCE_MODE_INCANDESCENT = 4;
    public static final int WHITE_BALANCE_MODE_MANUAL = 8;
    public static final int WHITE_BALANCE_MODE_SHADE = 5;
    public static final int WHITE_BALANCE_MODE_TWILIGHT = 6;
    public static final int WHITE_BALANCE_MODE_WARM_FLUORESCENT = 7;
    public static final SparseArray<a> sCameraModelInfos = new SparseArray<>();

    public static final class a {
        public float a;
        public float b;
        public float c;
    }

    int captureStillImage();

    int[] getCameraAPIFeature();

    float[] getDeviceInfo();

    int getFacing();

    float getFocalLength();

    float getFovY();

    int getImageSensorRotation();

    int getMaxNumExposureAreas();

    int getMaxNumFocusAreas();

    int getMaxStillImageSize(int[] iArr);

    Object getNativeDevice();

    int getRotation();

    int getStillImageSize(int[] iArr);

    int getSupportedExposureMode(int[] iArr, int i);

    int getSupportedFlashMode(int[] iArr, int i);

    int getSupportedFocusMode(int[] iArr, int i);

    int getSupportedSceneMode(int[] iArr, int i);

    int getSupportedStillImageFormat(int[] iArr, int i);

    int getSupportedStillImageSize(int[] iArr, int i);

    int getSupportedVideoImageFormat(int[] iArr, int i);

    int getSupportedVideoImageFpsRange(int[] iArr, int i);

    int getSupportedVideoImageSize(int[] iArr, int i);

    int getSupportedWhiteBalanceMode(int[] iArr, int i);

    int getVideoImageSize(int[] iArr);

    int open(Context context, long j, int i, Object obj, boolean z);

    void release();

    int runAutoExposure();

    int runAutoFocus();

    int runAutoWhiteBalance();

    int setExposureAreas(float[] fArr, int i);

    int setExposureMode(int i);

    int setFlashMode(int i);

    int setFocusAreas(float[] fArr, int i);

    int setFocusMode(int i);

    int setNativeVideoOutput(SurfaceView surfaceView);

    int setSceneMode(int i);

    int setStillImageFormat(int i);

    int setStillImageSize(int i, int i2);

    int setVideoImageFormat(int i);

    int setVideoImageFpsRange(int i, int i2);

    int setVideoImageSize(int i, int i2);

    int setWhiteBalanceMode(int i);

    int start();

    int stop();
}

package com.cyberstep.sanriocharacters;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import net.ypresto.qtfaststart.QtFastStart;

public class VideoWriter {
    static final int BIT_RATE = 3000000;
    public static final int FORMAT_TYPE_I420 = 3;
    public static final int FORMAT_TYPE_NV12 = 4;
    public static final int FORMAT_TYPE_RGB888 = 2;
    public static final int FORMAT_TYPE_RGBA8888 = 1;
    public static final int FORMAT_TYPE_SURFACE = 5;
    static final int IFRAME_INTERVAL = 1;
    static final String MIME_TYPE = "video/avc";
    ByteBuffer[] buffer;
    String cachename;
    MediaCodec codec;
    Context context;
    boolean drawable;
    String filename;
    MediaFormat format;
    int frame_cnt = 0;
    int height;
    CodecInputSurface input;
    boolean is_end;
    boolean is_recording;
    boolean is_update = false;
    Object lock;
    MediaMuxer mux;
    int num_frames = 30;
    MediaRecorder rec;
    int trackidx;
    int width;

    public VideoWriter() {
        try {
            this.codec = MediaCodec.createEncoderByType(MIME_TYPE);
        } catch (IOException e) {
            Log.e("SAKANA", "IOException_Codec_createEncoderByType.");
        }
        this.trackidx = -1;
        this.is_recording = false;
        this.height = 0;
        this.width = 0;
        this.lock = new Object();
        synchronized (this.lock) {
            this.is_end = false;
        }
    }

    public int getCodecCapabilities() {
        return 2130708361;
    }

    public void init(String s, int width2, int height2, float fps, Context cont) {
        this.num_frames = (int) fps;
        Log.d("SAKANA", "videoWriter frame width:" + width2 + " height:" + height2);
        this.width = width2;
        this.height = height2;
        this.filename = s.trim();
        this.cachename = cont.getCacheDir() + "/cache.mp4";
        this.context = cont;
        getCodecCapabilities();
        this.format = MediaFormat.createVideoFormat(MIME_TYPE, this.width, this.height);
        this.format.setInteger("color-format", getSupportedColorFormat());
        this.format.setInteger("bitrate", BIT_RATE);
        this.format.setInteger("frame-rate", (int) fps);
        this.format.setInteger("i-frame-interval", 1);
        this.codec.configure(this.format, (Surface) null, (MediaCrypto) null, 1);
        if (Build.VERSION.SDK_INT >= 21) {
            this.codec.setCallback(new MediaCodec.Callback() {
                public void onInputBufferAvailable(MediaCodec var1, int var2) {
                }

                @TargetApi(21)
                public void onOutputBufferAvailable(MediaCodec var1, int var2, MediaCodec.BufferInfo var3) {
                    ByteBuffer buf = VideoWriter.this.codec.getOutputBuffer(var2);
                    if (buf == null) {
                        throw new RuntimeException("process failed. encode result:" + var2);
                    }
                    if ((var3.flags & 2) != 0) {
                        var3.size = 0;
                    }
                    if (var3.size > 0) {
                        if (VideoWriter.this.mux != null) {
                            if (!VideoWriter.this.is_recording) {
                                throw new RuntimeException("muxer hasn't started");
                            }
                            buf.position(var3.offset);
                            buf.limit(var3.offset + var3.size);
                            VideoWriter.this.mux.writeSampleData(VideoWriter.this.trackidx, buf, var3);
                        } else {
                            return;
                        }
                    }
                    VideoWriter.this.codec.releaseOutputBuffer(var2, true);
                    if ((var3.flags & 4) != 0) {
                        Log.d("SAKANA", "record video stopped.");
                        synchronized (VideoWriter.this.lock) {
                            VideoWriter.this.is_end = true;
                            VideoWriter.this.is_recording = false;
                            if (VideoWriter.this.mux != null) {
                                VideoWriter.this.mux.stop();
                            }
                            if (VideoWriter.this.codec != null) {
                                VideoWriter.this.codec.stop();
                            }
                        }
                        File f = new File(VideoWriter.this.cachename);
                        File f2 = new File(VideoWriter.this.filename);
                        try {
                            QtFastStart.sDEBUG = true;
                            QtFastStart.fastStart(f, f2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        f.delete();
                    }
                }

                public void onError(MediaCodec var1, MediaCodec.CodecException var2) {
                    throw new RuntimeException("MediaCodec Callback error:" + var2.toString());
                }

                public void onOutputFormatChanged(MediaCodec var1, MediaFormat var2) {
                    if (VideoWriter.this.mux != null) {
                        if (VideoWriter.this.is_recording) {
                            throw new RuntimeException("format changed twice.");
                        }
                        VideoWriter.this.trackidx = VideoWriter.this.mux.addTrack(var2);
                        VideoWriter.this.mux.start();
                        VideoWriter.this.is_recording = true;
                    }
                }
            });
        }
        try {
            this.input = new CodecInputSurface(this.codec.createInputSurface(), this.width, this.height);
            this.mux = new MediaMuxer(this.cachename, 0);
            this.frame_cnt = 0;
            this.is_end = false;
        } catch (Exception e) {
            this.mux = null;
            throw new RuntimeException("IOException_MediaMuxer_instance.");
        }
    }

    public int getSupportedColorFormat() {
        return 2130708361;
    }

    public void start() {
        Log.d("SAKANA", "VideoWriter start.");
        this.codec.start();
        this.drawable = true;
    }

    public boolean end() {
        if (!this.is_recording) {
            return false;
        }
        this.codec.signalEndOfInputStream();
        this.drawable = false;
        return true;
    }

    public void dispose() {
        boolean b;
        Log.d("Sakana", "dispose wait start.");
        long start = System.currentTimeMillis();
        long j = start;
        while (true) {
            synchronized (this.lock) {
                b = this.is_end;
            }
            long cur = System.currentTimeMillis();
            if (b && cur - start >= 500) {
                Log.d("Sakana", "dispose wait end. remove start." + this.is_end);
                synchronized (this.lock) {
                    if (this.codec != null) {
                        this.codec.release();
                        this.codec = null;
                    }
                    if (this.input != null) {
                        this.input.release();
                        this.input = null;
                    }
                    if (this.mux != null) {
                        this.mux.release();
                        this.mux = null;
                    }
                }
                return;
            }
        }
        while (true) {
        }
    }

    public boolean isEnd() {
        boolean b;
        synchronized (this.lock) {
            b = this.is_end;
        }
        return b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        if (r4.drawable == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        r4.input.makeCurrent();
        r4.input.render(r5);
        r4.input.setPresentationTime(java.lang.System.nanoTime());
        r4.input.swapBuffers();
        r4.input.OldCurrent();
        r4.frame_cnt++;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawFrame(int r5) {
        /*
            r4 = this;
            java.lang.Object r1 = r4.lock
            monitor-enter(r1)
            boolean r0 = r4.is_end     // Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
        L_0x0008:
            return
        L_0x0009:
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            boolean r0 = r4.drawable
            if (r0 == 0) goto L_0x0008
            com.cyberstep.sanriocharacters.VideoWriter$CodecInputSurface r0 = r4.input
            r0.makeCurrent()
            com.cyberstep.sanriocharacters.VideoWriter$CodecInputSurface r0 = r4.input
            r0.render(r5)
            com.cyberstep.sanriocharacters.VideoWriter$CodecInputSurface r0 = r4.input
            long r2 = java.lang.System.nanoTime()
            r0.setPresentationTime(r2)
            com.cyberstep.sanriocharacters.VideoWriter$CodecInputSurface r0 = r4.input
            r0.swapBuffers()
            com.cyberstep.sanriocharacters.VideoWriter$CodecInputSurface r0 = r4.input
            r0.OldCurrent()
            int r0 = r4.frame_cnt
            int r0 = r0 + 1
            r4.frame_cnt = r0
            goto L_0x0008
        L_0x0032:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cyberstep.sanriocharacters.VideoWriter.drawFrame(int):void");
    }

    private long computePresentationTimeUsec(int frameIndex) {
        return ((long) (frameIndex % this.num_frames)) * (1000000000 / ((long) this.num_frames));
    }

    public boolean process(int textureid) {
        boolean z = true;
        try {
            if (this.codec != null && Build.VERSION.SDK_INT < 21) {
                if (!this.is_recording) {
                    this.buffer = this.codec.getOutputBuffers();
                }
                MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                int result = this.codec.dequeueOutputBuffer(info, 1000);
                if (result != -1) {
                    if (result == -3) {
                        this.buffer = this.codec.getOutputBuffers();
                    } else if (result == -2) {
                        if (this.is_recording) {
                            throw new RuntimeException("format changed twice.");
                        }
                        this.trackidx = this.mux.addTrack(this.codec.getOutputFormat());
                        this.mux.start();
                        this.is_recording = true;
                    } else if (result >= 0) {
                        ByteBuffer buf = this.buffer[result];
                        if (buf == null) {
                            throw new RuntimeException("process failed. encode result:" + result);
                        }
                        if ((info.flags & 2) != 0) {
                            info.size = 0;
                        }
                        if (info.size > 0) {
                            if (!this.is_recording) {
                                throw new RuntimeException("muxer hasn't started");
                            }
                            buf.position(info.offset);
                            buf.limit(info.offset + info.size);
                            this.mux.writeSampleData(this.trackidx, buf, info);
                        }
                        this.codec.releaseOutputBuffer(result, true);
                    }
                }
                if ((info.flags & 4) != 0) {
                    synchronized (this.lock) {
                        this.mux.stop();
                        this.codec.stop();
                        this.is_end = true;
                        this.is_recording = false;
                    }
                    File f = new File(this.cachename);
                    File f2 = new File(this.filename);
                    try {
                        QtFastStart.sDEBUG = true;
                        if (!QtFastStart.fastStart(f, f2)) {
                            FileChannel in = new FileInputStream(f).getChannel();
                            in.transferTo(0, in.size(), new FileOutputStream(f2).getChannel());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    f.delete();
                    return z;
                }
            }
            drawFrame(textureid);
            synchronized (this.lock) {
                z = this.is_end;
            }
            return z;
        } catch (Exception e2) {
            synchronized (this.lock) {
                this.is_end = false;
                this.is_recording = false;
                throw e2;
            }
        }
    }

    private static class CodecInputSurface {
        private static final int EGL_OPENGL_ES2_BIT = 4;
        private static final int EGL_OPENGL_ES3_BIT = 64;
        private static final int EGL_RECORDABLE_ANDROID = 12610;
        private static final String FRAGMENT_SHADER = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D uTexture;\nvoid main() {\n  gl_FragColor = texture2D( uTexture, vTextureCoord);\n}\n";
        private static final String VERTEX_SHADER = "attribute vec2 aTextureCoord;\nattribute vec4 aPosition;\nuniform mat4 uMVPMatrix;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = aTextureCoord;\n}\n";
        float bottom;
        short[] draworder;
        int height;
        float left;
        private EGLDisplay mEGLDisplay;
        private EGLContext mEGLInputContext;
        private EGLSurface mEGLInputSurface;
        private EGLContext mEGLOldContext;
        private EGLSurface mEGLOldSurface;
        float[] mMVPMatrix;
        private int mProgram;
        private Surface mSurface;
        ShortBuffer mTriangleIndexes;
        FloatBuffer mTriangleVertices;
        private int maColorHandle;
        private int maPositionHandle;
        private int maTextureHandle;
        private int muMVPMatrixHandle;
        private int muSTMatrixHandle;
        private int muTextureHandle;
        float right;
        float top;
        float uv_bottom;
        float uv_left;
        float uv_right;
        float uv_top;
        float[] vertexes;
        int width;

        private CodecInputSurface(Surface surface, int width2, int height2) {
            this.mEGLDisplay = EGL14.EGL_NO_DISPLAY;
            this.mEGLInputContext = EGL14.EGL_NO_CONTEXT;
            this.mEGLInputSurface = EGL14.EGL_NO_SURFACE;
            this.mEGLOldContext = EGL14.EGL_NO_CONTEXT;
            this.mEGLOldSurface = EGL14.EGL_NO_SURFACE;
            this.mProgram = 0;
            this.mMVPMatrix = new float[16];
            this.width = 0;
            this.height = 0;
            this.left = -1.0f;
            this.top = -1.0f;
            this.right = 1.0f;
            this.bottom = 1.0f;
            this.uv_left = 0.0f;
            this.uv_right = 1.0f;
            this.uv_top = 1.0f;
            this.uv_bottom = 0.0f;
            this.vertexes = new float[]{this.left, this.top, 0.0f, this.uv_left, this.uv_top, 1.0f, 0.0f, 0.0f, 1.0f, this.right, this.top, 0.0f, this.uv_right, this.uv_top, 0.0f, 0.0f, 1.0f, 1.0f, this.left, this.bottom, 0.0f, this.uv_left, this.uv_bottom, 0.0f, 1.0f, 0.0f, 1.0f, this.right, this.bottom, 0.0f, this.uv_right, this.uv_bottom, 1.0f, 1.0f, 1.0f, 1.0f};
            this.draworder = new short[]{0, 1, 2, 1, 3, 2};
            if (surface == null) {
                throw new NullPointerException();
            }
            this.mSurface = surface;
            eglSetup(width2, height2);
        }

        private EGLConfig[] createContext(int esbit, int esver) {
            int[] inputList = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, esbit, EGL_RECORDABLE_ANDROID, 1, 12344};
            EGLConfig[] configs = new EGLConfig[1];
            EGL14.eglChooseConfig(this.mEGLDisplay, inputList, 0, configs, 0, configs.length, new int[1], 0);
            VideoWriter.checkEglError("eglCreateContext RGB888+recordable ES2");
            this.mEGLInputContext = EGL14.eglCreateContext(this.mEGLDisplay, configs[0], this.mEGLOldContext, new int[]{12440, esver, 12344}, 0);
            VideoWriter.checkEglError("eglCreateContext");
            return configs;
        }

        private void eglSetup(int width2, int height2) {
            EGLConfig[] configs;
            this.mEGLDisplay = EGL14.eglGetDisplay(0);
            this.mEGLOldContext = EGL14.eglGetCurrentContext();
            this.mEGLOldSurface = EGL14.eglGetCurrentSurface(12377);
            int[] version = new int[2];
            if (!EGL14.eglInitialize(this.mEGLDisplay, version, 0, version, 1)) {
                throw new RuntimeException("unable to initialize EGL14");
            }
            int[] inputAttribs = {12344};
            try {
                configs = createContext(64, 3);
            } catch (Exception e) {
                configs = createContext(4, 2);
            }
            this.mEGLInputSurface = EGL14.eglCreateWindowSurface(this.mEGLDisplay, configs[0], this.mSurface, inputAttribs, 0);
            EGL14.eglQuerySurface(this.mEGLDisplay, this.mEGLInputSurface, 12375, new int[]{width2}, 0);
            EGL14.eglQuerySurface(this.mEGLDisplay, this.mEGLInputSurface, 12374, new int[]{height2}, 0);
            VideoWriter.checkEglError("eglQuerySurface");
            makeCurrent();
            this.mProgram = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
            if (this.mProgram == 0) {
                Log.e("SAKANA", "Create shader program failed......");
                return;
            }
            this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
            VideoWriter.checkGLError("glGetAttribLocation aPosition");
            this.maTextureHandle = GLES20.glGetAttribLocation(this.mProgram, "aTextureCoord");
            VideoWriter.checkGLError("glGetAttribLocation aTextureCoord");
            this.maColorHandle = GLES20.glGetAttribLocation(this.mProgram, "aColor");
            VideoWriter.checkGLError("glGetUniformLocation aColor");
            this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
            VideoWriter.checkGLError("glGetAttribLocation uMVPMatrix");
            this.muSTMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
            VideoWriter.checkGLError("glGetUniformLocation uSTMatrix");
            this.muTextureHandle = GLES20.glGetUniformLocation(this.mProgram, "uTexture");
            VideoWriter.checkGLError("glGetUniformLocation uTexture");
            this.width = width2;
            this.height = height2;
            VideoWriter.checkGLError("glViewport");
            int[] iArr = new int[1];
            VideoWriter.checkGLError("glBindTexture");
            this.mTriangleVertices = ByteBuffer.allocateDirect(this.vertexes.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.mTriangleVertices.put(this.vertexes).position(0);
            this.mTriangleIndexes = ByteBuffer.allocateDirect(this.draworder.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
            this.mTriangleIndexes.put(this.draworder).position(0);
            OldCurrent();
        }

        public void release() {
            OldCurrent();
            this.mSurface.release();
            this.mEGLDisplay = EGL14.EGL_NO_DISPLAY;
            EGLContext eGLContext = EGL14.EGL_NO_CONTEXT;
            this.mEGLInputContext = eGLContext;
            this.mEGLOldContext = eGLContext;
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            this.mEGLInputSurface = eGLSurface;
            this.mEGLOldSurface = eGLSurface;
            this.mSurface = null;
        }

        public void makeCurrent() {
            EGL14.eglMakeCurrent(this.mEGLDisplay, this.mEGLInputSurface, this.mEGLInputSurface, this.mEGLInputContext);
            VideoWriter.checkEglError("eglMakeCurrent");
        }

        public void OldCurrent() {
            EGL14.eglMakeCurrent(this.mEGLDisplay, this.mEGLOldSurface, this.mEGLOldSurface, this.mEGLOldContext);
            VideoWriter.checkEglError("eglMakeCurrent");
        }

        public void render(int textureid) {
            GLES20.glViewport(0, 0, this.width, this.height);
            VideoWriter.checkGLError("glViewport");
            GLES20.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
            GLES20.glClear(16640);
            GLES20.glDisable(3042);
            VideoWriter.checkGLError("glClear");
            if (this.mProgram == 0) {
                throw new RuntimeException("mProgram read failed.");
            }
            GLES20.glUseProgram(this.mProgram);
            VideoWriter.checkGLError("glUseProgram");
            GLES20.glActiveTexture(33984);
            VideoWriter.checkGLError("glActiveTexture");
            GLES20.glBindTexture(3553, textureid);
            VideoWriter.checkGLError("glbindTexture");
            this.mTriangleVertices.position(0);
            GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 36, this.mTriangleVertices);
            VideoWriter.checkGLError("glVertexAttribPointer maPositionHandle");
            GLES20.glEnableVertexAttribArray(this.maPositionHandle);
            VideoWriter.checkGLError("glEnableVertexAttribArray maPositionHandle");
            this.mTriangleVertices.position(3);
            GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 36, this.mTriangleVertices);
            VideoWriter.checkGLError("glVertexAttribPointer maTextureHandle");
            GLES20.glEnableVertexAttribArray(this.maTextureHandle);
            VideoWriter.checkGLError("glEnableVertexAttribArray maTextureHandle");
            Matrix.setIdentityM(this.mMVPMatrix, 0);
            GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, this.mMVPMatrix, 0);
            GLES20.glUniform1i(this.muTextureHandle, 0);
            GLES20.glDrawElements(4, this.draworder.length, 5123, this.mTriangleIndexes);
            VideoWriter.checkGLError("glDrawArrays");
            GLES20.glFinish();
            GLES20.glEnable(3042);
            GLES20.glUseProgram(0);
        }

        public boolean swapBuffers() {
            boolean result = EGL14.eglSwapBuffers(this.mEGLDisplay, this.mEGLInputSurface);
            VideoWriter.checkEglError("eglSwapBuffers");
            return result;
        }

        public void setPresentationTime(long nsecs) {
            EGLExt.eglPresentationTimeANDROID(this.mEGLDisplay, this.mEGLInputSurface, nsecs);
            VideoWriter.checkEglError("eglPresentationTimeANDROID");
        }

        private int loadShader(int shaderType, String source) {
            int shader = GLES20.glCreateShader(shaderType);
            VideoWriter.checkGLError("glCreateShader type=" + shaderType);
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            int[] logLength = new int[1];
            GLES20.glGetShaderiv(shader, 35716, logLength, 0);
            if (logLength[0] > 0) {
                Log.e("GLRenderer", "Shader compile log:" + GLES20.glGetShaderInfoLog(shader));
            }
            GLES20.glGetShaderiv(shader, 35713, compiled, 0);
            if (compiled[0] != 0) {
                return shader;
            }
            Log.e("SAKANA", "Could not compile shader " + shaderType + ":");
            Log.e("SAKANA", " " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            return 0;
        }

        private int createProgram(String vertexSource, String fragmentSource) {
            int vertexShader = loadShader(35633, vertexSource);
            if (vertexShader == 0) {
                return 0;
            }
            int pixelShader = loadShader(35632, fragmentSource);
            if (pixelShader == 0) {
                return 0;
            }
            int program = GLES20.glCreateProgram();
            VideoWriter.checkGLError("glCreateProgram");
            if (program == 0) {
                Log.e("SAKANA", "Could not create program");
            }
            GLES20.glAttachShader(program, vertexShader);
            VideoWriter.checkGLError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            VideoWriter.checkGLError("glAttachShader");
            GLES20.glLinkProgram(program);
            VideoWriter.checkGLError("glLinkProgram");
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
            VideoWriter.checkGLError("glGetProgramiv");
            if (linkStatus[0] == 1) {
                return program;
            }
            Log.e("SAKANA", "Could not link program: ");
            Log.e("SAKANA", GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public static void checkEglError(String msg) {
        int error = EGL14.eglGetError();
        if (error != 12288) {
            throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
        }
    }

    /* access modifiers changed from: private */
    public static void checkGLError(String msg) {
        int error = GLES20.glGetError();
        if (error != 0) {
            Log.e("SAKANA", msg + ": glError " + error);
            throw new RuntimeException(msg + " glError" + error);
        }
    }
}

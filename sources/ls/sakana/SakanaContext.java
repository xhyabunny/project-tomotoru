package ls.sakana;

import android.util.Log;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class SakanaContext {
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final int EGL_OPENGL_ES3_BIT = 64;
    EGLConfig config;
    EGLContext context;
    EGLDisplay display;
    EGL10 egl;
    int esver = 0;
    EGLSurface surface;
    SakanaView view;

    public void createContext() {
        this.egl = (EGL10) EGLContext.getEGL();
        this.display = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.display == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay failed");
        }
        if (!this.egl.eglInitialize(this.display, new int[2])) {
            throw new RuntimeException("eglInitialize failed");
        }
        try {
            createContextES(64, 3);
        } catch (Exception e) {
            createContextES(4, 2);
        }
        this.surface = null;
    }

    public void createSurface(SakanaView view2, SurfaceHolder holder) {
        if (this.egl == null) {
            throw new RuntimeException("egl not initialized");
        } else if (this.display == null) {
            throw new RuntimeException("eglDisplay not initialized");
        } else if (this.config == null) {
            throw new RuntimeException("mEglConfig not initialized");
        } else {
            if (!(this.surface == null || this.surface == EGL10.EGL_NO_SURFACE)) {
                this.egl.eglMakeCurrent(this.display, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.egl.eglDestroySurface(this.display, this.surface);
            }
            this.surface = this.egl.eglCreateWindowSurface(this.display, this.config, holder, (int[]) null);
            if (this.surface == null || this.surface == EGL10.EGL_NO_SURFACE) {
                if (this.egl.eglGetError() == 12299) {
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
            } else if (!this.egl.eglMakeCurrent(this.display, this.surface, this.surface, this.context)) {
                throw new RuntimeException("eglMakeCurrent error : " + this.egl.eglGetError());
            }
        }
    }

    private void createContextES(int esbit, int esver2) {
        int[] configspec = {12352, esbit, 12344};
        int[] num_config = new int[1];
        if (!this.egl.eglChooseConfig(this.display, configspec, (EGLConfig[]) null, 0, num_config)) {
            throw new IllegalArgumentException("eglChooseConfig failed");
        }
        int numConfigs = num_config[0];
        if (numConfigs <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        EGLConfig[] configs = new EGLConfig[numConfigs];
        if (!this.egl.eglChooseConfig(this.display, configspec, configs, numConfigs, num_config)) {
            throw new IllegalArgumentException("eglChooseConfig#2 failed");
        }
        this.config = null;
        int[] val = new int[1];
        int l = 0;
        while (true) {
            if (l >= configs.length) {
                break;
            }
            this.egl.eglGetConfigAttrib(this.display, configs[l], 12325, val);
            if (val[0] > 0) {
                this.config = configs[l];
                break;
            }
            l++;
        }
        if (this.config == null) {
            throw new IllegalArgumentException("No config chosen");
        }
        this.context = this.egl.eglCreateContext(this.display, this.config, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, esver2, 12344});
        if (this.context == null || this.context == EGL10.EGL_NO_CONTEXT) {
            this.context = null;
            throw new RuntimeException("createContext error : " + this.egl.eglGetError());
        } else {
            this.esver = esver2;
        }
    }

    public void destroySurface() {
        if (this.surface != null && this.surface != EGL10.EGL_NO_SURFACE) {
            this.egl.eglMakeCurrent(this.display, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.egl.eglDestroySurface(this.display, this.surface);
            this.surface = null;
        }
    }

    public void finish() {
        if (this.context != null) {
            if (!this.egl.eglDestroyContext(this.display, this.context)) {
                Log.e("DefaultContextFactory", "display:" + this.display + " context: " + this.context);
                throw new RuntimeException("eglDestroyContext failed: " + this.egl.eglGetError());
            }
            this.context = null;
        }
        if (this.display != null) {
            this.egl.eglTerminate(this.display);
            this.display = null;
        }
    }

    public boolean swap() {
        if (this.surface == null) {
            return false;
        }
        if (!this.egl.eglSwapBuffers(this.display, this.surface)) {
            int error = this.egl.eglGetError();
            switch (error) {
                case 12299:
                    Log.e("EglHelper", "eglSwapBuffers returned EGL_BAD_NATIVE_WINDOW. tid=" + Thread.currentThread().getId());
                    break;
                case 12301:
                    Log.e("EglHelper", "eglSwapBuffers returned EGL_BAD_SURFACE. tid=" + Thread.currentThread().getId());
                    break;
                case 12302:
                    return false;
                default:
                    throw new RuntimeException("eglSwapBuffers error : " + error);
            }
        }
        return true;
    }
}

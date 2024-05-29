package com.appsflyer;

import android.os.AsyncTask;
import android.os.Build;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AFExecutor {

    /* renamed from: ˋ  reason: contains not printable characters */
    private static AFExecutor f3;

    /* renamed from: ˊ  reason: contains not printable characters */
    private ScheduledExecutorService f4;

    /* renamed from: ˎ  reason: contains not printable characters */
    private Executor f5;

    /* renamed from: ˏ  reason: contains not printable characters */
    private Executor f6;

    private AFExecutor() {
    }

    public static AFExecutor getInstance() {
        if (f3 == null) {
            f3 = new AFExecutor();
        }
        return f3;
    }

    public Executor getSerialExecutor() {
        if (this.f6 == null) {
            if (Build.VERSION.SDK_INT < 11) {
                return Executors.newSingleThreadExecutor();
            }
            this.f6 = AsyncTask.SERIAL_EXECUTOR;
        }
        return this.f6;
    }

    public Executor getThreadPoolExecutor() {
        if (this.f5 == null || ((this.f5 instanceof ThreadPoolExecutor) && (((ThreadPoolExecutor) this.f5).isShutdown() || ((ThreadPoolExecutor) this.f5).isTerminated() || ((ThreadPoolExecutor) this.f5).isTerminating()))) {
            if (Build.VERSION.SDK_INT < 11) {
                return Executors.newSingleThreadExecutor();
            }
            this.f5 = Executors.newFixedThreadPool(2);
        }
        return this.f5;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final ScheduledThreadPoolExecutor m2() {
        if (this.f4 == null || this.f4.isShutdown() || this.f4.isTerminated()) {
            this.f4 = Executors.newScheduledThreadPool(2);
        }
        return (ScheduledThreadPoolExecutor) this.f4;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m1() {
        try {
            m0(this.f4);
            if (this.f5 instanceof ThreadPoolExecutor) {
                m0((ThreadPoolExecutor) this.f5);
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog("failed to stop Executors", th);
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private static void m0(ExecutorService executorService) {
        try {
            AFLogger.afRDLog("shut downing executor ...");
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
        } catch (InterruptedException e) {
            AFLogger.afRDLog("InterruptedException!!!");
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
        } catch (Throwable th) {
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
            throw th;
        }
    }
}

package com.appsflyer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

final class g {
    g() {
    }

    static final class d {

        /* renamed from: ˊ  reason: contains not printable characters */
        private final String f130;

        /* renamed from: ˎ  reason: contains not printable characters */
        private final boolean f131;

        d(String str, boolean z) {
            this.f130 = str;
            this.f131 = z;
        }

        /* renamed from: ˊ  reason: contains not printable characters */
        public final String m134() {
            return this.f130;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ॱ  reason: contains not printable characters */
        public final boolean m135() {
            return this.f131;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    static d m130(Context context) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            b bVar = new b((byte) 0);
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (context.bindService(intent, bVar, 1)) {
                    c cVar = new c(bVar.m131());
                    d dVar = new d(cVar.m132(), cVar.m133());
                    if (context != null) {
                        context.unbindService(bVar);
                    }
                    return dVar;
                }
                if (context != null) {
                    context.unbindService(bVar);
                }
                throw new IOException("Google Play connection failed");
            } catch (Exception e) {
                throw e;
            } catch (Throwable th) {
                if (context != null) {
                    context.unbindService(bVar);
                }
                throw th;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    static final class b implements ServiceConnection {

        /* renamed from: ˊ  reason: contains not printable characters */
        private boolean f127;

        /* renamed from: ˏ  reason: contains not printable characters */
        private final LinkedBlockingQueue<IBinder> f128;

        private b() {
            this.f127 = false;
            this.f128 = new LinkedBlockingQueue<>(1);
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f128.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        public final IBinder m131() throws InterruptedException {
            if (this.f127) {
                throw new IllegalStateException();
            }
            this.f127 = true;
            return this.f128.take();
        }
    }

    static final class c implements IInterface {

        /* renamed from: ˎ  reason: contains not printable characters */
        private IBinder f129;

        c(IBinder iBinder) {
            this.f129 = iBinder;
        }

        public final IBinder asBinder() {
            return this.f129;
        }

        /* renamed from: ˋ  reason: contains not printable characters */
        public final String m132() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f129.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ˎ  reason: contains not printable characters */
        public final boolean m133() throws RemoteException {
            boolean z = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(1);
                this.f129.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z = false;
                }
                return z;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }
}

package com.sony.smartar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* compiled from: GuFileUtil */
final class e {
    /* JADX INFO: finally extract failed */
    public static byte[] a(InputStream inputStream, int i) throws IOException {
        int i2;
        if (i == -1) {
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            loop0:
            while (true) {
                try {
                    int max = Math.max(4096, inputStream.available());
                    byte[] bArr = new byte[max];
                    arrayList.add(bArr);
                    i2 = i3;
                    int i4 = 0;
                    while (i4 < max) {
                        int read = inputStream.read(bArr, i4, max - i4);
                        if (read == -1) {
                            break loop0;
                        }
                        i4 += read;
                        i2 = read + i2;
                    }
                    i3 = i2;
                } catch (Throwable th) {
                    inputStream.close();
                    throw th;
                }
            }
            inputStream.close();
            byte[] bArr2 = new byte[i2];
            int size = arrayList.size();
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                byte[] bArr3 = (byte[]) arrayList.get(i6);
                System.arraycopy(bArr3, 0, bArr2, i5, Math.min(bArr3.length, i2 - i5));
                i5 += bArr3.length;
            }
            return bArr2;
        }
        byte[] bArr4 = new byte[i];
        int i7 = 0;
        while (i7 < i) {
            try {
                i7 += inputStream.read(bArr4, i7, i - i7);
            } catch (Throwable th2) {
                inputStream.close();
                throw th2;
            }
        }
        inputStream.close();
        return bArr4;
    }
}

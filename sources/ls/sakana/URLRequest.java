package ls.sakana;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;

public class URLRequest implements Runnable {
    public int contentlength;
    public ByteBuffer data;
    int flags;
    FileOutputStream fos;
    public String header;
    HttpURLConnection http;
    InputStream is;
    public long lastmodified;
    int lockcount;
    int method;
    File outfile;
    byte[] post;
    public int procmode;
    String[] proplist;
    public int readsize;
    public int status;
    Thread thread;
    String url;

    public void startWithParameters(String _url, int _flags, int _method, String[] _proplist, byte[] _post, String _tofilepath) {
        if (!lock()) {
            this.procmode = -1;
            this.status = -1;
            return;
        }
        this.url = _url;
        this.flags = _flags;
        this.method = _method;
        this.proplist = _proplist;
        this.post = _post;
        if (_tofilepath != null) {
            this.outfile = new File(_tofilepath);
        }
        unlock();
        start();
    }

    public void start() {
        if (!lock()) {
            this.procmode = -1;
            this.status = -1;
            return;
        }
        this.procmode = 1;
        this.thread = new Thread(this, "URLResutst Thread[" + this.url + "]");
        this.thread.start();
        unlock();
    }

    public void stop() {
        if (!lock()) {
            this.procmode = -1;
            this.status = -1;
            return;
        }
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
        unlock();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061 A[Catch:{ IOException -> 0x0047 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a6 A[SYNTHETIC, Splitter:B:26:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x001d A[Catch:{ IOException -> 0x0047 }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0027 A[Catch:{ IOException -> 0x0047 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r12 = 4
            r11 = 2
            r10 = -1
            java.net.URL r6 = new java.net.URL     // Catch:{ IOException -> 0x0047 }
            java.lang.String r7 = r13.url     // Catch:{ IOException -> 0x0047 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0047 }
            java.net.URLConnection r6 = r6.openConnection()     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch:{ IOException -> 0x0047 }
            r13.http = r6     // Catch:{ IOException -> 0x0047 }
            int r6 = r13.method     // Catch:{ IOException -> 0x0047 }
            switch(r6) {
                case 0: goto L_0x003f;
                case 1: goto L_0x004d;
                case 2: goto L_0x0055;
                default: goto L_0x0017;
            }     // Catch:{ IOException -> 0x0047 }
        L_0x0017:
            int r6 = r13.flags     // Catch:{ IOException -> 0x0047 }
            r6 = r6 & 1
            if (r6 == 0) goto L_0x0023
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            r7 = 0
            r6.setUseCaches(r7)     // Catch:{ IOException -> 0x0047 }
        L_0x0023:
            java.lang.String[] r6 = r13.proplist     // Catch:{ IOException -> 0x0047 }
            if (r6 == 0) goto L_0x005d
            r3 = 0
        L_0x0028:
            java.lang.String[] r6 = r13.proplist     // Catch:{ IOException -> 0x0047 }
            int r6 = r6.length     // Catch:{ IOException -> 0x0047 }
            if (r3 >= r6) goto L_0x005d
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.lang.String[] r7 = r13.proplist     // Catch:{ IOException -> 0x0047 }
            r7 = r7[r3]     // Catch:{ IOException -> 0x0047 }
            java.lang.String[] r8 = r13.proplist     // Catch:{ IOException -> 0x0047 }
            int r9 = r3 + 1
            r8 = r8[r9]     // Catch:{ IOException -> 0x0047 }
            r6.addRequestProperty(r7, r8)     // Catch:{ IOException -> 0x0047 }
            int r3 = r3 + 2
            goto L_0x0028
        L_0x003f:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.lang.String r7 = "GET"
            r6.setRequestMethod(r7)     // Catch:{ IOException -> 0x0047 }
            goto L_0x0017
        L_0x0047:
            r2 = move-exception
            r13.status = r10
            r13.procmode = r10
        L_0x004c:
            return
        L_0x004d:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.lang.String r7 = "PUT"
            r6.setRequestMethod(r7)     // Catch:{ IOException -> 0x0047 }
            goto L_0x0017
        L_0x0055:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.lang.String r7 = "HEADER"
            r6.setRequestMethod(r7)     // Catch:{ IOException -> 0x0047 }
            goto L_0x0017
        L_0x005d:
            byte[] r6 = r13.post     // Catch:{ IOException -> 0x0047 }
            if (r6 == 0) goto L_0x00a6
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            r7 = 1
            r6.setDoOutput(r7)     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            r6.connect()     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.io.OutputStream r4 = r6.getOutputStream()     // Catch:{ IOException -> 0x0047 }
            byte[] r6 = r13.post     // Catch:{ IOException -> 0x0047 }
            r4.write(r6)     // Catch:{ IOException -> 0x0047 }
        L_0x0077:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            java.util.Map r6 = r6.getHeaderFields()     // Catch:{ IOException -> 0x0047 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0047 }
            r13.header = r6     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            int r6 = r6.getResponseCode()     // Catch:{ IOException -> 0x0047 }
            r13.status = r6     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            long r6 = r6.getDate()     // Catch:{ IOException -> 0x0047 }
            r13.lastmodified = r6     // Catch:{ IOException -> 0x0047 }
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            int r6 = r6.getContentLength()     // Catch:{ IOException -> 0x0047 }
            r13.contentlength = r6     // Catch:{ IOException -> 0x0047 }
            boolean r6 = r13.lock()
            if (r6 != 0) goto L_0x00ac
            r13.status = r10
            r13.procmode = r10
            goto L_0x004c
        L_0x00a6:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ IOException -> 0x0047 }
            r6.connect()     // Catch:{ IOException -> 0x0047 }
            goto L_0x0077
        L_0x00ac:
            int r6 = r13.method
            if (r6 != r11) goto L_0x00b6
            r13.procmode = r12
            r13.unlock()
            goto L_0x004c
        L_0x00b6:
            r13.procmode = r11
            r13.unlock()
            r6 = 32768(0x8000, float:4.5918E-41)
            byte[] r0 = new byte[r6]
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ Exception -> 0x0107 }
            java.io.InputStream r6 = r6.getInputStream()     // Catch:{ Exception -> 0x0107 }
            r13.is = r6     // Catch:{ Exception -> 0x0107 }
            java.io.File r6 = r13.outfile     // Catch:{ Exception -> 0x0107 }
            if (r6 == 0) goto L_0x00fe
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0107 }
            java.io.File r7 = r13.outfile     // Catch:{ Exception -> 0x0107 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0107 }
            r13.fos = r6     // Catch:{ Exception -> 0x0107 }
        L_0x00d5:
            java.io.InputStream r6 = r13.is     // Catch:{ Exception -> 0x0107 }
            int r5 = r6.read(r0)     // Catch:{ Exception -> 0x0107 }
            if (r5 > 0) goto L_0x010e
        L_0x00dd:
            java.io.FileOutputStream r6 = r13.fos     // Catch:{ Exception -> 0x0107 }
            if (r6 == 0) goto L_0x00e6
            java.io.FileOutputStream r6 = r13.fos     // Catch:{ Exception -> 0x0107 }
            r6.close()     // Catch:{ Exception -> 0x0107 }
        L_0x00e6:
            java.net.HttpURLConnection r6 = r13.http     // Catch:{ Exception -> 0x0107 }
            int r6 = r6.getResponseCode()     // Catch:{ Exception -> 0x0107 }
            r13.status = r6     // Catch:{ Exception -> 0x0107 }
            r6 = 3
            r13.procmode = r6     // Catch:{ Exception -> 0x0107 }
            boolean r6 = r13.lock()
            if (r6 == 0) goto L_0x0142
            r13.procmode = r12
            r13.unlock()
            goto L_0x004c
        L_0x00fe:
            int r6 = r13.contentlength     // Catch:{ Exception -> 0x0107 }
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.allocateDirect(r6)     // Catch:{ Exception -> 0x0107 }
            r13.data = r6     // Catch:{ Exception -> 0x0107 }
            goto L_0x00d5
        L_0x0107:
            r1 = move-exception
            r13.status = r10
            r13.procmode = r10
            goto L_0x004c
        L_0x010e:
            boolean r6 = r13.lock()     // Catch:{ Exception -> 0x0107 }
            if (r6 != 0) goto L_0x011b
            r6 = -1
            r13.procmode = r6     // Catch:{ Exception -> 0x0107 }
            r6 = -1
            r13.status = r6     // Catch:{ Exception -> 0x0107 }
            goto L_0x00dd
        L_0x011b:
            java.io.FileOutputStream r6 = r13.fos     // Catch:{ Exception -> 0x0107 }
            if (r6 == 0) goto L_0x013b
            java.io.FileOutputStream r6 = r13.fos     // Catch:{ Exception -> 0x0107 }
            r7 = 0
            r6.write(r0, r7, r5)     // Catch:{ Exception -> 0x0107 }
        L_0x0125:
            int r6 = r13.readsize     // Catch:{ Exception -> 0x0107 }
            int r6 = r6 + r5
            r13.readsize = r6     // Catch:{ Exception -> 0x0107 }
            int r6 = r13.readsize     // Catch:{ Exception -> 0x0107 }
            int r7 = r13.contentlength     // Catch:{ Exception -> 0x0107 }
            if (r6 <= r7) goto L_0x0137
            int r6 = r13.contentlength     // Catch:{ Exception -> 0x0107 }
            int r7 = r13.readsize     // Catch:{ Exception -> 0x0107 }
            int r6 = r6 + r7
            r13.contentlength = r6     // Catch:{ Exception -> 0x0107 }
        L_0x0137:
            r13.unlock()     // Catch:{ Exception -> 0x0107 }
            goto L_0x00d5
        L_0x013b:
            java.nio.ByteBuffer r6 = r13.data     // Catch:{ Exception -> 0x0107 }
            r7 = 0
            r6.put(r0, r7, r5)     // Catch:{ Exception -> 0x0107 }
            goto L_0x0125
        L_0x0142:
            r13.procmode = r10
            r13.status = r10
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: ls.sakana.URLRequest.run():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000a, code lost:
        r1 = false;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean lock() {
        /*
            r2 = this;
            monitor-enter(r2)
        L_0x0001:
            int r1 = r2.lockcount     // Catch:{ InterruptedException -> 0x0009, all -> 0x000f }
            if (r1 <= 0) goto L_0x000d
            r2.wait()     // Catch:{ InterruptedException -> 0x0009, all -> 0x000f }
            goto L_0x0001
        L_0x0009:
            r0 = move-exception
            r1 = 0
        L_0x000b:
            monitor-exit(r2)
            return r1
        L_0x000d:
            r1 = 1
            goto L_0x000b
        L_0x000f:
            r1 = move-exception
            monitor-exit(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: ls.sakana.URLRequest.lock():boolean");
    }

    public synchronized void unlock() {
        int i = this.lockcount - 1;
        this.lockcount = i;
        if (i <= 0) {
            notifyAll();
        }
    }
}

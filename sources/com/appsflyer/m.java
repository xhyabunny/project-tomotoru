package com.appsflyer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.appsflyer.j;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;

final class m extends AsyncTask<String, Void, String> {

    /* renamed from: ʻ  reason: contains not printable characters */
    private boolean f174;

    /* renamed from: ʼ  reason: contains not printable characters */
    private boolean f175;

    /* renamed from: ʽ  reason: contains not printable characters */
    private WeakReference<Context> f176;

    /* renamed from: ˊ  reason: contains not printable characters */
    private boolean f177 = false;

    /* renamed from: ˋ  reason: contains not printable characters */
    private boolean f178 = false;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f179 = "";

    /* renamed from: ˏ  reason: contains not printable characters */
    Map<String, String> f180;

    /* renamed from: ॱ  reason: contains not printable characters */
    String f181;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private URL f182;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private HttpURLConnection f183;

    m(Context context, boolean z) {
        this.f176 = new WeakReference<>(context);
        this.f174 = true;
        this.f175 = true;
        this.f178 = z;
    }

    /* access modifiers changed from: protected */
    public final void onPreExecute() {
        if (this.f181 == null) {
            this.f181 = new JSONObject(this.f180).toString();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final String doInBackground(String... strArr) {
        if (this.f178) {
            return null;
        }
        try {
            this.f182 = new URL(strArr[0]);
            if (this.f174) {
                C0001r.m185().m198(this.f182.toString(), this.f181);
                int length = this.f181.getBytes("UTF-8").length;
                j.AnonymousClass2.m152(new StringBuilder("call = ").append(this.f182).append("; size = ").append(length).append(" byte").append(length > 1 ? "s" : "").append("; body = ").append(this.f181).toString());
            }
            this.f183 = (HttpURLConnection) this.f182.openConnection();
            this.f183.setReadTimeout(30000);
            this.f183.setConnectTimeout(30000);
            this.f183.setRequestMethod("POST");
            this.f183.setDoInput(true);
            this.f183.setDoOutput(true);
            this.f183.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = this.f183.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(this.f181);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            this.f183.connect();
            int responseCode = this.f183.getResponseCode();
            if (this.f175) {
                AppsFlyerLib.getInstance();
                this.f179 = AppsFlyerLib.m31(this.f183);
            }
            if (this.f174) {
                C0001r.m185().m195(this.f182.toString(), responseCode, this.f179);
            }
            if (responseCode == 200) {
                AFLogger.afInfoLog("Status 200 ok");
                Context context = this.f176.get();
                if (this.f182.toString().startsWith(ServerConfigHandler.getUrl(AppsFlyerLib.f20)) && context != null) {
                    SharedPreferences.Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
                    edit.putBoolean("sentRegisterRequestToAF", true);
                    edit.apply();
                    AFLogger.afDebugLog("Successfully registered for Uninstall Tracking");
                }
            } else {
                this.f177 = true;
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog(new StringBuilder("Error while calling ").append(this.f182.toString()).toString(), th);
            this.f177 = true;
        }
        return this.f179;
    }

    /* access modifiers changed from: protected */
    public final void onCancelled() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void onPostExecute(String str) {
        if (this.f177) {
            AFLogger.afInfoLog("Connection error: ".concat(String.valueOf(str)));
        } else {
            AFLogger.afInfoLog("Connection call succeeded: ".concat(String.valueOf(str)));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m159() {
        this.f174 = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final HttpURLConnection m160() {
        return this.f183;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m157() {
        this.f175 = false;
    }
}

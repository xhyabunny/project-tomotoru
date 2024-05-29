package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.appsflyer.share.Constants;
import com.appsflyer.share.LinkGenerator;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateOneLinkHttpTask extends OneLinkHttpTask {

    /* renamed from: ʻ  reason: contains not printable characters */
    private boolean f89 = false;

    /* renamed from: ʽ  reason: contains not printable characters */
    private Context f90;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f91 = "";

    /* renamed from: ˋ  reason: contains not printable characters */
    private Map<String, String> f92;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f93;

    /* renamed from: ˏ  reason: contains not printable characters */
    private ResponseListener f94;

    public interface ResponseListener {
        @WorkerThread
        void onResponse(String str);

        @WorkerThread
        void onResponseError(String str);
    }

    public CreateOneLinkHttpTask(@NonNull String str, @NonNull Map<String, String> map, AppsFlyerLib appsFlyerLib, @NonNull Context context, boolean z) {
        super(appsFlyerLib);
        this.f89 = z;
        this.f90 = context;
        if (this.f90 != null) {
            this.f91 = context.getPackageName();
        } else {
            AFLogger.afWarnLog("CreateOneLinkHttpTask: context can't be null");
        }
        this.f97 = str;
        this.f93 = "-1";
        this.f92 = map;
    }

    public void setListener(@NonNull ResponseListener responseListener) {
        this.f94 = responseListener;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m88(HttpsURLConnection httpsURLConnection) throws JSONException, IOException {
        if (!this.f89) {
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setUseCaches(false);
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject(this.f92);
            jSONObject.put("ttl", this.f93);
            jSONObject.put("data", jSONObject2);
            httpsURLConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
            dataOutputStream.writeBytes(jSONObject.toString());
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final String m90() {
        return new StringBuilder().append(ServerConfigHandler.getUrl("https://onelink.%s/shortlink-sdk/v1")).append(Constants.URL_PATH_DELIMITER).append(this.f97).toString();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m91(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                this.f94.onResponse(jSONObject.optString(keys.next()));
            }
        } catch (JSONException e) {
            this.f94.onResponseError("Can't parse one link data");
            AFLogger.afErrorLog("Error while parsing to json ".concat(String.valueOf(str)), e);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m89() {
        LinkGenerator addParameters = new LinkGenerator(Constants.USER_INVITE_LINK_TYPE).setBaseURL(this.f97, AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_DOMAIN), this.f91).addParameter(Constants.URL_SITE_ID, this.f91).addParameters(this.f92);
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID);
        if (string != null) {
            addParameters.setReferrerCustomerId(string);
        }
        this.f94.onResponse(addParameters.generateLink());
    }
}

package com.appsflyer.share;

import android.content.Context;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.ServerConfigHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LinkGenerator {

    /* renamed from: ʻ  reason: contains not printable characters */
    private String f239;

    /* renamed from: ʼ  reason: contains not printable characters */
    private String f240;

    /* renamed from: ʽ  reason: contains not printable characters */
    private String f241;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f242;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f243;

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private String f244;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f245;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f246;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private Map<String, String> f247 = new HashMap();

    /* renamed from: ͺ  reason: contains not printable characters */
    private Map<String, String> f248 = new HashMap();

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f249;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private String f250;

    /* renamed from: ᐝ  reason: contains not printable characters */
    private String f251;

    public LinkGenerator(String str) {
        this.f243 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final LinkGenerator m214(String str) {
        this.f251 = str;
        return this;
    }

    public LinkGenerator setDeeplinkPath(String str) {
        this.f241 = str;
        return this;
    }

    public LinkGenerator setBaseDeeplink(String str) {
        this.f244 = str;
        return this;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final LinkGenerator m215(String str) {
        this.f240 = str;
        return this;
    }

    public LinkGenerator setChannel(String str) {
        this.f246 = str;
        return this;
    }

    public String getChannel() {
        return this.f246;
    }

    public LinkGenerator setReferrerCustomerId(String str) {
        this.f249 = str;
        return this;
    }

    public String getMediaSource() {
        return this.f243;
    }

    public Map<String, String> getParameters() {
        return this.f248;
    }

    public LinkGenerator setCampaign(String str) {
        this.f242 = str;
        return this;
    }

    public String getCampaign() {
        return this.f242;
    }

    public LinkGenerator addParameter(String str, String str2) {
        this.f248.put(str, str2);
        return this;
    }

    public LinkGenerator addParameters(Map<String, String> map) {
        if (map != null) {
            this.f248.putAll(map);
        }
        return this;
    }

    public LinkGenerator setReferrerUID(String str) {
        this.f245 = str;
        return this;
    }

    public LinkGenerator setReferrerName(String str) {
        this.f239 = str;
        return this;
    }

    public LinkGenerator setReferrerImageURL(String str) {
        this.f250 = str;
        return this;
    }

    public LinkGenerator setBaseURL(String str, String str2, String str3) {
        if (str == null || str.length() <= 0) {
            this.f240 = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{ServerConfigHandler.getUrl(Constants.APPSFLYER_DEFAULT_APP_DOMAIN), str3});
        } else {
            if (str2 == null || str2.length() < 5) {
                str2 = Constants.ONELINK_DEFAULT_DOMAIN;
            }
            this.f240 = String.format(Constants.AF_BASE_URL_FORMAT, new Object[]{str2, str});
        }
        return this;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private StringBuilder m212() {
        StringBuilder sb = new StringBuilder();
        if (this.f240 == null || !this.f240.startsWith("http")) {
            sb.append(ServerConfigHandler.getUrl(Constants.BASE_URL_APP_APPSFLYER_COM));
        } else {
            sb.append(this.f240);
        }
        if (this.f251 != null) {
            sb.append('/').append(this.f251);
        }
        this.f247.put(Constants.URL_MEDIA_SOURCE, this.f243);
        sb.append('?').append("pid=").append(m213(this.f243, "media source"));
        if (this.f245 != null) {
            this.f247.put(Constants.URL_REFERRER_UID, this.f245);
            sb.append('&').append("af_referrer_uid=").append(m213(this.f245, "referrerUID"));
        }
        if (this.f246 != null) {
            this.f247.put("af_channel", this.f246);
            sb.append('&').append("af_channel=").append(m213(this.f246, AppsFlyerProperties.CHANNEL));
        }
        if (this.f249 != null) {
            this.f247.put(Constants.URL_REFERRER_CUSTOMER_ID, this.f249);
            sb.append('&').append("af_referrer_customer_id=").append(m213(this.f249, "referrerCustomerId"));
        }
        if (this.f242 != null) {
            this.f247.put(Constants.URL_CAMPAIGN, this.f242);
            sb.append('&').append("c=").append(m213(this.f242, "campaign"));
        }
        if (this.f239 != null) {
            this.f247.put(Constants.URL_REFERRER_NAME, this.f239);
            sb.append('&').append("af_referrer_name=").append(m213(this.f239, "referrerName"));
        }
        if (this.f250 != null) {
            this.f247.put(Constants.URL_REFERRER_IMAGE_URL, this.f250);
            sb.append('&').append("af_referrer_image_url=").append(m213(this.f250, "referrerImageURL"));
        }
        if (this.f244 != null) {
            StringBuilder append = new StringBuilder().append(this.f244);
            append.append(this.f244.endsWith(Constants.URL_PATH_DELIMITER) ? "" : Constants.URL_PATH_DELIMITER);
            if (this.f241 != null) {
                append.append(this.f241);
            }
            this.f247.put(Constants.URL_BASE_DEEPLINK, append.toString());
            sb.append('&').append("af_dp=").append(m213(this.f244, "baseDeeplink"));
            if (this.f241 != null) {
                sb.append(this.f244.endsWith(Constants.URL_PATH_DELIMITER) ? "" : "%2F").append(m213(this.f241, "deeplinkPath"));
            }
        }
        for (String next : this.f248.keySet()) {
            if (!sb.toString().contains(new StringBuilder().append(next).append("=").append(m213(this.f248.get(next), next)).toString())) {
                sb.append('&').append(next).append('=').append(m213(this.f248.get(next), next));
            }
        }
        return sb;
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static String m213(String str, String str2) {
        try {
            return URLEncoder.encode(str, "utf8");
        } catch (UnsupportedEncodingException e) {
            AFLogger.afInfoLog(new StringBuilder("Illegal ").append(str2).append(": ").append(str).toString());
            return "";
        } catch (Throwable th) {
            return "";
        }
    }

    public String generateLink() {
        return m212().toString();
    }

    public void generateLink(Context context, CreateOneLinkHttpTask.ResponseListener responseListener) {
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID);
        if (!this.f248.isEmpty()) {
            for (Map.Entry next : this.f248.entrySet()) {
                this.f247.put(next.getKey(), next.getValue());
            }
        }
        m212();
        ShareInviteHelper.generateUserInviteLink(context, string, this.f247, responseListener);
    }
}

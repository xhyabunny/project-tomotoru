package com.appsflyer.share;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.appsflyer.AFExecutor;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.OneLinkHttpTask;
import java.util.HashMap;
import java.util.Map;

public class ShareInviteHelper {
    public static LinkGenerator generateInviteUrl(Context context) {
        String appsFlyerUID = AppsFlyerLib.getInstance().getAppsFlyerUID(context);
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_ID);
        String string2 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_DOMAIN);
        LinkGenerator addParameter = new LinkGenerator(Constants.USER_INVITE_LINK_TYPE).setBaseURL(string, string2, context.getPackageName()).setReferrerUID(appsFlyerUID).setReferrerCustomerId(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID)).addParameter(Constants.URL_SITE_ID, context.getPackageName());
        String string3 = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_SCHEME);
        if (string3 != null && string3.length() > 3) {
            addParameter.setBaseDeeplink(string3);
        }
        return addParameter;
    }

    public static void generateUserInviteLink(@NonNull Context context, @NonNull String str, @NonNull Map<String, String> map, @NonNull CreateOneLinkHttpTask.ResponseListener responseListener) {
        if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, generate User Invite Link is disabled", true);
            return;
        }
        CreateOneLinkHttpTask createOneLinkHttpTask = new CreateOneLinkHttpTask(str, map, AppsFlyerLib.getInstance(), context, AppsFlyerLib.getInstance().isTrackingStopped());
        createOneLinkHttpTask.setConnProvider(new OneLinkHttpTask.HttpsUrlConnectionProvider());
        createOneLinkHttpTask.setListener(responseListener);
        AFExecutor.getInstance().getThreadPoolExecutor().execute(createOneLinkHttpTask);
    }

    public static void trackInvite(Context context, String str, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            AFLogger.afWarnLog(Constants.LOG_INVITE_ERROR_NO_CHANNEL);
        } else if (AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.AF_WAITFOR_CUSTOMERID, false)) {
            AFLogger.afInfoLog("CustomerUserId not set, track Invite is disabled", true);
        } else {
            LinkGenerator generateInviteUrl = generateInviteUrl(context);
            generateInviteUrl.addParameters(map);
            AFLogger.afDebugLog(Constants.LOG_INVITE_TRACKING_APP_INVITE_VIA_CHANNEL.concat(String.valueOf(str)));
            AFLogger.afDebugLog(new StringBuilder(Constants.LOG_INVITE_GENERATED_URL).append(generateInviteUrl.generateLink()).toString());
            String mediaSource = generateInviteUrl.getMediaSource();
            if (Constants.USER_INVITE_LINK_TYPE.equals(mediaSource)) {
                mediaSource = AFInAppEventType.INVITE;
            } else if (Constants.USER_SHARE_LINK_TYPE.equals(mediaSource)) {
                mediaSource = AFInAppEventType.SHARE;
            }
            HashMap hashMap = new HashMap();
            if (generateInviteUrl.getParameters() != null) {
                hashMap.putAll(generateInviteUrl.getParameters());
            }
            hashMap.put("af_channel", str);
            AppsFlyerLib.getInstance().trackEvent(context, mediaSource, hashMap);
        }
    }
}

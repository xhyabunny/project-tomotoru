package com.appsflyer.share;

public interface Constants {
    public static final String AF_BASE_URL_FORMAT = "https://%s/%s";
    public static final String APPSFLYER_DEFAULT_APP_DOMAIN = "app.%s";
    public static final String BASE_URL_APP_APPSFLYER_COM = "https://app.%s";
    public static final String HTTP_REDIRECT_URL_HEADER_FIELD = "Location";
    public static final String LOG_CROSS_PROMOTION_APP_INSTALLED_FROM_CROSS_PROMOTION = "[CrossPromotion] App was installed via %s's Cross Promotion";
    public static final String LOG_CROSS_PROMOTION_CLICK_URL = "[CrossPromotion] Click URL: ";
    public static final String LOG_CROSS_PROMOTION_FAILED_RESPONSE_CODE = "[CrossPromotion] Response code is %s for: %s";
    public static final String LOG_CROSS_PROMOTION_IMPRESSION_SUCCESS = "[CrossPromotion] Impression succeeded";
    public static final String LOG_CROSS_PROMOTION_IMPRESSION_URL = "[CrossPromotion] Impression URL: ";
    public static final String LOG_CROSS_PROMOTION_REDIRECTION_STATUS = "[CrossPromotion] Redirecting to: ";
    public static final String LOG_INVITE_DETECTED_APP_INVITE_VIA_CHANNEL = "[Invite] Detected App-Invite via channel: ";
    public static final String LOG_INVITE_ERROR_NO_CHANNEL = "[Invite] Cannot track App-Invite with null/empty channel";
    public static final String LOG_INVITE_GENERATED_URL = "[Invite] Generated URL: ";
    public static final String LOG_INVITE_TRACKING_APP_INVITE_VIA_CHANNEL = "[Invite] Tracking App-Invite via channel: ";
    public static final String ONELINK_DEFAULT_DOMAIN = "go.onelink.me";
    public static final String URL_ADVERTISING_ID = "advertising_id";
    public static final String URL_BASE_DEEPLINK = "af_dp";
    public static final String URL_CAMPAIGN = "c";
    public static final String URL_CHANNEL = "af_channel";
    public static final String URL_MEDIA_SOURCE = "pid";
    public static final String URL_PATH_DELIMITER = "/";
    public static final String URL_REFERRER_CUSTOMER_ID = "af_referrer_customer_id";
    public static final String URL_REFERRER_IMAGE_URL = "af_referrer_image_url";
    public static final String URL_REFERRER_NAME = "af_referrer_name";
    public static final String URL_REFERRER_UID = "af_referrer_uid";
    public static final String URL_SITE_ID = "af_siteid";
    public static final String USER_INVITE_LINK_TYPE = "af_app_invites";
    public static final String USER_SHARE_LINK_TYPE = "af_user_share";
}

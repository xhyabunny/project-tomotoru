package com.appsflyer;

public interface AppsFlyerInAppPurchaseValidatorListener {
    void onValidateInApp();

    void onValidateInAppFailure(String str);
}

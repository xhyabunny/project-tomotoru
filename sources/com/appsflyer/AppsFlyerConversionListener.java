package com.appsflyer;

import android.support.annotation.WorkerThread;
import java.util.Map;

public interface AppsFlyerConversionListener {
    @WorkerThread
    void onAppOpenAttribution(Map<String, String> map);

    void onAttributionFailure(String str);

    @WorkerThread
    void onInstallConversionDataLoaded(Map<String, String> map);

    void onInstallConversionFailure(String str);
}
